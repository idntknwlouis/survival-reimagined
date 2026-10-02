package net.mcreator.survivalreimagined.world.inventory;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import net.mcreator.survivalreimagined.block.entity.MillstoneBlockEntity;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModMenus;

public class MillstoneGUIMenu extends AbstractContainerMenu {
    private static final int MILLSTONE_SLOT_COUNT = 2;
    private static final int PLAYER_INVENTORY_END = MILLSTONE_SLOT_COUNT + 27;
    private final Container container;
    private final ContainerData data;
    private final BlockPos blockPos;

    public MillstoneGUIMenu(int id, Inventory inventory, BlockPos pos) {
        this(id, inventory, new SimpleContainer(MillstoneBlockEntity.CONTAINER_SIZE), new SimpleContainerData(1), pos);
    }

    public MillstoneGUIMenu(int id, Inventory inventory, MillstoneBlockEntity millstone) {
        this(id, inventory, millstone, millstone.getDataAccess(), millstone.getBlockPos());
    }

    private MillstoneGUIMenu(int id, Inventory inventory, Container container, ContainerData data, BlockPos pos) {
        super(SurvivalReimaginedModMenus.MILLSTONE_GUI.get(), id);
        checkContainerSize(container, MillstoneBlockEntity.CONTAINER_SIZE);
        checkContainerDataCount(data, 1);
        this.container = container;
        this.data = data;
        this.blockPos = pos;
        this.container.startOpen(inventory.player);

        this.addSlot(new Slot(container, MillstoneBlockEntity.INPUT_SLOT, 80, 21) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return MillstoneBlockEntity.isMillingInput(stack);
            }
        });
        this.addSlot(new Slot(container, MillstoneBlockEntity.OUTPUT_SLOT, 80, 66) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });

        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(inventory, column + (row + 1) * 9, 8 + column * 18, 84 + row * 18));
            }
        }
        for (int column = 0; column < 9; ++column) {
            this.addSlot(new Slot(inventory, column, 8 + column * 18, 142));
        }

        this.addDataSlots(data);
    }

    public BlockPos getBlockPos() {
        return this.blockPos;
    }

    public int getProgress() {
        return this.data.get(0);
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot == null || !slot.hasItem()) return result;

        ItemStack stack = slot.getItem();
        result = stack.copy();

        if (index < MILLSTONE_SLOT_COUNT) {
            if (!this.moveItemStackTo(stack, MILLSTONE_SLOT_COUNT, this.slots.size(), true)) return ItemStack.EMPTY;
        } else if (MillstoneBlockEntity.isMillingInput(stack)) {
            if (!this.moveItemStackTo(stack, 0, 1, false)) return ItemStack.EMPTY;
        } else if (index < PLAYER_INVENTORY_END) {
            if (!this.moveItemStackTo(stack, PLAYER_INVENTORY_END, this.slots.size(), false)) return ItemStack.EMPTY;
        } else if (!this.moveItemStackTo(stack, MILLSTONE_SLOT_COUNT, PLAYER_INVENTORY_END, false)) {
            return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) slot.setByPlayer(ItemStack.EMPTY);
        else slot.setChanged();
        if (stack.getCount() == result.getCount()) return ItemStack.EMPTY;
        slot.onTake(player, stack);
        return result;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.container.stopOpen(player);
    }
}
