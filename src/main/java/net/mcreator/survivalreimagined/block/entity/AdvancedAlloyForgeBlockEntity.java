package net.mcreator.survivalreimagined.block.entity;

import com.terraformersmc.modmenu.gui.widget.entries.ModListEntry;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.mcreator.survivalreimagined.block.AdvancedAlloyForgeBlock;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlockEntities;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.mcreator.survivalreimagined.world.inventory.AdvancedAlloyForgeGUIMenu;

import java.util.List;
import java.util.stream.IntStream;

public class AdvancedAlloyForgeBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer, ExtendedScreenHandlerFactory<BlockPos> {
	public static final int CONTAINER_SIZE = 8;
	public static final int MAX_PROGRESS = 300;
	public static final int BASE_FUEL_CAPACITY = 3000;
	private static final int[] SLOTS = IntStream.range(0, CONTAINER_SIZE).toArray();

	// Alloy Rocks
	private static final TagKey<Item> COPPER = alloyTag("copper_items");
	private static final TagKey<Item> TIN = alloyTag("tin_items");
	private static final TagKey<Item> IRON = alloyTag("iron_items");
	private static final TagKey<Item> MANGANESE = alloyTag("manganese_items");
	private static final TagKey<Item> DIAMOND = alloyTag("diamond_items");
	private static final TagKey<Item> STEEL = alloyTag("steel_items");
	private static final TagKey<Item> GOLD = alloyTag("gold_items");
	private static final TagKey<Item> NETHERITE = alloyTag("netherite_items");
	private static final TagKey<Item> TITANIUM = alloyTag("titanium_items");
	private static final TagKey<Item> URANIUM = alloyTag("uranium_items");
	private static final TagKey<Item> SILVER = alloyTag("silver_items");
	private static final TagKey<Item> LIME = alloyTag("lime_items");

	// Alloy Blocks
	private static final TagKey<Item> COPPER_BLOCKS = alloyBlockTag("copper_items");
	private static final TagKey<Item> TIN_BLOCKS = alloyBlockTag("tin_items");
	private static final TagKey<Item> IRON_BLOCKS = alloyBlockTag("iron_items");
	private static final TagKey<Item> MANGANESE_BLOCKS = alloyBlockTag("manganese_items");
	private static final TagKey<Item> DIAMOND_BLOCKS = alloyBlockTag("diamond_items");
	private static final TagKey<Item> STEEL_BLOCKS = alloyBlockTag("steel_items");
	private static final TagKey<Item> GOLD_BLOCKS = alloyBlockTag("gold_items");
	private static final TagKey<Item> NETHERITE_BLOCKS = alloyBlockTag("netherite_items");
	private static final TagKey<Item> TITANIUM_BLOCKS = alloyBlockTag("titanium_items");
	private static final TagKey<Item> URANIUM_BLOCKS = alloyBlockTag("uranium_items");

	// Create Compat
	private static final TagKey<Item> ZINC = alloyTag("zinc_items");
	private static final TagKey<Item> ZINC_BLOCKS = alloyBlockTag("zinc_items");
	private static final TagKey<Item> ANDESITE = alloyTag("andesite_items");





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
				case 2 -> forgeMaxFuelCapacity();
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

	public int getProgress() { return this.progress; }

	public int getFuelCapacity() { return this.fuelCapacity; }

	public int getMaxFuelCapacity() { return this.forgeMaxFuelCapacity(); }

