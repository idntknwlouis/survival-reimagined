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
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.mcreator.survivalreimagined.block.ForgeBlock;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlockEntities;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModSounds;
import net.mcreator.survivalreimagined.world.inventory.ForgeGUIMenu;

import java.util.stream.IntStream;

public class ForgeBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer, ExtendedScreenHandlerFactory<BlockPos> {
	public static final int CONTAINER_SIZE = 9;
	private static final int[] SLOTS = IntStream.range(0, CONTAINER_SIZE).toArray();

	private NonNullList<ItemStack> stacks = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
	private int openCount;

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
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		if (!this.trySaveLootTable(tag)) {
			ContainerHelper.saveAllItems(tag, this.stacks, registries);
		}
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

	@Override
	public boolean canPlaceItem(int index, ItemStack stack) {
		return index != 3;
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
}
