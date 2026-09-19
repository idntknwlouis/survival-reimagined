package net.mcreator.survivalreimagined.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.mcreator.survivalreimagined.block.ForgeBlock;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlockEntities;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModSounds;
import net.mcreator.survivalreimagined.world.inventory.ForgeGUIMenu;

import java.util.stream.IntStream;

public class ForgeBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer, ExtendedScreenHandlerFactory<BlockPos> {
	public static final int CONTAINER_SIZE = 9;
	public static final int MAX_FUEL = 600;
	public static final int MAX_BURN_TIME = 60;
	private static final int MAX_FUEL_HUNDREDTHS = MAX_FUEL * 100;
	private static final int[] SLOTS = IntStream.range(0, CONTAINER_SIZE).toArray();

	private NonNullList<ItemStack> stacks = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
	private int fuelMeterHundredths;
	private int burnTime;
	private int openCount;

	private final ContainerData dataAccess = new ContainerData() {
		@Override
		public int get(int index) {
			return switch (index) {
				case 0 -> ForgeBlockEntity.this.getFuelMeter();
				case 1 -> ForgeBlockEntity.this.burnTime;
				default -> 0;
			};
		}

		@Override
		public void set(int index, int value) {
			switch (index) {
				case 0 -> ForgeBlockEntity.this.fuelMeterHundredths = value * 100;
				case 1 -> ForgeBlockEntity.this.burnTime = value;
			}
		}

		@Override
		public int getCount() {
			return 2;
		}
	};

	public ForgeBlockEntity(BlockPos pos, BlockState state) {
		super(SurvivalReimaginedModBlockEntities.FORGE.get(), pos, state);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		if (!this.tryLoadLootTable(tag)) {
			ContainerHelper.loadAllItems(tag, this.stacks, registries);
		}
		this.fuelMeterHundredths = tag.contains("FuelMeterHundredths") ? tag.getInt("FuelMeterHundredths") : (tag.contains("FuelMeterTenths") ? tag.getInt("FuelMeterTenths") * 10 : tag.getInt("FuelMeter") * 100);
		this.burnTime = tag.getInt("BurnTime");
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		if (!this.trySaveLootTable(tag)) {
			ContainerHelper.saveAllItems(tag, this.stacks, registries);
		}
		tag.putInt("FuelMeter", this.getFuelMeter());
		tag.putInt("FuelMeterHundredths", this.fuelMeterHundredths);
		tag.putInt("BurnTime", this.burnTime);
	}

	@Override
	public int getContainerSize() {
		return this.stacks.size();
	}

	@Override
	protected Component getDefaultName() {
		return Component.translatable("block.survival_reimagined.forge");
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.stacks;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> stacks) {
		this.stacks = stacks;
	}