	public List<ItemStack> getInstalledUpgrades() {
		return java.util.stream.IntStream.range(4, 8)
				.mapToObj(this::getItem)
				.filter(stack -> !stack.isEmpty())
				.toList();
	}

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
				|| stack.is(SurvivalReimaginedModItems.YIELD_UPGRADE.get())
				|| stack.is(SurvivalReimaginedModItems.YIELD_UPGRADE_MKII.get())
				|| stack.is(SurvivalReimaginedModItems.YIELD_UPGRADE_MKIII.get())
				|| stack.is(SurvivalReimaginedModItems.EFFICIENCY_UPGRADE.get())
				|| stack.is(SurvivalReimaginedModItems.BLOCK_PACKAGING_UPGRADE.get());
	}

	public static final TagKey<Item> ALLOY_INPUTS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "alloy"));
	public static final TagKey<Item> ALLOY_BLOCK_INPUTS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "alloy_block_items"));

	public static boolean isAlloyInput(ItemStack stack) {
		return stack.is(ALLOY_INPUTS) || stack.is(ALLOY_BLOCK_INPUTS);

	}

	private static TagKey<Item> alloyTag(String path) {
		return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "alloy/" + path));
	}
	private static TagKey<Item> alloyBlockTag(String path) {
		return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "alloy_block/" + path));
	}

	private boolean hasUpgrade(Item item) {
		for (int i = 4; i < 8; i++) {
			if (this.getItem(i).is(item)) return true;
		}
		return false;
	}

	private int forgeMaxFuelCapacity() {
		if (hasUpgrade(SurvivalReimaginedModItems.FUEL_UPGRADE_MKII.get())) return 9000;
		if (hasUpgrade(SurvivalReimaginedModItems.FUEL_UPGRADE.get())) return 6000;
		return BASE_FUEL_CAPACITY;
	}

	private int yieldMultiplier() {
		if (hasUpgrade(SurvivalReimaginedModItems.YIELD_UPGRADE_MKIII.get())) return 4;
		if (hasUpgrade(SurvivalReimaginedModItems.YIELD_UPGRADE_MKII.get())) return 3;
		if (hasUpgrade(SurvivalReimaginedModItems.YIELD_UPGRADE.get())) return 2;
		return 1;
	}

	private int efficiencyMultiplier() {
		return hasUpgrade(SurvivalReimaginedModItems.EFFICIENCY_UPGRADE.get()) ? 2 : 1;
	}

	private boolean hasBlockPackagingUpgrade() {
		return hasUpgrade(SurvivalReimaginedModItems.BLOCK_PACKAGING_UPGRADE.get());
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, AdvancedAlloyForgeBlockEntity forge) {
		if (level.isClientSide()) return;
		boolean changed = false;

		int maxFuel = forge.forgeMaxFuelCapacity();
		if (forge.fuelCapacity > maxFuel) {
			forge.fuelCapacity = maxFuel;
			changed = true;
		}

		ItemStack rod = forge.getItem(3);
		if (isReactorRod(rod) && forge.fuelCapacity < maxFuel) {
			forge.fuelCapacity = Math.min(maxFuel, forge.fuelCapacity + 10);
			forge.fuelTimer += 10;
			changed = true;
			boolean advanced = rod.is(SurvivalReimaginedModItems.ADVANCED_REACTOR_ROD.get());
			int life = advanced ? 3000 : 1500;
			if (forge.fuelTimer >= life) {
				forge.setItem(3, new ItemStack(advanced
						? SurvivalReimaginedModItems.DRAINED_ADVANCED_REACTOR_ROD.get()
						: SurvivalReimaginedModItems.DEPLETED_REACTOR_ROD.get()));
				forge.fuelTimer = 0;
			}
		}

		AlloyRecipe recipe = forge.getRecipe(forge.getItem(1), forge.getItem(2));
		int yield = forge.yieldMultiplier();
		int outputCount = recipe == null ? 0 : recipe.count() * (recipe.applyYield() ? yield : 1);
		if (recipe == null || !forge.canOutput(recipe.result(), outputCount)) {
			if (forge.progress != 0) {
				forge.progress = 0;
				changed = true;
			}
		} else if (forge.fuelCapacity > 0) {
			int efficiency = forge.efficiencyMultiplier();
			forge.progress += 2 * efficiency;
			forge.fuelCapacity = Math.max(0, forge.fuelCapacity - Math.max(1, 4 / efficiency));
			changed = true;

			if (forge.progress >= MAX_PROGRESS) {
				forge.getItem(1).shrink(1);
				forge.getItem(2).shrink(1);
				ItemStack output = forge.getItem(0);
				if (output.isEmpty()) forge.setItem(0, new ItemStack(recipe.result(), outputCount));
				else output.grow(outputCount);
				forge.progress = 0;
			}
		} else if (forge.progress > 0) {
			forge.progress = Math.max(0, forge.progress - 2);
			changed = true;
		}

		if (forge.fuelCapacity > 0 && level instanceof ServerLevel serverLevel && level.getGameTime() % 10L == 0L) {
			emitRunningEffects(serverLevel, pos, state);
		}

		if (changed) forge.setChanged();
	}

	private static void emitRunningEffects(ServerLevel level, BlockPos pos, BlockState state) {
		Direction facing = state.hasProperty(AdvancedAlloyForgeBlock.FACING)
				? state.getValue(AdvancedAlloyForgeBlock.FACING) : Direction.NORTH;

		double x = pos.getX() + 0.5D;
		double y = pos.getY() + 0.15D;
		double z = pos.getZ() + 0.5D;
		double sideSpread = 0.35D;

		switch (facing) {
			case NORTH -> z += 0.9D;
			case SOUTH -> z -= 0.9D;
			case WEST -> x += 0.9D;
			case EAST -> x -= 0.9D;
			default -> { }
		}

		level.sendParticles(ParticleTypes.LARGE_SMOKE, x, y, z, 6, sideSpread, 0.05D, sideSpread, 0.02D);
		if (level.getRandom().nextFloat() < 0.35F) {
			level.playSound(null, pos, SoundEvents.BLASTFURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F);
		}
	}

	private AlloyRecipe getRecipe(ItemStack a, ItemStack b) {
		if (hasBlockPackagingUpgrade()) {

			// Alloy Block Recipes
			if (matches(a, b, COPPER_BLOCKS, TIN_BLOCKS)) return new AlloyRecipe(SurvivalReimaginedModItems.BLOCK_OF_BRONZE.get(), 2, false);
			if (matches(a, b, IRON_BLOCKS, MANGANESE_BLOCKS)) return new AlloyRecipe(SurvivalReimaginedModItems.BLOCK_OF_STEEL.get(), 2, false);
			if (matches(a, b, DIAMOND_BLOCKS, STEEL_BLOCKS)) return new AlloyRecipe(SurvivalReimaginedModItems.PLATED_DIAMOND_BLOCK.get(), 2, false);
			if (matches(a, b, TITANIUM_BLOCKS, URANIUM_BLOCKS)) return new AlloyRecipe(SurvivalReimaginedModItems.TURANITE_BLOCK.get(), 2, false);
			if (matches(a ,b, GOLD_BLOCKS, NETHERITE_BLOCKS)) return new AlloyRecipe(Items.NETHERITE_BLOCK, 1,false);

			// Block Recipes
			class BlockLists {
				static final List<ItemStack> COPPER_BLOCKS = List.of(
						new ItemStack(Items.RAW_COPPER_BLOCK),
						new ItemStack(SurvivalReimaginedModItems.RAW_MALACHITE_BLOCK.get()),
						new ItemStack(SurvivalReimaginedModItems.RAW_AZURITE_BLOCK.get())
				);
				static final List<ItemStack> TIN_BLOCKS = List.of(
						new ItemStack(SurvivalReimaginedModItems.BLOCK_OF_RAW_TIN.get()),
						new ItemStack(SurvivalReimaginedModItems.RAW_CASSITERITE_BLOCK.get())
				);
				static final List<ItemStack> IRON_BLOCKS = List.of(
						new ItemStack(Items.RAW_IRON_BLOCK),
						new ItemStack(SurvivalReimaginedModItems.HEMATITE_BLOCK.get()),
						new ItemStack(SurvivalReimaginedModItems.MAGNETITE_BLOCK.get())
				);
				static final List<ItemStack> MANGANESE_BLOCKS = List.of(
						new ItemStack(SurvivalReimaginedModItems.BLOCK_OF_RAW_MANGANESE.get()),
						new ItemStack(SurvivalReimaginedModItems.PYROLUSITE_BLOCK.get()),
						new ItemStack(SurvivalReimaginedModItems.MANGANITE_BLOCK.get())
				);
				static final List<ItemStack> GOLD_BLOCKS = List.of(
						new ItemStack(Items.RAW_GOLD_BLOCK),
						new ItemStack(SurvivalReimaginedModItems.CALAVERITE_BLOCK.get())
				);
				static final List<ItemStack> TITANIUM_BLOCKS = List.of(
						new ItemStack(SurvivalReimaginedModItems.BLOCK_OF_RAW_TITANIUM.get()),
						new ItemStack(SurvivalReimaginedModItems.ILMENITE_BLOCK.get())
				);
				static final List<ItemStack> URANIUM_BLOCKS = List.of(
						new ItemStack(SurvivalReimaginedModItems.BLOCK_OF_RAW_URANINITE.get()),
						new ItemStack(SurvivalReimaginedModItems.URANOPHANE_BLOCK.get())
				);
			}
			if (matchesList(a, b, BlockLists.COPPER_BLOCKS, BlockLists.COPPER_BLOCKS)) return new AlloyRecipe(Items.COPPER_BLOCK, 2, false);
			if (matchesList(a, b, BlockLists.TIN_BLOCKS, BlockLists.TIN_BLOCKS)) return new AlloyRecipe(SurvivalReimaginedModItems.BLOCK_OF_TIN.get(), 2, false);
			if (matchesList(a, b, BlockLists.IRON_BLOCKS, BlockLists.IRON_BLOCKS)) return new AlloyRecipe(Items.IRON_BLOCK, 2, false);
			if (matchesList(a, b, BlockLists.MANGANESE_BLOCKS, BlockLists.MANGANESE_BLOCKS)) return new AlloyRecipe(SurvivalReimaginedModItems.BLOCK_OF_MANGANESE.get(), 2, false);
			if (matchesList(a, b, BlockLists.GOLD_BLOCKS, BlockLists.GOLD_BLOCKS)) return new AlloyRecipe(Items.GOLD_BLOCK, 2, false);
			if (matchesList(a, b, BlockLists.TITANIUM_BLOCKS, BlockLists.TITANIUM_BLOCKS)) return new AlloyRecipe(SurvivalReimaginedModItems.BLOCK_OF_TITANIUM.get(), 2, false);
			if (matchesList(a, b, BlockLists.URANIUM_BLOCKS, BlockLists.URANIUM_BLOCKS)) return new AlloyRecipe(SurvivalReimaginedModItems.BLOCK_OF_URANIUM.get(), 2, false);

			// Create Compat
			//if (FabricLoader.getInstance().isModLoaded("create")) {}

		}
		if (matches(a, b, COPPER, TIN)) return new AlloyRecipe(SurvivalReimaginedModItems.ROUGH_BRONZE.get(), 2, true);
		if (matches(a, b, IRON, MANGANESE)) return new AlloyRecipe(SurvivalReimaginedModItems.ROUGH_STEEL.get(), 2, true);
		if (matches(a, b, URANIUM, TITANIUM)) return new AlloyRecipe(SurvivalReimaginedModItems.ROUGH_TURANITE.get(), 2, true);
		if (matches(a, b, DIAMOND, STEEL)) return new AlloyRecipe(SurvivalReimaginedModItems.ROUGH_PLATED_DIAMOND.get(), 2, true);
		if (matches(a, b, NETHERITE, GOLD)) return new AlloyRecipe(SurvivalReimaginedModItems.ROUGH_NETHERITE.get(), 2, true);
		return null;
	}

	private static boolean matches(ItemStack a, ItemStack b, TagKey<Item> first, TagKey<Item> second) {
		return a.is(first) && b.is(second) || a.is(second) && b.is(first);
	}

	private static boolean matchesList(ItemStack a, ItemStack b, List<ItemStack> first, List<ItemStack> second) {
		return first.stream().anyMatch(item -> ItemStack.isSameItem(a, item)) && second.stream().anyMatch(item -> ItemStack.isSameItem(b, item))
				|| second.stream().anyMatch(item -> ItemStack.isSameItem(a, item)) && first.stream().anyMatch(item -> ItemStack.isSameItem(b, item));
	}

	private static boolean matchesItems(ItemStack a, ItemStack b, Item first, Item second) {
		return a.is(first) && b.is(second) || a.is(second) && b.is(first);
	}

	private boolean canOutput(Item result, int count) {
		ItemStack output = this.getItem(0);
		return output.isEmpty() || output.is(result) && output.getCount() + count <= output.getMaxStackSize();
	}

	private record AlloyRecipe(Item result, int count, boolean applyYield) {}

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
