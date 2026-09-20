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

import net.mcreator.survivalreimagined.block.entity.AdvancedAlloyForgeBlockEntity;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModMenus;

public class AdvancedAlloyForgeGUIMenu extends AbstractContainerMenu {
	private static final int MACHINE_SLOTS = 8;
	private static final int PLAYER_INV_END = MACHINE_SLOTS + 27;
	private final Container container;
	private final ContainerData data;
	private final BlockPos blockPos;

	public AdvancedAlloyForgeGUIMenu(int id, Inventory inventory, BlockPos pos) {
		this(id, inventory, new SimpleContainer(AdvancedAlloyForgeBlockEntity.CONTAINER_SIZE), new SimpleContainerData(3), pos);
	}

	public AdvancedAlloyForgeGUIMenu(int id, Inventory inventory, AdvancedAlloyForgeBlockEntity forge) {
		this(id, inventory, forge, forge.getDataAccess(), forge.getBlockPos());
	}

	private AdvancedAlloyForgeGUIMenu(int id, Inventory inventory, Container container, ContainerData data, BlockPos pos) {
		super(SurvivalReimaginedModMenus.ADVANCED_ALLOY_FORGE_GUI.get(), id);
		checkContainerSize(container, AdvancedAlloyForgeBlockEntity.CONTAINER_SIZE);
		checkContainerDataCount(data, 3);
		this.container = container;
		this.data = data;
		this.blockPos = pos;
		container.startOpen(inventory.player);

		this.addSlot(new Slot(container, 0, 134, 39) {
			@Override public boolean mayPlace(ItemStack stack) { return false; }
		});
		this.addSlot(new Slot(container, 1, 71, 39) {
			@Override public boolean mayPlace(ItemStack stack) { return AdvancedAlloyForgeBlockEntity.isAlloyInput(stack); }
		});
		this.addSlot(new Slot(container, 2, 89, 39) {
			@Override public boolean mayPlace(ItemStack stack) { return AdvancedAlloyForgeBlockEntity.isAlloyInput(stack); }
		});
		this.addSlot(new Slot(container, 3, 8, 57) {
			@Override public boolean mayPlace(ItemStack stack) { return AdvancedAlloyForgeBlockEntity.isReactorRod(stack) && !this.hasItem(); }
			@Override public boolean mayPickup(Player player) { return !this.hasItem() || AdvancedAlloyForgeBlockEntity.isDepletedRod(this.getItem()); }
			@Override public int getMaxStackSize() { return 1; }
		});
		for (int i = 4; i < 8; i++) {
			int local = i;
			int x = i % 2 == 0 ? 197 : 215;
			int y = i < 6 ? 19 : 37;
			this.addSlot(new Slot(container, local, x, y) {
				@Override public boolean mayPlace(ItemStack stack) { return AdvancedAlloyForgeBlockEntity.canPlaceUpgrade(container, local, stack); }
				@Override public int getMaxStackSize() { return 1; }
			});
		}

		for (int row = 0; row < 3; row++)
			for (int col = 0; col < 9; col++)
				this.addSlot(new Slot(inventory, col + (row + 1) * 9, 8 + col * 18, 84 + row * 18));
		for (int col = 0; col < 9; col++)
			this.addSlot(new Slot(inventory, col, 8 + col * 18, 142));

		this.addDataSlots(data);
	}

	public BlockPos getBlockPos() { return blockPos; }
	public int getProgress() { return data.get(0); }
	public int getFuelCapacity() { return data.get(1); }
	public int getMaxFuelCapacity() { return data.get(2); }

	@Override public boolean stillValid(Player player) { return container.stillValid(player); }

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack result = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot == null || !slot.hasItem()) return result;
		ItemStack stack = slot.getItem();
		result = stack.copy();

		if (index < MACHINE_SLOTS) {
			if (!moveItemStackTo(stack, MACHINE_SLOTS, slots.size(), true)) return ItemStack.EMPTY;
		} else if (AdvancedAlloyForgeBlockEntity.isReactorRod(stack)) {
			if (!moveItemStackTo(stack, 3, 4, false)) return ItemStack.EMPTY;
		} else if (AdvancedAlloyForgeBlockEntity.isUpgrade(stack)) {
			if (!moveItemStackTo(stack, 4, 8, false)) return ItemStack.EMPTY;
		} else if (AdvancedAlloyForgeBlockEntity.isAlloyInput(stack)) {
			if (!moveItemStackTo(stack, 1, 3, false)) return ItemStack.EMPTY;
		} else if (index < PLAYER_INV_END) {
			if (!moveItemStackTo(stack, PLAYER_INV_END, slots.size(), false)) return ItemStack.EMPTY;
		} else if (!moveItemStackTo(stack, MACHINE_SLOTS, PLAYER_INV_END, false)) return ItemStack.EMPTY;

		if (stack.isEmpty()) slot.setByPlayer(ItemStack.EMPTY); else slot.setChanged();
		if (stack.getCount() == result.getCount()) return ItemStack.EMPTY;
		slot.onTake(player, stack);
		return result;
	}

	@Override
	public void removed(Player player) {
		super.removed(player);
		container.stopOpen(player);
	}
}
