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

import net.mcreator.survivalreimagined.block.entity.CampfireBlockEntity;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModMenus;

public class CampfireGUIMenu extends AbstractContainerMenu {
	private static final int MACHINE_SLOTS = 5;
	private static final int PLAYER_INV_END = MACHINE_SLOTS + 27;
	private final Container container;
	private final ContainerData data;
	private final BlockPos blockPos;

	public CampfireGUIMenu(int id, Inventory inventory, BlockPos pos) {
		this(id, inventory, new SimpleContainer(CampfireBlockEntity.CONTAINER_SIZE), new SimpleContainerData(1), pos);
	}

	public CampfireGUIMenu(int id, Inventory inventory, CampfireBlockEntity campfire) {
		this(id, inventory, campfire, campfire.getDataAccess(), campfire.getBlockPos());
	}

	private CampfireGUIMenu(int id, Inventory inventory, Container container, ContainerData data, BlockPos pos) {
		super(SurvivalReimaginedModMenus.CAMPFIRE_GUI.get(), id);
		checkContainerSize(container, CampfireBlockEntity.CONTAINER_SIZE);
		checkContainerDataCount(data, 1);
		this.container = container;
		this.data = data;
		this.blockPos = pos;
		container.startOpen(inventory.player);

		this.addSlot(new Slot(container, 0, 80, 66) {
			@Override public boolean mayPlace(ItemStack stack) { return CampfireBlockEntity.isFuel(stack); }
		});
		this.addSlot(cookSlot(container, 1, 71, 29));
		this.addSlot(cookSlot(container, 2, 89, 29));
		this.addSlot(cookSlot(container, 3, 71, 11));
		this.addSlot(cookSlot(container, 4, 89, 11));

		for (int row = 0; row < 3; row++)
			for (int col = 0; col < 9; col++)
				this.addSlot(new Slot(inventory, col + (row + 1) * 9, 8 + col * 18, 84 + row * 18));
		for (int col = 0; col < 9; col++)
			this.addSlot(new Slot(inventory, col, 8 + col * 18, 142));

		this.addDataSlots(data);
	}

	private static Slot cookSlot(Container container, int index, int x, int y) {
		return new Slot(container, index, x, y) {
			@Override public boolean mayPlace(ItemStack stack) { return CampfireBlockEntity.isCookable(stack); }
		};
	}

	public BlockPos getBlockPos() { return blockPos; }
	public int getFuelProgress() { return data.get(0); }

	@Override public boolean stillValid(Player player) { return container.stillValid(player); }

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		Slot slot = this.slots.get(index);
		if (slot == null || !slot.hasItem()) return ItemStack.EMPTY;
		ItemStack stack = slot.getItem();
		ItemStack copy = stack.copy();

		if (index < MACHINE_SLOTS) {
			if (!moveItemStackTo(stack, MACHINE_SLOTS, slots.size(), true)) return ItemStack.EMPTY;
		} else if (CampfireBlockEntity.isFuel(stack)) {
			if (!moveItemStackTo(stack, 0, 1, false)) return ItemStack.EMPTY;
		} else if (CampfireBlockEntity.isCookable(stack)) {
			if (!moveItemStackTo(stack, 1, 5, false)) return ItemStack.EMPTY;
		} else if (index < PLAYER_INV_END) {
			if (!moveItemStackTo(stack, PLAYER_INV_END, slots.size(), false)) return ItemStack.EMPTY;
		} else if (!moveItemStackTo(stack, MACHINE_SLOTS, PLAYER_INV_END, false)) {
			return ItemStack.EMPTY;
		}

		if (stack.isEmpty()) slot.setByPlayer(ItemStack.EMPTY); else slot.setChanged();
		if (stack.getCount() == copy.getCount()) return ItemStack.EMPTY;
		slot.onTake(player, stack);
		return copy;
	}

	@Override
	public void removed(Player player) {
		super.removed(player);
		container.stopOpen(player);
	}
}
