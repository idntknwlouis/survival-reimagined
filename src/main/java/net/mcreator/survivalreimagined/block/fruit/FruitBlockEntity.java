package net.mcreator.survivalreimagined.block.fruit;

import net.minecraft.core.*;
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

public class FruitBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
    private NonNullList<ItemStack> stacks = NonNullList.withSize(9, ItemStack.EMPTY);
    private final FruitTypeConfig config;

    public FruitBlockEntity(BlockPos position, BlockState state, FruitTypeConfig config) {
        super(config.blockEntityType().get(), position, state);
        this.config = config;
    }
    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider lookupProvider) {
        super.loadAdditional(compound, lookupProvider);
        if (!this.tryLoadLootTable(compound)) {
            this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        }
        ContainerHelper.loadAllItems(compound, this.stacks, lookupProvider);
    }
    @Override
    public void saveAdditional(CompoundTag compound, HolderLookup.Provider lookupProvider) {
        super.saveAdditional(compound, lookupProvider);
        if (!this.trySaveLootTable(compound)) {
            ContainerHelper.saveAllItems(compound, this.stacks, lookupProvider);
        }
    }
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider lookupProvider) {
        return this.saveWithFullMetadata(lookupProvider);
    }
    @Override
    public int getContainerSize() {
        return 9;
    }
    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.stacks) {
            if (!itemstack.isEmpty()) return false;
        }
        return true;
    }
    @Override
    public Component getDefaultName() {
        return Component.literal(config.name() + "_fruit");
    }
    @Override
    public Component getDisplayName() {
        String name = config.name();
        return Component.literal(name.substring(0, 1).toUpperCase() + name.substring(1));
    }
    @Override
    public int getMaxStackSize() {
        return 64;
    }
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return ChestMenu.threeRows(id, inventory);
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
        return IntStream.range(0,this.getContainerSize()).toArray();
    }
    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack itemstack, @Nullable Direction direction) {
        return this.canPlaceItem(index, itemstack);
    }
    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack itemstack, Direction direction) {
        return true;
    }
}
