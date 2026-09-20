package net.mcreator.survivalreimagined.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlockEntities;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.mcreator.survivalreimagined.world.inventory.AdvancedAlloyForgeGUIMenu;

import java.util.stream.IntStream;

public class AdvancedAlloyForgeBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer, ExtendedScreenHandlerFactory<BlockPos> {
	public static final int CONTAINER_SIZE = 8;
	public static final int MAX_PROGRESS = 300;
	public static final int BASE_FUEL_CAPACITY = 3000;
	private static final int[] SLOTS = IntStream.range(0, CONTAINER_SIZE).toArray();

	private static final TagKey<Item> COPPER = alloyTag("copper_items");
	private static final TagKey<Item> TIN = alloyTag("tin_items");
	private static final TagKey<Item> IRON = alloyTag("iron_items");
	private static final TagKey<Item> MANGANESE = alloyTag("manganese_items");
	private static final TagKey<Item> URANIUM = alloyTag("uranium_items");
	private static final TagKey<Item> TITANIUM = alloyTag("titanium_items");
	private static final TagKey<Item> DIAMOND = alloyTag("diamond_items");
	private static final TagKey<Item> STEEL = alloyTag("steel_items");
	private static final TagKey<Item> NETHERITE = alloyTag("netherite_items");
	private static final TagKey<Item> GOLD = alloyTag("gold_items");

	private NonNullList<ItemStack> stacks = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
	private int progress;
	private int fuelCapacity;
	private int fuelTimer;

	private final ContainerData dataAccess = new ContainerData() {
		@Override
		public int get(int index) {
			return switch (index) {
				case 0 -> progress;
				case 1 -> fuelCapacity;
				case 2 -> BASE_FUEL_CAPACITY;
				default -> 0;
			};
		}

		@Override
		public void set(int index, int value) {
			switch (index) {
				case 0 -> progress = value;
				case 1 -> fuelCapacity = value;
				default -> { }
			}
		}

		@Override
		public int getCount() {
			return 3;
		}
	};

	public AdvancedAlloyForgeBlockEntity(BlockPos pos, BlockState state) {
		super(SurvivalReimaginedModBlockEntities.ADVANCED_ALLOY_FORGE.get(), pos, state);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		this.stacks = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
		if (!this.tryLoadLootTable(tag)) ContainerHelper.loadAllItems(tag, this.stacks, registries);
		this.progress = tag.getInt("SmeltTime");
		this.fuelCapacity = tag.getInt("FuelCapacity");
		this.fuelTimer = tag.getInt("FuelTimer");
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		if (!this.trySaveLootTable(tag)) ContainerHelper.saveAllItems(tag, this.stacks, registries);
		tag.putInt("SmeltTime", this.progress);
		tag.putInt("FuelCapacity", this.fuelCapacity);
		tag.putInt("FuelTimer", this.fuelTimer);
	}

	@Override public int getContainerSize() { return CONTAINER_SIZE; }
	@Override protected NonNullList<ItemStack> getItems() { return this.stacks; }
	@Override protected void setItems(NonNullList<ItemStack> stacks) { this.stacks = stacks; }
	@Override protected Component getDefaultName() { return Component.translatable("block.survival_reimagined.advanced_alloy_forge"); }
	@Override protected AbstractContainerMenu createMenu(int id, Inventory inventory) { return new AdvancedAlloyForgeGUIMenu(id, inventory, this); }
	@Override public BlockPos getScreenOpeningData(ServerPlayer player) { return this.worldPosition; }

	public ContainerData getDataAccess() { return this.dataAccess; }

	public static boolean isReactorRod(ItemStack stack) {
		return stack.is(SurvivalReimaginedModItems.REACTOR_ROD.get());
	}

	public static boolean isDepletedRod(ItemStack stack) {
		return stack.is(SurvivalReimaginedModItems.DEPLETED_REACTOR_ROD.get());
	}

