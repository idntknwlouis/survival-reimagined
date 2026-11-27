package net.mcreator.survivalreimagined.world.inventory;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModMenus;
import net.mcreator.survivalreimagined.util.BackpackMenuData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public class BackpackGUI1Menu extends AbstractContainerMenu {
    private static final int INTERNAL_SIZE = 9;
    private static final int PLAYER_INV_START = 9;
    private static final int PLAYER_INV_END = 36;
    private static final int HOTBAR_START = 36;
    private static final int MENU_SLOT_COUNT = 45;

    public final Map<String, Object> menuState = new HashMap<>() {
        @Override
        public Object put(String key, Object value) {
            if (!this.containsKey(key) && this.size() >= 46) return null;
            return super.put(key, value);
        }
    };

    public final Level world;
    public final Player entity;
    public int x,y,z;

    private ContainerLevelAccess access = ContainerLevelAccess.NULL;
    private Container internal;
    private final UUID backpackUUID;
    private final Map<Integer, Slot> customSlots = new HashMap<>();
    private boolean bound;
    private Supplier<Boolean> boundItemMatcher;
    private Entity boundEntity;
    private BlockEntity boundBlockEntity;

    public BackpackGUI1Menu(int id, Inventory inv, BackpackMenuData data) {
        super(SurvivalReimaginedModMenus.BACKPACK_GUI_1.get(), id);

        BlockPos pos = data.pos();
        ItemStack backpackStack = data.stack();
        this.backpackUUID = BackpackIdentity.getOrCreate(backpackStack);

        this.entity = inv.player;
        this.world = inv.player.level();
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        this.internal = new SimpleContainer(INTERNAL_SIZE);
        this.access = ContainerLevelAccess.create(world, pos);

        this.internal = new ItemComponentContainer(backpackStack, INTERNAL_SIZE);
        this.bound = true;

        addInternalSlot(0,8,21);
        addInternalSlot(1,26,21);
        addInternalSlot(2,44,21);
        addInternalSlot(3,62,21);
        addInternalSlot(4,80,21);
        addInternalSlot(5,98,21);
        addInternalSlot(6,116,21);
        addInternalSlot(7,134,21);
        addInternalSlot(8,152,21);

        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                addSlot(new Slot(inv, column + (row + 1) * 9, 8 + column * 18, 84 + row * 18));
            }
        }

        for (int column = 0; column < 9; ++column) {
            addSlot(new Slot(inv, column, 8 + column * 18, 142));
        }
    }

    private void addInternalSlot(int index, int x, int y) {
        this.customSlots.put(index, this.addSlot(new Slot(this.internal, index,x,y)));
    }

    @Override
    public boolean stillValid(Player player) {
        boolean backpackHeld = BackpackIdentity.hasUUID(player.getMainHandItem(), backpackUUID) || BackpackIdentity.hasUUID(player.getOffhandItem(), backpackUUID);
        if (!backpackHeld) return false;

        if (!bound) return true;
        if (boundItemMatcher != null) return boundItemMatcher.get();
        if (boundBlockEntity != null) return !boundBlockEntity.isRemoved() && AbstractContainerMenu.stillValid(access, player, boundBlockEntity.getBlockState().getBlock());
        if (boundEntity != null) return boundEntity.isAlive() && boundEntity instanceof Container;
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        if (index < 0 || index >= slots.size()) return ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (slot == null || !slot.hasItem()) return ItemStack.EMPTY;

        ItemStack source = slot.getItem();
        ItemStack original = source.copy();

        if (index < INTERNAL_SIZE) {
            if (!moveItemStackTo(source, PLAYER_INV_START, MENU_SLOT_COUNT, true)) return ItemStack.EMPTY;
            slot.onQuickCraft(source, original);
        } else if (!moveItemStackTo(source, 0, INTERNAL_SIZE, false)) {
            if (index < PLAYER_INV_END) {
                if (!moveItemStackTo(source, HOTBAR_START, MENU_SLOT_COUNT, false)) return ItemStack.EMPTY;
            }
        } else {
            if (!moveItemStackTo(source, PLAYER_INV_START, PLAYER_INV_END, false)) return ItemStack.EMPTY;
        }

        if (source.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        if (source.getCount() == original.getCount()) return ItemStack.EMPTY;
        slot.onTake(player, source);
        return original;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);

        if (!bound && player instanceof ServerPlayer serverPlayer) {
            for (int slot = 0;
                 slot < internal.getContainerSize(); ++slot) {
                ItemStack stack = internal.getItem(slot);

                if (stack.isEmpty()) continue;

                if (!serverPlayer.isAlive() || serverPlayer.hasDisconnected()) {
                    player.drop(stack, false);
                } else {
                    player.getInventory().placeItemBackInInventory(stack);
                }
                internal.setItem(slot, ItemStack.EMPTY);
            }
            internal.setChanged();
        }
    }

    public Map<Integer, Slot> getSlots() {
        return Collections.unmodifiableMap(customSlots);
    }

    public Map<String, Object> getMenuState() {
        return menuState;
    }
    private static final class ItemComponentContainer
            implements Container {

        private final ItemStack parent;
        private final int size;
        private final NonNullList<ItemStack> items;

        private ItemComponentContainer(ItemStack parent, int size) {
            this.parent = parent;
            this.size = size;

            this.items = NonNullList.withSize(
                    size, ItemStack.EMPTY);

            ItemContainerContents contents = parent.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
            contents.copyInto(this.items);
            setChanged();
        }

        private void writeItems() {
            parent.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(items));
        }

        @Override
        public int getContainerSize() {
            return size;
        }

        @Override
        public boolean isEmpty() {
            for (ItemStack stack : items) {
                if (!stack.isEmpty()) return false;
            }
            return true;
        }

        @Override
        public ItemStack getItem(int slot) {
            return items.get(slot);
        }

        @Override
        public ItemStack removeItem(int slot, int amount) {
            ItemStack result = ContainerHelper.removeItem(items, slot, amount);

            if (!result.isEmpty()) setChanged();
            return result;
        }

        @Override
        public ItemStack removeItemNoUpdate(int slot) {
            ItemStack result = items.set(slot, ItemStack.EMPTY);

            if (!result.isEmpty()) setChanged();
            return result;
        }

        @Override
        public void setItem(int slot, ItemStack stack) {
            items.set(slot, stack);

            if (!stack.isEmpty() && stack.getCount() > getMaxStackSize()) {
                stack.setCount(getMaxStackSize());
            }
            setChanged();
        }

        @Override
        public void setChanged() {
            writeItems();
        }

        @Override
        public boolean stillValid(Player player) {
            return !parent.isEmpty();
        }

        @Override
        public boolean canPlaceItem(int slot, ItemStack stack) {
            return true;
        }

        @Override
        public void clearContent() {
            for (int slot = 0; slot < size; slot++) {
                items.set(slot, ItemStack.EMPTY);
            }
            setChanged();
        }
    }
}
