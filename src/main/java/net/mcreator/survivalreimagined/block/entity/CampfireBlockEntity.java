package net.mcreator.survivalreimagined.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.mcreator.survivalreimagined.block.CampfireBlock;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlockEntities;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.mcreator.survivalreimagined.world.inventory.CampfireGUIMenu;

import java.util.stream.IntStream;

public class CampfireBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer, ExtendedScreenHandlerFactory<BlockPos> {
	public static final int CONTAINER_SIZE = 5;
	public static final int MAX_FUEL = 560;
	private static final int[] SLOTS = IntStream.range(0, CONTAINER_SIZE).toArray();
	private static final TagKey<Item> CAMPFIRE_FUELS = tag("campfire_fuels");
	private static final TagKey<Item> BARK = tag("fuels/bark_large");
	private static final TagKey<Item> PLANK = tag("fuels/single_plank");
	private static final TagKey<Item> CHUNKS = tag("fuels/chunks");
	private static final TagKey<Item> RAW = tag("campfire/raw");
	private static final TagKey<Item> COOKED = tag("campfire/cooked");
	private static final TagKey<Item> BURNT = tag("campfire/burnt");

	private NonNullList<ItemStack> stacks = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
	private int fuelProgress;
	private final int[] cookProgress = new int[4];
	private int charcoalProgress;

	private final ContainerData dataAccess = new ContainerData() {
		@Override public int get(int index) { return index == 0 ? fuelProgress : 0; }
		@Override public void set(int index, int value) { if (index == 0) fuelProgress = value; }
		@Override public int getCount() { return 1; }
	};

	public CampfireBlockEntity(BlockPos pos, BlockState state) {
		super(SurvivalReimaginedModBlockEntities.CAMPFIRE.get(), pos, state);
	}

	private static TagKey<Item> tag(String path) {
		return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", path));
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		this.stacks = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
		if (!this.tryLoadLootTable(tag)) ContainerHelper.loadAllItems(tag, this.stacks, registries);
		this.fuelProgress = tag.getInt("CampfireFuel");
		for (int i = 0; i < cookProgress.length; i++) this.cookProgress[i] = tag.getInt("CookSlot" + (i + 1));
		this.charcoalProgress = tag.getInt("CharcoalProgress");
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		if (!this.trySaveLootTable(tag)) ContainerHelper.saveAllItems(tag, this.stacks, registries);
		tag.putInt("CampfireFuel", this.fuelProgress);
		for (int i = 0; i < cookProgress.length; i++) tag.putInt("CookSlot" + (i + 1), this.cookProgress[i]);
		tag.putInt("CharcoalProgress", this.charcoalProgress);
	}

	@Override public int getContainerSize() { return CONTAINER_SIZE; }
	@Override protected NonNullList<ItemStack> getItems() { return stacks; }
	@Override protected void setItems(NonNullList<ItemStack> items) { stacks = items; }
	@Override protected Component getDefaultName() { return Component.translatable("block.survival_reimagined.campfire"); }
	@Override protected AbstractContainerMenu createMenu(int id, Inventory inventory) { return new CampfireGUIMenu(id, inventory, this); }
	@Override public BlockPos getScreenOpeningData(ServerPlayer player) { return this.worldPosition; }
	public ContainerData getDataAccess() { return dataAccess; }
	public boolean hasFuel() { return getItem(0).is(CAMPFIRE_FUELS); }

	public static boolean isFuel(ItemStack stack) { return stack.is(CAMPFIRE_FUELS); }
	public static boolean isCookable(ItemStack stack) { return stack.is(RAW) || stack.is(COOKED) || stack.is(BURNT); }