	public static boolean isAlloyInput(ItemStack stack) {
		return stack.is(COPPER) || stack.is(TIN) || stack.is(IRON) || stack.is(MANGANESE)
				|| stack.is(URANIUM) || stack.is(TITANIUM) || stack.is(DIAMOND)
				|| stack.is(STEEL) || stack.is(NETHERITE) || stack.is(GOLD);
	}

	private static TagKey<Item> alloyTag(String path) {
		return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "alloy/" + path));
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, AdvancedAlloyForgeBlockEntity forge) {
		if (level.isClientSide()) return;
		boolean changed = false;

		ItemStack rod = forge.getItem(3);
		if (isReactorRod(rod) && forge.fuelCapacity < BASE_FUEL_CAPACITY) {
			forge.fuelCapacity = Math.min(BASE_FUEL_CAPACITY, forge.fuelCapacity + 10);
			forge.fuelTimer += 10;
			changed = true;
			if (forge.fuelTimer >= 1500) {
				forge.setItem(3, new ItemStack(SurvivalReimaginedModItems.DEPLETED_REACTOR_ROD.get()));
				forge.fuelTimer = 0;
			}
		}

		AlloyRecipe recipe = getRecipe(forge.getItem(1), forge.getItem(2));
		if (recipe == null || !forge.canOutput(recipe.result(), recipe.count())) {
			if (forge.progress != 0) {
				forge.progress = 0;
				changed = true;
			}
		} else if (forge.fuelCapacity > 0) {
			forge.progress += 2;
			forge.fuelCapacity = Math.max(0, forge.fuelCapacity - 4);
			changed = true;

			if (forge.progress >= MAX_PROGRESS) {
				forge.getItem(1).shrink(1);
				forge.getItem(2).shrink(1);
				ItemStack output = forge.getItem(0);
				if (output.isEmpty()) forge.setItem(0, new ItemStack(recipe.result(), recipe.count()));
				else output.grow(recipe.count());
				forge.progress = 0;
			}
		} else if (forge.progress > 0) {
			forge.progress = Math.max(0, forge.progress - 2);
			changed = true;
		}

		if (changed) forge.setChanged();
	}

	private static AlloyRecipe getRecipe(ItemStack a, ItemStack b) {
		if (matches(a, b, COPPER, TIN)) return new AlloyRecipe(SurvivalReimaginedModItems.ROUGH_BRONZE.get(), 2);
		if (matches(a, b, IRON, MANGANESE)) return new AlloyRecipe(SurvivalReimaginedModItems.ROUGH_STEEL.get(), 2);
		if (matches(a, b, URANIUM, TITANIUM)) return new AlloyRecipe(SurvivalReimaginedModItems.ROUGH_TURANITE.get(), 2);
		if (matches(a, b, DIAMOND, STEEL)) return new AlloyRecipe(SurvivalReimaginedModItems.ROUGH_PLATED_DIAMOND.get(), 2);
		if (matches(a, b, NETHERITE, GOLD)) return new AlloyRecipe(SurvivalReimaginedModItems.ROUGH_NETHERITE.get(), 2);
		return null;
	}

	private static boolean matches(ItemStack a, ItemStack b, TagKey<Item> first, TagKey<Item> second) {
		return a.is(first) && b.is(second) || a.is(second) && b.is(first);
	}

	private boolean canOutput(Item result, int count) {
		ItemStack output = this.getItem(0);
		return output.isEmpty() || output.is(result) && output.getCount() + count <= output.getMaxStackSize();
	}

	private record AlloyRecipe(Item result, int count) {}

	@Override
	public boolean canPlaceItem(int index, ItemStack stack) {
		if (index == 0) return false;
		if (index == 1 || index == 2) return isAlloyInput(stack);
		if (index == 3) return isReactorRod(stack) && this.getItem(3).isEmpty();
		return false;
	}

	@Override public int[] getSlotsForFace(Direction side) { return SLOTS; }
	@Override public boolean canPlaceItemThroughFace(int index, ItemStack stack, Direction direction) { return canPlaceItem(index, stack); }
	@Override public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
		return index == 0 || index == 3 && isDepletedRod(stack);
	}
}
