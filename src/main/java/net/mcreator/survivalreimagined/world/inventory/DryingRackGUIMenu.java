package net.mcreator.survivalreimagined.world.inventory;

import net.mcreator.survivalreimagined.client.gui.DryingRackGUIScreen;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DryingRackGUIMenu extends AbstractContainerMenu {
    public final Map<String, Object> menuState = new HashMap<>() {
        @Override
        public Object put (String key, Object value) {
            if (!containsKey(key) && size() >= 3) {
                return null;
            }
            return super.put(key, value);
        }
    };

    public final Level world;
    public final Player entity;
    public final int x;
    public final int y;
    public final int z;

    private final Container rackInventory;
    private final ContainerLevelAccess access;
    private final Map<Integer, Slot> customSlots = new HashMap<>();


    public DryingRackGUIMenu(int id, Inventory inventory, BlockPos pos) {
        super(SurvivalReimaginedModMenus.DRYING_RACK_GUI.get(), id);
        this.entity = inventory.player;
        this.world = inventory.player.level();
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        this.access = ContainerLevelAccess.create(world, pos);

        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof Container container && container.getContainerSize() >= 2) {
            this.rackInventory = container;
        } else {
            this.rackInventory = new SimpleContainer(2);
        }

        this.customSlots.put(0, addSlot(new Slot(rackInventory, 0, 71, 39) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(SurvivalReimaginedModItems.HIDE.get());
            }
        }));
        this.customSlots.put(1, addSlot(new Slot(rackInventory, 1, 89, 39) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(SurvivalReimaginedModItems.HIDE.get());
            }
        }));

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(inventory, column + (row + 1) * 9, 8 + column * 18, 84 + row * 18));
            }
        }
        for (int column = 0; column < 9; column++) {
            addSlot(new Slot(inventory, column, 8 + column * 18, 142));
        }
    }
    @Override
    public boolean stillValid(Player player) {
        return AbstractContainerMenu.stillValid(access, player, world.getBlockState(new BlockPos(x, y, z)).getBlock());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            result = stack.copy();
            if (index < 2) {
                if (!moveItemStackTo(stack, 2, slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(stack, result);
            } else if (!moveItemStackTo(stack, 0, 2, false)) {
                return ItemStack.EMPTY;
            }
            if (stack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (stack.getCount() == result.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, stack);
        }
        return result;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        if (rackInventory instanceof SimpleContainer) {
            clearContainer(player, rackInventory);
        }
    }
    public Map<Integer, Slot> getSlots() {
        return Collections.unmodifiableMap(customSlots);
    }

    public Map<String, Object> getMenuState() {
        return menuState;
    }
}