	public static void serverTick(Level level, BlockPos pos, BlockState state, CampfireBlockEntity campfire) {
		if (level.isClientSide()) return;
		boolean changed = false;
		boolean lit = state.getValue(CampfireBlock.LIT);

		if (lit) {
			ItemStack fuel = campfire.getItem(0);
			if (!isFuel(fuel)) {
				level.setBlock(pos, state.setValue(CampfireBlock.LIT, false), 3);
				campfire.fuelProgress = 0;
				changed = true;
			} else {
				campfire.fuelProgress += burnRate(fuel);
				if (campfire.fuelProgress >= MAX_FUEL) {
					fuel.shrink(1);
					campfire.fuelProgress = 0;
				}
				for (int slot = 1; slot < CONTAINER_SIZE; slot++) {
					ItemStack stack = campfire.getItem(slot);
					if (isCookable(stack)) {
						int i = slot - 1;
						campfire.cookProgress[i]++;
						int required = 20 * Math.max(1, stack.getCount());
						if (campfire.cookProgress[i] >= required) {
							campfire.cookProgress[i] = 0;
							CustomData data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
							int stageProgress = (int) data.copyTag().getDouble("PercentageNumber") + 1;
							if (stageProgress >= 30) {
								Item result = nextStage(stack);
								if (result != null) campfire.setItem(slot, new ItemStack(result, stack.getCount()));
							} else {
								CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putDouble("PercentageNumber", stageProgress));
							}
						}
					} else {
						campfire.cookProgress[slot - 1] = 0;
					}
				}

				if (level.getBlockState(pos.below()).is(net.minecraft.tags.BlockTags.LOGS)) {
					campfire.charcoalProgress++;
					if (campfire.charcoalProgress >= 1000) {
						level.setBlock(pos.below(), SurvivalReimaginedModBlocks.BLOCK_OF_CHARCOAL.get().defaultBlockState(), 3);
						campfire.charcoalProgress = 0;
					}
				} else {
					campfire.charcoalProgress = 0;
				}
				changed = true;
			}
		}

		if (changed) campfire.setChanged();
	}

	private static int burnRate(ItemStack fuel) {
		if (fuel.is(BARK) || fuel.is(PLANK)) return 4;
		if (fuel.is(CHUNKS)) return 2;
		if (fuel.is(ItemTags.COALS)) return 1;
		return 1;
	}

	private static Item nextStage(ItemStack stack) {
		String id = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
		if (stack.is(RAW)) {
			return switch (id) {
				case "beef" -> SurvivalReimaginedModItems.COOKED_BEEF.get();
				case "raw_mutton" -> SurvivalReimaginedModItems.COOKED_MUTTON.get();
				case "raw_porkchop" -> SurvivalReimaginedModItems.COOKED_PORKCHOP.get();
				case "raw_chicken" -> SurvivalReimaginedModItems.COOKED_CHICKEN.get();
				case "raw_rabbit" -> SurvivalReimaginedModItems.COOKED_RABBIT.get();
				case "raw_cod" -> SurvivalReimaginedModItems.COOKED_COD.get();
				case "raw_salmon" -> SurvivalReimaginedModItems.COOKED_SALMON.get();
				case "raw_equine" -> SurvivalReimaginedModItems.COOKED_EQUINE.get();
				case "corn_on_the_cob" -> SurvivalReimaginedModItems.COOKED_CORN_ON_THE_COB.get();
				case "potato" -> Items.BAKED_POTATO;
				default -> null;
			};
		}
		if (stack.is(COOKED)) {
			return switch (id) {
				case "cooked_beef" -> SurvivalReimaginedModItems.BURNT_BEEF.get();
				case "cooked_mutton" -> SurvivalReimaginedModItems.BURNT_MUTTON.get();
				case "cooked_porkchop" -> SurvivalReimaginedModItems.BURNT_PORKCHOP.get();
				case "cooked_chicken" -> SurvivalReimaginedModItems.BURNT_CHICKEN.get();
				case "cooked_rabbit" -> SurvivalReimaginedModItems.BURNT_RABBIT.get();
				case "cooked_cod" -> SurvivalReimaginedModItems.BURNT_COD.get();
				case "cooked_salmon" -> SurvivalReimaginedModItems.BURNT_SALMON.get();
				case "cooked_equine" -> SurvivalReimaginedModItems.BURNT_EQUINE.get();
				case "cooked_corn_on_the_cob" -> SurvivalReimaginedModItems.BURNT_CORN_ON_THE_COB.get();
				case "baked_potato" -> SurvivalReimaginedModItems.BURNT_POTATO.get();
				default -> null;
			};
		}
		if (stack.is(BURNT)) return SurvivalReimaginedModItems.CHARCOAL_POWDER.get();
		return null;
	}

	@Override
	public boolean canPlaceItem(int index, ItemStack stack) {
		if (index == 0) return isFuel(stack);
		return index > 0 && isCookable(stack);
	}

	@Override public int[] getSlotsForFace(Direction side) { return SLOTS; }
	@Override public boolean canPlaceItemThroughFace(int index, ItemStack stack, Direction direction) { return canPlaceItem(index, stack); }
	@Override public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) { return index > 0; }
}
