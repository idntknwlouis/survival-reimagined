package net.mcreator.survivalreimagined.block.fertilesoil;

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
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.stream.IntStream;

public class FertilitySoilBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer, FertilitySoilTickUpdate.CustomDataHolder {
    private NonNullList<ItemStack> stacks = NonNullList.withSize(9, ItemStack.EMPTY);

    private final CompoundTag persistentData = new CompoundTag();

    public int fertilizerClockPrimitive = 0;

    public FertilitySoilBlockEntity(BlockEntityType<?> type, BlockPos position, BlockState state) {
        super(type, position, state);
    }

    @Override
    public CompoundTag getPersistentData() {
        return this.persistentData;
    }

    @Override
    protected void loadAdditional(CompoundTag compound, HolderLookup.Provider lookupProvider) {
        super.loadAdditional(compound, lookupProvider);
        if (!this.tryLoadLootTable(compound)) {
            this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        }
        ContainerHelper.loadAllItems(compound, this.stacks, lookupProvider);

        for (String key : new java.util.HashSet<>(this.persistentData.getAllKeys())) {
            this.persistentData.remove(key);
        }

        if (compound.contains("N")) this.persistentData.putDouble("N", compound.getDouble("N"));
        if (compound.contains("P")) this.persistentData.putDouble("P", compound.getDouble("P"));
        if (compound.contains("K")) this.persistentData.putDouble("K", compound.getDouble("K"));
        if (compound.contains("MoistureCheck")) this.persistentData.putDouble("MoistureCheck", compound.getDouble("MoistureCheck"));
        if (compound.contains("MoistureClock")) this.persistentData.putDouble("MoistureClock", compound.getDouble("MoistureClock"));
        if (compound.contains("FertilizerClock")) this.persistentData.putDouble("FertilizerClock", compound.getDouble("FertilizerClock"));
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider lookupProvider) {
        super.saveAdditional(compound, lookupProvider);
        if (!this.trySaveLootTable(compound)) {
            ContainerHelper.saveAllItems(compound, this.stacks, lookupProvider);
        }
        compound.putDouble("N", this.persistentData.getDouble("N"));
        compound.putDouble("P", this.persistentData.getDouble("P"));
        compound.putDouble("K", this.persistentData.getDouble("K"));
        compound.putDouble("MoistureCheck", this.persistentData.getDouble("MoistureCheck"));
        compound.putDouble("MoistureClock", this.persistentData.getDouble("MoistureClock"));
        compound.putDouble("FertilizerClock", this.persistentData.getDouble("FertilizerClock"));
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider lookupProvider) {
        CompoundTag tag = super.getUpdateTag(lookupProvider);

        tag.putDouble("N", this.persistentData.getDouble("N"));
        tag.putDouble("P", this.persistentData.getDouble("P"));
        tag.putDouble("K", this.persistentData.getDouble("K"));
        tag.putDouble("MoistureCheck", this.persistentData.getDouble("MoistureCheck"));
        tag.putDouble("MoistureClock", this.persistentData.getDouble("MoistureClock"));
        tag.putDouble("FertilizerClock", this.persistentData.getDouble("FertilizerClock"));

        return tag;
    }

    @Override
    public int getContainerSize() {
        return this.stacks.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.stacks) {
            if (!itemstack.isEmpty()) return false;
        }
        return true;
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable(this.getBlockState().getBlock().getDescriptionId());
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
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
        return IntStream.range(0, this.getContainerSize()).toArray();
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
