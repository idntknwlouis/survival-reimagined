package net.mcreator.survivalreimagined.block.entity;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.stream.IntStream;

public class CrudeTorchBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
    private NonNullList<ItemStack> stacks = NonNullList.withSize(9, ItemStack.EMPTY);
    private int burnTarget = 0;
    private int burnClock = 0;

    public CrudeTorchBlockEntity(BlockPos pos, BlockState state) {
        super(SurvivalReimaginedModBlockEntities.CRUDE_TORCH.get(), pos, state);
    }

    @Override
    public void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider lookupProvider) {
        super.loadAdditional(compoundTag, lookupProvider);

        if (!this.tryLoadLootTable(compoundTag)) this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compoundTag, this.stacks, lookupProvider);
        this.burnTarget = compoundTag.getInt("BurnTarget");
        this.burnClock= compoundTag.getInt("BurnClock");
    }

    @Override
    public void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider lookupProvider) {
        super.saveAdditional(compoundTag, lookupProvider);

        if (!this.trySaveLootTable(compoundTag)) ContainerHelper.saveAllItems(compoundTag, this.stacks, lookupProvider);
        compoundTag.putInt("BurnTarget", this.burnTarget);
        compoundTag.putInt("BurnClock", this.burnClock);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider lookupProvider) {
        return this.saveWithFullMetadata(lookupProvider);
    }

    public int getBurnClock() {
        return burnClock;
    }

    public void setBurnClock(int burnClock) {
        this.burnClock = burnClock;
        setChanged();
    }

    public int getBurnTarget() {
        return burnTarget;
    }

    public void setBurnTarget(int burnTarget) {
        this.burnTarget = burnTarget;
        setChanged();
    }

    @Override
    public int getContainerSize() {
        return this.stacks.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.stacks) {
            if (!itemStack.isEmpty()) return false;
        }
        return true;
    }

    @Override
    public Component getDefaultName() {
        return Component.literal("crude_torch");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return ChestMenu.threeRows(id, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Crude Torch");
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
    public boolean canPlaceItem(int index, ItemStack stack) {
        return true;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return IntStream.range(0, this.getContainerSize()).toArray();
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
        return this.canPlaceItem(index, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return true;
    }
}
