package net.mcreator.survivalreimagined.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.mcreator.survivalreimagined.block.MineralProcessingTableBlock;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlockEntities;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.mcreator.survivalreimagined.world.inventory.MPTGUIMenu;

import java.util.stream.IntStream;

public class MineralProcessingTableBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer, ExtendedScreenHandlerFactory<BlockPos> {
	public static final int CONTAINER_SIZE = 3;
	public static final int MAX_PROGRESS = 300;
	public static final int MAX_ROD_CAPACITY_TENTHS = 24000;
	private static final int[] SLOTS = IntStream.range(0, CONTAINER_SIZE).toArray();

	private NonNullList<ItemStack> stacks = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
	private int progress;
	private int reactorRodCapacityTenths;

	private final ContainerData dataAccess = new ContainerData() {
		@Override
		public int get(int index) {
			return switch (index) {
				case 0 -> MineralProcessingTableBlockEntity.this.progress;
				case 1 -> MineralProcessingTableBlockEntity.this.reactorRodCapacityTenths / 10;
				case 2 -> MineralProcessingTableBlockEntity.this.isPowered() ? 1 : 0;
				default -> 0;
			};
		}

		@Override
		public void set(int index, int value) {
			switch (index) {
				case 0 -> MineralProcessingTableBlockEntity.this.progress = value;
				case 1 -> MineralProcessingTableBlockEntity.this.reactorRodCapacityTenths = value * 10;
				default -> {
				}
			}
		}

		@Override
		public int getCount() {
			return 3;
		}
	};

	public MineralProcessingTableBlockEntity(BlockPos pos, BlockState state) {
		super(SurvivalReimaginedModBlockEntities.MINERAL_PROCESSING_TABLE.get(), pos, state);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		if (!this.tryLoadLootTable(tag)) {
			ContainerHelper.loadAllItems(tag, this.stacks, registries);
		}
		this.progress = tag.getInt("MineralProcessing");
		this.reactorRodCapacityTenths = tag.getInt("ReactorRodCapacityTenths");
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		if (!this.trySaveLootTable(tag)) {
			ContainerHelper.saveAllItems(tag, this.stacks, registries);
		}
		tag.putInt("MineralProcessing", this.progress);
		tag.putInt("ReactorRodCapacityTenths", this.reactorRodCapacityTenths);
	}

	@Override
	public int getContainerSize() {
		return CONTAINER_SIZE;
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
	protected Component getDefaultName() {
		return Component.translatable("block.survival_reimagined.mineral_processing_table");
	}

	@Override
	protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
		return new MPTGUIMenu(id, inventory, this);
	}

	@Override
	public BlockPos getScreenOpeningData(ServerPlayer player) {
		return this.worldPosition;
	}

	public ContainerData getDataAccess() {
		return this.dataAccess;
	}

	public static boolean isReactorRod(ItemStack stack) {
		return stack.is(SurvivalReimaginedModItems.REACTOR_ROD.get());
	}

	public static boolean isDepletedRod(ItemStack stack) {
		return stack.is(SurvivalReimaginedModItems.DEPLETED_REACTOR_ROD.get());
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, MineralProcessingTableBlockEntity table) {
		if (level.isClientSide()) return;

		boolean changed = false;
		boolean powered = state.hasProperty(MineralProcessingTableBlock.POWERED) && state.getValue(MineralProcessingTableBlock.POWERED);
		ItemStack rod = table.getItem(1);
		Item result = getProcessingResult(table.getItem(0));

		if (isReactorRod(rod) && table.reactorRodCapacityTenths <= 0 && powered) {
			table.reactorRodCapacityTenths = MAX_ROD_CAPACITY_TENTHS;
			changed = true;
		}

		if (!powered || result == null || !isReactorRod(rod) || !table.canOutput(result)) {
			if (table.progress != 0) {
				table.progress = 0;
				changed = true;
			}
		} else {
			table.progress += 5;
			table.reactorRodCapacityTenths = Math.max(0, table.reactorRodCapacityTenths - 1);
			changed = true;

			if (table.progress >= MAX_PROGRESS) {
				table.getItem(0).shrink(1);
				ItemStack output = table.getItem(2);
				if (output.isEmpty()) table.setItem(2, new ItemStack(result));
				else output.grow(1);
				table.progress = 0;
			}

			if (table.reactorRodCapacityTenths <= 0) {
				table.setItem(1, new ItemStack(SurvivalReimaginedModItems.DEPLETED_REACTOR_ROD.get()));
				table.progress = 0;
			}
		}

		if (changed) table.setChanged();
	}

	private boolean isPowered() {
		if (this.level == null) return false;
		BlockState state = this.level.getBlockState(this.worldPosition);
		return state.hasProperty(MineralProcessingTableBlock.POWERED) && state.getValue(MineralProcessingTableBlock.POWERED);
	}

	private boolean canOutput(Item result) {
		ItemStack output = this.getItem(2);
		return output.isEmpty() || output.is(result) && output.getCount() < output.getMaxStackSize();
	}

	private static Item getProcessingResult(ItemStack input) {
		if (input.is(SurvivalReimaginedModItems.ROUGH_SAPPHIRE.get())) return SurvivalReimaginedModItems.SAPPHIRE.get();
		if (input.is(SurvivalReimaginedModItems.ROUGH_RUBY.get())) return SurvivalReimaginedModItems.RUBY.get();
		if (input.is(SurvivalReimaginedModItems.ROUGH_AMBER.get())) return SurvivalReimaginedModItems.AMBER.get();
		return null;
	}

	@Override
	public boolean canPlaceItem(int index, ItemStack stack) {
		if (index == 2) return false;
		if (index == 1) return isReactorRod(stack) && this.getItem(1).isEmpty();
		return getProcessingResult(stack) != null;
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
		return index == 2 || index == 1 && isDepletedRod(stack);
	}
}
