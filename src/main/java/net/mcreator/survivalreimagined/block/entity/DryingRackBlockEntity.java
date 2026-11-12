package net.mcreator.survivalreimagined.block.entity;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlockEntities;
import net.mcreator.survivalreimagined.world.inventory.DryingRackGUIMenu;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.stream.IntStream;

public class DryingRackBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
    private double dryClockSlot0 = 0;
    private double dryClockSlot1 = 0;

    private NonNullList<ItemStack> stacks = NonNullList.withSize(2, ItemStack.EMPTY);

    public DryingRackBlockEntity(BlockPos pos, BlockState state) {
        super(SurvivalReimaginedModBlockEntities.DRYING_RACK.get(), pos, state);
    }

    public double getDryClock(int slot) {
        return slot == 0 ? dryClockSlot0 : dryClockSlot1;
    }

    public void setDryClock(int slot, double value) {
        if (slot == 0) {
            dryClockSlot0 = value;
        } else if (slot == 1) {
            dryClockSlot1 = value;
        }
        setChanged();
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        ContainerHelper.loadAllItems(tag, this.stacks, registries);

        dryClockSlot0 = tag.getDouble("DryClockSlot0");
        dryClockSlot1 = tag.getDouble("DryClockSlot1");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        ContainerHelper.saveAllItems(tag, this.stacks, registries);

        tag.putDouble("DryClockSlot0", dryClockSlot0);
        tag.putDouble("DryClockSlot1", dryClockSlot1);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveWithFullMetadata(registries);
    }

    @Override
    public int getContainerSize() {
        return this.stacks.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : this.stacks) {
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.survival_reimagined.drying_rack");
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.survival_reimagined.drying_rack");
    }

    @Override
    public int getMaxStackSize() {
        return 64;
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return new DryingRackGUIMenu(id, inventory, this.worldPosition);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.stacks;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.stacks = items;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return true;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return IntStream.range(0, this.getContainerSize()).toArray();
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction direction) {
        return this.canPlaceItem(slot, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction direction) {
        return true;
    }

}