	@Override
	protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
		return new ForgeGUIMenu(id, inventory, this);
	}

	@Override
	public BlockPos getScreenOpeningData(ServerPlayer player) {
		return this.worldPosition;
	}

	public ContainerData getDataAccess() {
		return this.dataAccess;
	}

	public int getFuelMeter() {
		return Math.max(0, Math.min(MAX_FUEL, this.fuelMeterHundredths / 100));
	}

	public int getBurnTime() {
		return this.burnTime;
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, ForgeBlockEntity forge) {
		if (level.isClientSide()) {
			return;
		}

		boolean changed = forge.consumeFuel();
		changed |= forge.processRecipe();

		if (changed) {
			forge.setChanged();
		}
	}

	private boolean consumeFuel() {
		ItemStack fuel = this.getItem(2);
		if (fuel.isEmpty() || this.fuelMeterHundredths >= MAX_FUEL_HUNDREDTHS) {
			return false;
		}

		int fuelValue = getFuelValue(fuel);
		if (fuelValue <= 0) {
			return false;
		}

		if (fuel.is(Items.LAVA_BUCKET)) {
			this.fuelMeterHundredths = MAX_FUEL_HUNDREDTHS;
			this.setItem(2, new ItemStack(Items.BUCKET));
			return true;
		}

		int fuelHundredths = fuelValue * 100;
		if (this.fuelMeterHundredths + fuelHundredths > MAX_FUEL_HUNDREDTHS) {
			return false;
		}

		this.fuelMeterHundredths += fuelHundredths;
		fuel.shrink(1);
		if (fuel.isEmpty()) {
			this.setItem(2, ItemStack.EMPTY);
		}
		return true;
	}

	private boolean processRecipe() {
		Boolean meltResult = this.processMeltRecipe();
		if (meltResult != null) {
			return meltResult;
		}

		ForgeRecipe recipe = findRecipe(this.getItem(0), this.getItem(1));
		if (recipe == null || !this.canOutput(recipe.output(), recipe.outputCount())) {
			if (this.burnTime != 0) {
				this.burnTime = 0;
				return true;
			}
			return false;
		}

		if (this.fuelMeterHundredths < recipe.fuelCostHundredthsPerStep()) {
			if (this.burnTime > 0) {
				this.burnTime = Math.max(0, this.burnTime - 1);
				return true;
			}
			return false;
		}

		this.fuelMeterHundredths -= recipe.fuelCostHundredthsPerStep();
		this.burnTime += 2;

		if (this.burnTime >= MAX_BURN_TIME) {
			if (recipe.consumeFirst() > 0) {
				this.getItem(0).shrink(recipe.consumeFirst());
			}
			if (recipe.consumeSecond() > 0) {
				this.getItem(1).shrink(recipe.consumeSecond());
			}

			ItemStack output = this.getItem(3);
			if (output.isEmpty()) {
				this.setItem(3, new ItemStack(recipe.output(), recipe.outputCount()));
			} else {
				output.grow(recipe.outputCount());
			}

			this.burnTime = 0;
		}

		return true;
	}

	private Boolean processMeltRecipe() {
		ItemStack first = this.getItem(0);
		ItemStack second = this.getItem(1);
		Item output = null;
		int minOutput = 1;
		int maxOutput = 3;

		if (isIronMeltItem(first) && isIronMeltItem(second)) {
			output = SurvivalReimaginedModItems.ROUGH_IRON.get();
		} else if (isGoldMeltItem(first) && isGoldMeltItem(second)) {
			output = SurvivalReimaginedModItems.ROUGH_GOLD.get();
		} else if (isSteelMeltItem(first) && isSteelMeltItem(second)) {
			output = SurvivalReimaginedModItems.ROUGH_STEEL.get();
		} else if (isBronzeMeltItem(first) && isBronzeMeltItem(second)) {
			output = SurvivalReimaginedModItems.ROUGH_BRONZE.get();
		} else if (isDiamondMeltItem(first) && isDiamondMeltItem(second)) {
			output = SurvivalReimaginedModItems.ROUGH_PLATED_DIAMOND.get();
			maxOutput = 2;
		} else {
			return null;
		}

		if (!this.canOutput(output, maxOutput)) {
			if (this.burnTime != 0) {
				this.burnTime = 0;
				return true;
			}
			return false;
		}
		if (this.fuelMeterHundredths < 100) {
			if (this.burnTime > 0) {
				this.burnTime = Math.max(0, this.burnTime - 1);
				return true;
			}
			return false;
		}

		this.fuelMeterHundredths -= 100;
		this.burnTime += 1;
		if (this.burnTime >= MAX_BURN_TIME) {
			first.shrink(1);
			second.shrink(1);
			int produced = minOutput + this.level.random.nextInt(maxOutput - minOutput + 1);
			ItemStack out = this.getItem(3);
			if (out.isEmpty()) this.setItem(3, new ItemStack(output, produced));
			else out.grow(produced);
			this.burnTime = 0;
		}
		return true;
	}

	private static boolean isIronMeltItem(ItemStack s) {
		return s.is(Items.IRON_DOOR) || s.is(Items.IRON_TRAPDOOR) || s.is(Items.IRON_BARS) || s.is(Items.IRON_SWORD)
				|| s.is(Items.IRON_SHOVEL) || s.is(Items.IRON_PICKAXE) || s.is(Items.IRON_AXE) || s.is(Items.IRON_HOE)
				|| s.is(Items.IRON_HELMET) || s.is(Items.IRON_CHESTPLATE) || s.is(Items.IRON_LEGGINGS) || s.is(Items.IRON_BOOTS)
				|| s.is(Items.IRON_HORSE_ARMOR);
	}

	private static boolean isGoldMeltItem(ItemStack s) {
		return s.is(Items.GOLDEN_SWORD) || s.is(Items.GOLDEN_SHOVEL) || s.is(Items.GOLDEN_PICKAXE) || s.is(Items.GOLDEN_AXE)
				|| s.is(Items.GOLDEN_HOE) || s.is(Items.GOLDEN_HELMET) || s.is(Items.GOLDEN_CHESTPLATE) || s.is(Items.GOLDEN_LEGGINGS)
				|| s.is(Items.GOLDEN_BOOTS) || s.is(Items.GOLDEN_HORSE_ARMOR);
	}

	private static boolean isBronzeMeltItem(ItemStack s) {
		return s.is(SurvivalReimaginedModItems.BRONZE_SWORD_BLADE.get()) || s.is(SurvivalReimaginedModItems.BRONZE_PICKAXE_HEAD.get())
				|| s.is(SurvivalReimaginedModItems.BRONZE_AXE_HEAD.get()) || s.is(SurvivalReimaginedModItems.BRONZE_SHOVEL_HEAD.get())
				|| s.is(SurvivalReimaginedModItems.BRONZE_HOE_BLADE.get()) || s.is(SurvivalReimaginedModItems.BRONZE_HAMMER_HEAD.get())
				|| s.is(SurvivalReimaginedModItems.BRONZE_SAW_BLADE.get()) || s.is(SurvivalReimaginedModItems.BRONZE_KNIFE_BLADE.get());
	}

	private static boolean isSteelMeltItem(ItemStack s) {
		return s.is(SurvivalReimaginedModItems.STEEL_SWORD_BLADE.get()) || s.is(SurvivalReimaginedModItems.STEEL_PICKAXE_HEAD.get())
				|| s.is(SurvivalReimaginedModItems.STEEL_AXE_HEAD.get()) || s.is(SurvivalReimaginedModItems.STEEL_SHOVEL_HEAD.get())
				|| s.is(SurvivalReimaginedModItems.STEEL_HOE_BLADE.get()) || s.is(SurvivalReimaginedModItems.STEEL_HAMMER_HEAD.get())
				|| s.is(SurvivalReimaginedModItems.STEEL_SAW_BLADE.get()) || s.is(SurvivalReimaginedModItems.STEEL_KNIFE_BLADE.get());
	}

	private static boolean isDiamondMeltItem(ItemStack s) {
		return s.is(SurvivalReimaginedModItems.DIAMOND_SWORD_BLADE.get()) || s.is(SurvivalReimaginedModItems.DIAMOND_PICKAXE_HEAD.get())
				|| s.is(SurvivalReimaginedModItems.DIAMOND_AXE_HEAD.get()) || s.is(SurvivalReimaginedModItems.DIAMOND_SHOVEL_HEAD.get())
				|| s.is(SurvivalReimaginedModItems.DIAMOND_HOE_BLADE.get()) || s.is(SurvivalReimaginedModItems.DIAMOND_HAMMER_HEAD.get())
				|| s.is(SurvivalReimaginedModItems.DIAMOND_SAW_BLADE.get()) || s.is(SurvivalReimaginedModItems.DIAMOND_KNIFE_BLADE.get())
				|| s.is(Items.DIAMOND_SWORD) || s.is(Items.DIAMOND_SHOVEL) || s.is(Items.DIAMOND_PICKAXE) || s.is(Items.DIAMOND_AXE)
				|| s.is(Items.DIAMOND_HOE) || s.is(Items.DIAMOND_HELMET) || s.is(Items.DIAMOND_CHESTPLATE) || s.is(Items.DIAMOND_LEGGINGS)
				|| s.is(Items.DIAMOND_BOOTS) || s.is(Items.DIAMOND_HORSE_ARMOR);
	}

	private boolean canOutput(Item item, int count) {
		ItemStack output = this.getItem(3);
		if (output.isEmpty()) {
			return count <= item.getDefaultMaxStackSize();
		}
		return output.is(item) && output.getCount() + count <= output.getMaxStackSize();
	}

	private static ForgeRecipe findRecipe(ItemStack first, ItemStack second) {
		if (first.isEmpty() || second.isEmpty()) {
			return null;
		}

		if (first.is(second.getItem())) {
			Item input = first.getItem();
			if (input == Blocks.RAW_IRON_BLOCK.asItem()) {
				return new ForgeRecipe(Blocks.IRON_BLOCK.asItem(), 2, 1, 1, 100);
			}
			if (input == Blocks.RAW_GOLD_BLOCK.asItem()) {
				return new ForgeRecipe(Blocks.GOLD_BLOCK.asItem(), 2, 1, 1, 100);
			}
			if (input == Blocks.RAW_COPPER_BLOCK.asItem()) {
				return new ForgeRecipe(Blocks.COPPER_BLOCK.asItem(), 2, 1, 1, 100);
			}
			if (input == SurvivalReimaginedModBlocks.BLOCK_OF_RAW_TIN.get().asItem()) {
				return new ForgeRecipe(SurvivalReimaginedModBlocks.BLOCK_OF_TIN.get().asItem(), 2, 1, 1, 100);
			}
			if (input == SurvivalReimaginedModBlocks.BLOCK_OF_RAW_MANGANESE.get().asItem()) {
				return new ForgeRecipe(SurvivalReimaginedModBlocks.BLOCK_OF_MANGANESE.get().asItem(), 2, 1, 1, 100);
			}
			if (input == SurvivalReimaginedModBlocks.BLOCK_OF_RAW_TITANIUM.get().asItem()) {
				return new ForgeRecipe(SurvivalReimaginedModBlocks.BLOCK_OF_TITANIUM.get().asItem(), 2, 1, 1, 100);
			}
			if (input == SurvivalReimaginedModBlocks.BLOCK_OF_RAW_URANINITE.get().asItem()) {
				return new ForgeRecipe(SurvivalReimaginedModBlocks.BLOCK_OF_URANIUM.get().asItem(), 2, 1, 1, 100);
			}
		}

		if (isCopperAlloyItem(first) && isTinAlloyItem(second) || isTinAlloyItem(first) && isCopperAlloyItem(second)) {
			return new ForgeRecipe(SurvivalReimaginedModItems.ROUGH_BRONZE.get(), 1, 1, 1, 10);
		}

		if (isIronAlloyItem(first) && isManganeseAlloyItem(second) || isManganeseAlloyItem(first) && isIronAlloyItem(second)) {
			return new ForgeRecipe(SurvivalReimaginedModItems.ROUGH_STEEL.get(), 1, 1, 1, 10);
		}

		if (isDiamondAlloyItem(first) && isSteelAlloyItem(second) || isSteelAlloyItem(first) && isDiamondAlloyItem(second)) {
			return new ForgeRecipe(SurvivalReimaginedModItems.ROUGH_PLATED_DIAMOND.get(), 1, 1, 1, 10);
		}

		// Upstream Netherite alloying is directional: gold in slot 0, netherite material in slot 1.
		if (isGoldAlloyItem(first) && first.getCount() >= 4 && isNetheriteAlloyItem(second) && second.getCount() >= 4) {
			return new ForgeRecipe(SurvivalReimaginedModItems.ROUGH_NETHERITE.get(), 1, 4, 4, 100);
		}

		if (isTitaniumAlloyItem(first) && isUraniumAlloyItem(second) || isUraniumAlloyItem(first) && isTitaniumAlloyItem(second)) {
			return new ForgeRecipe(SurvivalReimaginedModItems.ROUGH_TURANITE.get(), 1, 1, 1, 10);
		}

		if (isLimeItem(first) && isLimeItem(second)) {
			return new ForgeRecipe(SurvivalReimaginedModItems.QUICK_LIME.get(), 2, 1, 1, 100);
		}

		if (first.is(SurvivalReimaginedModItems.DARK_CINDER_POWDER.get()) && second.is(SurvivalReimaginedModItems.ANTHRACITE.get())
				|| second.is(SurvivalReimaginedModItems.DARK_CINDER_POWDER.get()) && first.is(SurvivalReimaginedModItems.ANTHRACITE.get())) {
			return new ForgeRecipe(SurvivalReimaginedModItems.DARK_CINDER_COAL.get(), 2, 1, 1, 100);
		}

		ForgeRecipe rune = findRuneRecipe(first, second);
		if (rune != null) return rune;

		ForgeRecipe plate = findPlateRecipe(first, second);
		if (plate != null) {
			return plate;
		}

		ForgeRecipe toolPart = findToolPartRecipe(first, second);
		if (toolPart != null) {
			return toolPart;
		}

		if (first.is(SurvivalReimaginedModBlocks.INGOT_MOLD.get().asItem())) {
			Item result = getIngotResult(second);
			if (result != null) {
				return new ForgeRecipe(result, 1, 0, 1, 10);
			}
		}
		if (second.is(SurvivalReimaginedModBlocks.INGOT_MOLD.get().asItem())) {
			Item result = getIngotResult(first);
			if (result != null) {
				return new ForgeRecipe(result, 1, 1, 0, 10);
			}
		}

		return null;
	}

	private static ForgeRecipe findRuneRecipe(ItemStack first, ItemStack second) {
		ForgeRecipe r = runeFor(first, second);
		if (r != null) return r;
		r = runeFor(second, first);
		return r == null ? null : new ForgeRecipe(r.output(), r.outputCount(), 0, r.consumeFirst(), r.fuelCostHundredthsPerStep());
	}

	private static ForgeRecipe runeFor(ItemStack metal, ItemStack mold) {
		if (!mold.is(SurvivalReimaginedModBlocks.RUNE_MOLD.get().asItem()) || metal.getCount() < 4) return null;
		if (metal.is(Items.GOLD_INGOT)) return new ForgeRecipe(SurvivalReimaginedModItems.EMPTY_GOLD_RUNE.get(), 1, 5, 0, 50);
		if (metal.is(SurvivalReimaginedModItems.SILVER_INGOT.get())) return new ForgeRecipe(SurvivalReimaginedModItems.EMPTY_SILVER_RUNE.get(), 1, 5, 0, 50);
		return null;
	}

	private static boolean isLimeItem(ItemStack stack) {
		return stack.is(Items.BONE) || stack.is(SurvivalReimaginedModItems.CALCITE_ROCK.get()) || stack.is(SurvivalReimaginedModItems.DIORITE_ROCK.get());
	}

	private static ForgeRecipe findPlateRecipe(ItemStack first, ItemStack second) {
		ForgeRecipe recipe = plateFor(first, second);
		if (recipe != null) return recipe;
		recipe = plateFor(second, first);
		return recipe == null ? null : new ForgeRecipe(recipe.output(), recipe.outputCount(), 0, recipe.consumeFirst(), recipe.fuelCostHundredthsPerStep());
	}

	private static ForgeRecipe plateFor(ItemStack metal, ItemStack mold) {
		if (!mold.is(SurvivalReimaginedModBlocks.METAL_PLATE_MOLD.get().asItem()) || metal.getCount() < 4) return null;
		if (metal.is(SurvivalReimaginedModItems.BRONZE_INGOT.get())) {
			return new ForgeRecipe(SurvivalReimaginedModBlocks.BRONZE_PLATE.get().asItem(), 1, 4, 0, 10);
		}
		if (metal.is(SurvivalReimaginedModItems.STEEL_INGOT.get())) {
			return new ForgeRecipe(SurvivalReimaginedModBlocks.STEEL_PLATE.get().asItem(), 1, 4, 0, 10);
		}
		if (metal.is(SurvivalReimaginedModItems.DIAMOND_PLATED_INGOT.get())) {
			return new ForgeRecipe(SurvivalReimaginedModBlocks.DIAMOND_PLATE.get().asItem(), 1, 4, 0, 10);
		}
		if (metal.is(Items.NETHERITE_INGOT)) {
			return new ForgeRecipe(SurvivalReimaginedModBlocks.NETHERITE_PLATE.get().asItem(), 1, 4, 0, 10);
		}
		return null;
	}

	private static ForgeRecipe findToolPartRecipe(ItemStack first, ItemStack second) {
		ForgeRecipe recipe = toolPartFor(first, second);
		if (recipe != null) {
			return recipe;
		}
		recipe = toolPartFor(second, first);
		return recipe == null ? null : new ForgeRecipe(recipe.output(), recipe.outputCount(), 0, recipe.consumeFirst(), recipe.fuelCostHundredthsPerStep());
	}

	private static ForgeRecipe toolPartFor(ItemStack metal, ItemStack mold) {
		boolean bronze = metal.is(SurvivalReimaginedModItems.BRONZE_INGOT.get());
		boolean steel = metal.is(SurvivalReimaginedModItems.STEEL_INGOT.get());
		boolean diamond = metal.is(SurvivalReimaginedModItems.DIAMOND_PLATED_INGOT.get());
		if (!bronze && !steel && !diamond) {
			return null;
		}

		Item output;
		int count;
		if (mold.is(SurvivalReimaginedModBlocks.SWORD_BLADE_MOLD.get().asItem())) {
			output = diamond ? SurvivalReimaginedModItems.DIAMOND_SWORD_BLADE.get() : (bronze ? SurvivalReimaginedModItems.BRONZE_SWORD_BLADE.get() : SurvivalReimaginedModItems.STEEL_SWORD_BLADE.get());
			count = 2;
		} else if (mold.is(SurvivalReimaginedModBlocks.PICKAXE_HEAD_MOLD.get().asItem())) {
			output = diamond ? SurvivalReimaginedModItems.DIAMOND_PICKAXE_HEAD.get() : (bronze ? SurvivalReimaginedModItems.BRONZE_PICKAXE_HEAD.get() : SurvivalReimaginedModItems.STEEL_PICKAXE_HEAD.get());
			count = 3;
		} else if (mold.is(SurvivalReimaginedModBlocks.AXE_HEAD_MOLD.get().asItem())) {
			output = diamond ? SurvivalReimaginedModItems.DIAMOND_AXE_HEAD.get() : (bronze ? SurvivalReimaginedModItems.BRONZE_AXE_HEAD.get() : SurvivalReimaginedModItems.STEEL_AXE_HEAD.get());
			count = 3;
		} else if (mold.is(SurvivalReimaginedModBlocks.SHOVEL_HEAD_MOLD.get().asItem())) {
			output = diamond ? SurvivalReimaginedModItems.DIAMOND_SHOVEL_HEAD.get() : (bronze ? SurvivalReimaginedModItems.BRONZE_SHOVEL_HEAD.get() : SurvivalReimaginedModItems.STEEL_SHOVEL_HEAD.get());
			count = 1;
		} else if (mold.is(SurvivalReimaginedModBlocks.HOE_HEAD_MOLD.get().asItem())) {
			output = diamond ? SurvivalReimaginedModItems.DIAMOND_HOE_BLADE.get() : (bronze ? SurvivalReimaginedModItems.BRONZE_HOE_BLADE.get() : SurvivalReimaginedModItems.STEEL_HOE_BLADE.get());
			count = 2;
		} else if (mold.is(SurvivalReimaginedModBlocks.HAMMER_HEAD_MOLD.get().asItem())) {
			output = diamond ? SurvivalReimaginedModItems.DIAMOND_HAMMER_HEAD.get() : (bronze ? SurvivalReimaginedModItems.BRONZE_HAMMER_HEAD.get() : SurvivalReimaginedModItems.STEEL_HAMMER_HEAD.get());
			count = 2;
		} else if (mold.is(SurvivalReimaginedModBlocks.SAW_BLADE_MOLD.get().asItem())) {
			output = diamond ? SurvivalReimaginedModItems.DIAMOND_SAW_BLADE.get() : (bronze ? SurvivalReimaginedModItems.BRONZE_SAW_BLADE.get() : SurvivalReimaginedModItems.STEEL_SAW_BLADE.get());
			count = 2;
		} else if (mold.is(SurvivalReimaginedModBlocks.KNIFE_BLADE_MOLD.get().asItem())) {
			output = diamond ? SurvivalReimaginedModItems.DIAMOND_KNIFE_BLADE.get() : (bronze ? SurvivalReimaginedModItems.BRONZE_KNIFE_BLADE.get() : SurvivalReimaginedModItems.STEEL_KNIFE_BLADE.get());
			count = 1;
		} else {
			return null;
		}

		if (metal.getCount() < count) {
			return null;
		}
		// Metal is consumed, fired mold is preserved. Original cost is 0.25 fuel/tick.
		return new ForgeRecipe(output, 1, count, 0, 25);
	}

	private static boolean isTitaniumAlloyItem(ItemStack stack) {
		return stack.is(SurvivalReimaginedModItems.RAW_TITANIUM.get())
				|| stack.is(SurvivalReimaginedModItems.ROUGH_TITANIUM.get())
				|| stack.is(SurvivalReimaginedModItems.TITANIUM_INGOT.get());
	}

	private static boolean isUraniumAlloyItem(ItemStack stack) {
		return stack.is(SurvivalReimaginedModItems.RAW_URANINITE.get())
				|| stack.is(SurvivalReimaginedModItems.ROUGH_URANIUM.get())
				|| stack.is(SurvivalReimaginedModItems.URANIUM_INGOT.get());
	}

	private static boolean isGoldAlloyItem(ItemStack stack) {
		return stack.is(Items.RAW_GOLD) || stack.is(Items.GOLD_INGOT) || stack.is(SurvivalReimaginedModItems.ROUGH_GOLD.get());
	}

	private static boolean isNetheriteAlloyItem(ItemStack stack) {
		return stack.is(Items.NETHERITE_SCRAP) || stack.is(Blocks.ANCIENT_DEBRIS.asItem());
	}

	private static boolean isDiamondAlloyItem(ItemStack stack) {
		return stack.is(Items.DIAMOND);
	}

	private static boolean isSteelAlloyItem(ItemStack stack) {
		return stack.is(SurvivalReimaginedModItems.ROUGH_STEEL.get()) || stack.is(SurvivalReimaginedModItems.STEEL_INGOT.get());
	}

	private static boolean isCopperAlloyItem(ItemStack stack) {
		return stack.is(Items.RAW_COPPER)
				|| stack.is(Items.COPPER_INGOT)
				|| stack.is(SurvivalReimaginedModItems.ROUGH_COPPER.get());
	}

	private static boolean isTinAlloyItem(ItemStack stack) {
		return stack.is(SurvivalReimaginedModItems.RAW_TIN.get())
				|| stack.is(SurvivalReimaginedModItems.TIN_INGOT.get())
				|| stack.is(SurvivalReimaginedModItems.ROUGH_TIN.get());
	}

	private static boolean isIronAlloyItem(ItemStack stack) {
		return stack.is(Items.RAW_IRON)
				|| stack.is(Items.IRON_INGOT)
				|| stack.is(SurvivalReimaginedModItems.ROUGH_IRON.get());
	}

	private static boolean isManganeseAlloyItem(ItemStack stack) {
		return stack.is(SurvivalReimaginedModItems.RAW_MANGANESE.get())
				|| stack.is(SurvivalReimaginedModItems.MANGANESE_INGOT.get())
				|| stack.is(SurvivalReimaginedModItems.ROUGH_MANGANESE.get());
	}

	private static Item getIngotResult(ItemStack stack) {
		if (stack.is(SurvivalReimaginedModItems.ROUGH_BRONZE.get())) {
			return SurvivalReimaginedModItems.BRONZE_INGOT.get();
		}
		if (stack.is(SurvivalReimaginedModItems.ROUGH_STEEL.get())) {
			return SurvivalReimaginedModItems.STEEL_INGOT.get();
		}
		if (stack.is(SurvivalReimaginedModItems.ROUGH_MANGANESE.get())) {
			return SurvivalReimaginedModItems.MANGANESE_INGOT.get();
		}
		if (stack.is(SurvivalReimaginedModItems.ROUGH_TIN.get())) {
			return SurvivalReimaginedModItems.TIN_INGOT.get();
		}
		if (stack.is(SurvivalReimaginedModItems.ROUGH_IRON.get())) {
			return Items.IRON_INGOT;
		}
		if (stack.is(SurvivalReimaginedModItems.ROUGH_GOLD.get())) {
			return Items.GOLD_INGOT;
		}
		if (stack.is(SurvivalReimaginedModItems.ROUGH_COPPER.get())) {
			return Items.COPPER_INGOT;
		}
		if (stack.is(SurvivalReimaginedModItems.ROUGH_PLATED_DIAMOND.get())) {
			return SurvivalReimaginedModItems.DIAMOND_PLATED_INGOT.get();
		}
		if (stack.is(SurvivalReimaginedModItems.ROUGH_NETHERITE.get())) {
			return Items.NETHERITE_INGOT;
		}
		if (stack.is(SurvivalReimaginedModItems.ROUGH_TITANIUM.get())) {
			return SurvivalReimaginedModItems.TITANIUM_INGOT.get();
		}
		if (stack.is(SurvivalReimaginedModItems.ROUGH_URANIUM.get())) {
			return SurvivalReimaginedModItems.URANIUM_INGOT.get();
		}
		if (stack.is(SurvivalReimaginedModItems.ROUGH_TURANITE.get())) {
			return SurvivalReimaginedModItems.TURANITE_INGOT.get();
		}
		return null;
	}

	public static int getFuelValue(ItemStack stack) {
		if (stack.is(Items.LAVA_BUCKET)) {
			return MAX_FUEL;
		}
		if (stack.is(Blocks.COAL_BLOCK.asItem())) {
			return 60;
		}
		if (stack.is(SurvivalReimaginedModItems.LIGINITE.get())) {
			return 5;
		}
		if (stack.is(SurvivalReimaginedModItems.ANTHRACITE.get())) {
			return 20;
		}
		if (stack.is(SurvivalReimaginedModItems.DARK_CINDER_COAL.get())) {
			return 40;
		}
		if (stack.is(SurvivalReimaginedModItems.SMALL_LIGINITE.get())) {
			return 1;
		}
		if (stack.is(SurvivalReimaginedModItems.SMALL_ANTHRACITE.get())) {
			return 4;
		}
		if (stack.is(SurvivalReimaginedModBlocks.LIGINITE_BLOCK.get().asItem())) {
			return 30;
		}
		if (stack.is(SurvivalReimaginedModBlocks.ANTHRACITE_BLOCK.get().asItem())) {
			return 120;
		}
		if (stack.is(Items.COAL) || stack.is(Items.CHARCOAL)) {
			return 10;
		}
		if (stack.is(ItemTags.PLANKS)) {
			return 16;
		}
		return 0;
	}

	@Override
	public boolean canPlaceItem(int index, ItemStack stack) {
		if (index == 3) {
			return false;
		}
		if (index == 2) {
			return getFuelValue(stack) > 0;
		}
		return true;
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		return SLOTS;
	}

	@Override
	public boolean canPlaceItemThroughFace(int index, ItemStack stack, Direction direction) {
		return this.canPlaceItem(index, stack);
	}

	@Override
	public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
		return index > 2;
	}

	@Override
	public void startOpen(Player player) {
		if (!player.isSpectator() && this.openCount++ == 0) {
			this.setOpened(true);
		}
	}

	@Override
	public void stopOpen(Player player) {
		if (!player.isSpectator()) {
			this.openCount = Math.max(0, this.openCount - 1);
			if (this.openCount == 0) {
				this.setOpened(false);
			}
		}
	}

	private void setOpened(boolean opened) {
		if (this.level == null || this.level.isClientSide()) {
			return;
		}

		BlockState state = this.level.getBlockState(this.worldPosition);
		if (!state.is(SurvivalReimaginedModBlocks.FORGE.get()) || !state.hasProperty(ForgeBlock.BLOCKSTATE)) {
			return;
		}

		int value = opened ? 1 : 0;
		if (state.getValue(ForgeBlock.BLOCKSTATE) == value) {
			return;
		}

		this.level.setBlock(this.worldPosition, state.setValue(ForgeBlock.BLOCKSTATE, value), 3);
		this.level.playSound(null, this.worldPosition,
				(opened ? SurvivalReimaginedModSounds.FORGE_OPEN : SurvivalReimaginedModSounds.CLOSE_FORGE).get(),
				SoundSource.BLOCKS, 2.0F, 1.0F);
	}

	private record ForgeRecipe(Item output, int outputCount, int consumeFirst, int consumeSecond, int fuelCostHundredthsPerStep) {
	}
}
