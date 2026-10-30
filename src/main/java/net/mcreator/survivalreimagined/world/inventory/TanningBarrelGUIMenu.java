package net.mcreator.survivalreimagined.world.inventory;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class TanningBarrelGUIMenu extends AbstractContainerMenu {
    public final Map<String, Object> menuState = new HashMap<>() {
        @Override
        public Object put(String key, Object value) {
            if (!this.containsKey(key) && this.size() >= 24) return null;
            return super.put(key, value);
        }
    };
    public final Level world;
    public final Player entity;
    public int x,y,z;
    private ContainerLevelAccess access = ContainerLevelAccess.NULL;
    private Container internal;
    private final Map<Integer, Slot> customSlots = new HashMap<>();
    private boolean bound = false;
    private Supplier<Boolean> boundItemMatcher = null;
    private Entity boundEntity = null;
    private BlockEntity boundBlockEntity = null;

    public TanningBarrelGUIMenu(int id, Inventory inv, BlockPos pos) {
        super(SurvivalReimaginedModMenus.TANNING_BARREL_GUI.get(), id);
        this.entity = inv.player;
        this.world = inv.player.level();
        this.internal = new SimpleContainer(2);
        if (pos != null) {
            this.x = pos.getX();
            this.y = pos.getY();
            this.z = pos.getZ();
            this.access = ContainerLevelAccess.create(world, pos);

            this.boundBlockEntity = world.getBlockEntity(pos);
            if (boundBlockEntity instanceof Container blockContainer) {
                this.internal = blockContainer;
                this.bound = true;
            }
        }

        this.customSlots.put(0, this.addSlot(new Slot(internal, 0, 8, 66) {
            @Override
            public boolean mayPickup(Player entity) {
                return true;
            }
            @Override
            public boolean mayPlace(ItemStack stack) {
                return true;
            }
        }));

        this.customSlots.put(1, this.addSlot(new Slot(internal, 1, 80, 32) {
            @Override
            public int getMaxStackSize() {
                return 6;
            }

            @Override
            public boolean mayPickup(Player entity) {
                return true;
            }

            @Override
            public boolean mayPlace(ItemStack stack) {
               return true;
           }
        }));

        for (int row = 0; row < 3; ++row)
            for (int col = 0; col < 9; ++col)
                this.addSlot(new Slot(inv, col + (row + 1) * 9, 8 + col * 18, 84 + row * 18));

        for (int row = 0; row < 9; ++row)
            this.addSlot(new Slot(inv, row, 8 + row * 18, 142));
    }

    @Override
    public boolean stillValid(Player player) {
        if (this.bound) {
            if (this.boundItemMatcher != null)
                return this.boundItemMatcher.get();
            else if (this.boundBlockEntity != null)
                return AbstractContainerMenu.stillValid(this.access, player, this.boundBlockEntity.getBlockState().getBlock());
            else if (this.boundEntity != null)
                return this.boundEntity.isAlive();
        }
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot) this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemStack1 = slot.getItem();
            itemStack = itemStack1.copy();
            if (index < 2) {
                if (!this.moveItemStackTo(itemStack1, 2, this.slots.size(), true))
                    return ItemStack.EMPTY;
                slot.onQuickCraft(itemStack1, itemStack);
            } else if (!this.moveItemStackTo(itemStack1, 0, 2, false)) {
                if (index < 2 + 27) {
                    if (!this.moveItemStackTo(itemStack1, 2 + 27, this.slots.size(), true))
                        return ItemStack.EMPTY;
                } else {
                    if (!this.moveItemStackTo(itemStack1, 2, 2 + 27, false))
                        return ItemStack.EMPTY;
                }
                return ItemStack.EMPTY;
            }
            if (itemStack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (itemStack1.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(playerIn, itemStack1);
        }
        return itemStack;
    }

    @Override
    public void removed(Player playerIn) {
        super.removed(playerIn);
        if (!bound) {
            for (int i = 0; i < internal.getContainerSize(); ++i) {
                playerIn.getInventory().placeItemBackInInventory(internal.getItem(i));
                internal.setItem(i, ItemStack.EMPTY);
            }
        }
    }

    public Map<Integer, Slot> getSlots() {
        return Collections.unmodifiableMap(customSlots);
    }

    public Map<String, Object> getMenuState() {
        return menuState;
    }
}
