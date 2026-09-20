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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.mcreator.survivalreimagined.block.AdvancedAlloyForgeBlock;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlockEntities;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
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
				case 2 -> getMaxFuelCapacity();
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
		return stack.is(SurvivalReimaginedModItems.REACTOR_ROD.get())
				|| stack.is(SurvivalReimaginedModItems.ADVANCED_REACTOR_ROD.get());
	}

	public static boolean isDepletedRod(ItemStack stack) {
		return stack.is(SurvivalReimaginedModItems.DEPLETED_REACTOR_ROD.get())
				|| stack.is(SurvivalReimaginedModItems.DRAINED_ADVANCED_REACTOR_ROD.get());
	}

	public static boolean isUpgrade(ItemStack stack) {
		return stack.is(SurvivalReimaginedModItems.FUEL_UPGRADE.get())
				|| stack.is(SurvivalReimaginedModItems.FUEL_UPGRADE_MKII.get())
				|| stack.is(SurvivalReimaginedModItems.EFFICIENCY_UPGRADE.get())
				|| stack.is(SurvivalReimaginedModItems.YIELD_UPGRADE.get())
				|| stack.is(SurvivalReimaginedModItems.YIELD_UPGRADE_MKII.get())
				|| stack.is(SurvivalReimaginedModItems.YIELD_UPGRADE_MKIII.get())
				|| stack.is(SurvivalReimaginedModItems.BLOCK_PACKAGING_UPGRADE.get());
	}

	public static boolean isAlloyInput(ItemStack stack) {
		return stack.is(COPPER) || stack.is(TIN) || stack.is(IRON) || stack.is(MANGANESE)
				|| stack.is(URANIUM) || stack.is(TITANIUM) || stack.is(DIAMOND)
				|| stack.is(STEEL) || stack.is(NETHERITE) || stack.is(GOLD);
	}

	private int getMaxFuelCapacity() {
		if (hasUpgrade(SurvivalReimaginedModItems.FUEL_UPGRADE_MKII.get())) return 9000;
		if (hasUpgrade(SurvivalReimaginedModItems.FUEL_UPGRADE.get())) return 6000;
		return BASE_FUEL_CAPACITY;
	}

	private int efficiencyMultiplier() {
		return hasUpgrade(SurvivalReimaginedModItems.EFFICIENCY_UPGRADE.get()) ? 2 : 1;
	}

	private int yieldMultiplier() {
		if (hasUpgrade(SurvivalReimaginedModItems.YIELD_UPGRADE_MKIII.get())) return 4;
		if (hasUpgrade(SurvivalReimaginedModItems.YIELD_UPGRADE_MKII.get())) return 3;
		if (hasUpgrade(SurvivalReimaginedModItems.YIELD_UPGRADE.get())) return 2;
		return 1;
	}

	private boolean packagingEnabled() {
		return hasUpgrade(SurvivalReimaginedModItems.BLOCK_PACKAGING_UPGRADE.get());
	}

	private boolean hasUpgrade(Item item) {
		for (int i = 4; i <= 7; i++) if (getItem(i).is(item)) return true;
		return false;
	}

	private static TagKey<Item> alloyTag(String path) {
		return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "alloy/" + path));
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, AdvancedAlloyForgeBlockEntity forge) {
		if (level.isClientSide()) return;
		if (!AdvancedAlloyForgeBlock.isSetupComplete(level, pos)) {
			if (forge.progress != 0) {
				forge.progress = 0;
				forge.setChanged();
			}
			return;
		}
		boolean changed = false;

		int maxFuel = forge.getMaxFuelCapacity();
		if (forge.fuelCapacity > maxFuel) {
			forge.fuelCapacity = maxFuel;
			changed = true;
		}

		ItemStack rod = forge.getItem(3);
		boolean advancedRod = rod.is(SurvivalReimaginedModItems.ADVANCED_REACTOR_ROD.get());
		if (isReactorRod(rod) && forge.fuelCapacity < maxFuel) {
			forge.fuelCapacity = Math.min(maxFuel, forge.fuelCapacity + 10);
			forge.fuelTimer += 10;
			changed = true;
			int rodLife = advancedRod ? 3000 : 1500;
			if (forge.fuelTimer >= rodLife) {
				forge.setItem(3, new ItemStack(advancedRod
						? SurvivalReimaginedModItems.DRAINED_ADVANCED_REACTOR_ROD.get()
						: SurvivalReimaginedModItems.DEPLETED_REACTOR_ROD.get()));
				forge.fuelTimer = 0;
			}
		}

		AlloyRecipe recipe = getRecipe(forge.getItem(1), forge.getItem(2), forge.packagingEnabled());
		if (recipe == null || !forge.canOutput(recipe.result(), recipe.outputCount(forge.yieldMultiplier()))) {
			if (forge.progress != 0) {
				forge.progress = 0;
				changed = true;
			}
		} else if (forge.fuelCapacity > 0) {
			int efficiency = forge.efficiencyMultiplier();
			forge.progress += recipe.packaged() ? efficiency : 2 * efficiency;
			forge.fuelCapacity = Math.max(0, forge.fuelCapacity - Math.max(1, recipe.packaged() ? 6 / efficiency : 4 / efficiency));
			changed = true;

			if (forge.progress >= MAX_PROGRESS) {
				forge.getItem(1).shrink(recipe.inputCount());
				forge.getItem(2).shrink(recipe.inputCount());
				int amount = recipe.outputCount(forge.yieldMultiplier());
				ItemStack output = forge.getItem(0);
				if (output.isEmpty()) forge.setItem(0, new ItemStack(recipe.result(), amount));
				else output.grow(amount);
				forge.progress = 0;
			}
		} else if (forge.progress > 0) {
			forge.progress = Math.max(0, forge.progress - 2);
			changed = true;
		}

		if (changed) forge.setChanged();
	}

	private static AlloyRecipe getRecipe(ItemStack a, ItemStack b, boolean packaged) {
		if (matches(a, b, COPPER, TIN)) return packaged
				? new AlloyRecipe(SurvivalReimaginedModBlocks.BLOCK_OF_BRONZE.get().asItem(), 9, 2, true)
				: new AlloyRecipe(SurvivalReimaginedModItems.ROUGH_BRONZE.get(), 1, 2, false);
		if (matches(a, b, IRON, MANGANESE)) return packaged
				? new AlloyRecipe(SurvivalReimaginedModBlocks.BLOCK_OF_STEEL.get().asItem(), 9, 2, true)
				: new AlloyRecipe(SurvivalReimaginedModItems.ROUGH_STEEL.get(), 1, 2, false);
		if (matches(a, b, URANIUM, TITANIUM)) return packaged
				? new AlloyRecipe(SurvivalReimaginedModBlocks.TURANITE_BLOCK.get().asItem(), 9, 2, true)
				: new AlloyRecipe(SurvivalReimaginedModItems.ROUGH_TURANITE.get(), 1, 2, false);
		if (matches(a, b, DIAMOND, STEEL))
			return new AlloyRecipe(SurvivalReimaginedModItems.ROUGH_PLATED_DIAMOND.get(), 1, 2, false);
		if (matches(a, b, NETHERITE, GOLD)) return packaged
				? new AlloyRecipe(Blocks.NETHERITE_BLOCK.asItem(), 36, 2, true)
				: new AlloyRecipe(SurvivalReimaginedModItems.ROUGH_NETHERITE.get(), 1, 2, false);
		return null;
	}

	private static boolean matches(ItemStack a, ItemStack b, TagKey<Item> first, TagKey<Item> second) {
		return a.is(first) && b.is(second) || a.is(second) && b.is(first);
	}

	private boolean canOutput(Item result, int count) {
		ItemStack output = this.getItem(0);
		return output.isEmpty() || output.is(result) && output.getCount() + count <= output.getMaxStackSize();
	}

	private record AlloyRecipe(Item result, int inputCount, int baseOutput, boolean packaged) {
		int outputCount(int yield) { return packaged ? baseOutput : baseOutput * yield; }
	}

	@Override
	public boolean canPlaceItem(int index, ItemStack stack) {
		if (index == 0) return false;
		if (index == 1 || index == 2) return isAlloyInput(stack);
		if (index == 3) return isReactorRod(stack) && this.getItem(3).isEmpty();
		if (index >= 4 && index <= 7) return isUpgrade(stack);
		return false;
	}

	@Override public int[] getSlotsForFace(Direction side) { return SLOTS; }
	@Override public boolean canPlaceItemThroughFace(int index, ItemStack stack, Direction direction) { return canPlaceItem(index, stack); }
	@Override public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
		return index == 0 || index == 3 && isDepletedRod(stack);
	}
}
