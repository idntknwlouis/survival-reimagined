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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlockEntities;
import net.mcreator.survivalreimagined.world.inventory.RMIMenu;

import java.util.stream.IntStream;

public class RuneMagicInfuserBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer, ExtendedScreenHandlerFactory<BlockPos> {
	public static final int CONTAINER_SIZE = 3;
	private static final int[] SLOTS = IntStream.range(0, CONTAINER_SIZE).toArray();
	private NonNullList<ItemStack> stacks = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);

	public RuneMagicInfuserBlockEntity(BlockPos pos, BlockState state) {
		super(SurvivalReimaginedModBlockEntities.RUNE_MAGIC_INFUSER.get(), pos, state);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		this.stacks = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
		if (!this.tryLoadLootTable(tag)) ContainerHelper.loadAllItems(tag, this.stacks, registries);
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		if (!this.trySaveLootTable(tag)) ContainerHelper.saveAllItems(tag, this.stacks, registries);
	}

	@Override public int getContainerSize() { return CONTAINER_SIZE; }
	@Override protected NonNullList<ItemStack> getItems() { return this.stacks; }
	@Override protected void setItems(NonNullList<ItemStack> stacks) { this.stacks = stacks; }
	@Override protected Component getDefaultName() { return Component.translatable("block.survival_reimagined.rune_magic_infuser"); }
	@Override protected AbstractContainerMenu createMenu(int id, Inventory inventory) { return new RMIMenu(id, inventory, this); }
	@Override public BlockPos getScreenOpeningData(ServerPlayer player) { return this.worldPosition; }

	@Override
	public boolean canPlaceItem(int index, ItemStack stack) {
		return switch(index) {
			case 0 -> RMIMenu.isInfusable(stack);
			case 1 -> RMIMenu.isRune(stack);
			case 2 -> stack.is(net.minecraft.world.item.Items.LAPIS_LAZULI);
			default -> false;
		};
	}

	@Override public int[] getSlotsForFace(Direction side) { return SLOTS; }
	@Override public boolean canPlaceItemThroughFace(int index, ItemStack stack, Direction direction) { return canPlaceItem(index, stack); }
	@Override public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) { return true; }
}
