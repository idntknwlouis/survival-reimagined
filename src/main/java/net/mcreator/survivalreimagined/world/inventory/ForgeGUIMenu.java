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

import net.mcreator.survivalreimagined.block.entity.ForgeBlockEntity;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModMenus;

public class ForgeGUIMenu extends AbstractContainerMenu {
	private static final int FORGE_SLOT_COUNT = 4;
	private static final int PLAYER_INVENTORY_END = FORGE_SLOT_COUNT + 27;
	private final Container container;
	private final ContainerData data;
	private final BlockPos blockPos;

	public ForgeGUIMenu(int id, Inventory inventory, BlockPos pos) {
		this(id, inventory, new SimpleContainer(ForgeBlockEntity.CONTAINER_SIZE), new SimpleContainerData(2), pos);
	}

	public ForgeGUIMenu(int id, Inventory inventory, ForgeBlockEntity forge) {
		this(id, inventory, forge, forge.getDataAccess(), forge.getBlockPos());
	}

	private ForgeGUIMenu(int id, Inventory inventory, Container container, ContainerData data, BlockPos pos) {
		super(SurvivalReimaginedModMenus.FORGE_GUI.get(), id);
		checkContainerSize(container, ForgeBlockEntity.CONTAINER_SIZE);
		checkContainerDataCount(data, 2);
		this.container = container;
		this.data = data;
		this.blockPos = pos;
		this.container.startOpen(inventory.player);

		this.addSlot(new Slot(container, 0, 71, 36));
		this.addSlot(new Slot(container, 1, 89, 36));
		this.addSlot(new Slot(container, 2, 26, 62) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return ForgeBlockEntity.getFuelValue(stack) > 0;
			}
		});
		this.addSlot(new Slot(container, 3, 134, 36) {
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

	public int getFuelMeter() {
		return this.data.get(0);
	}

	public int getBurnTime() {
		return this.data.get(1);
	}

	@Override
	public boolean stillValid(Player player) {
		return this.container.stillValid(player);
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack result = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot == null || !slot.hasItem()) {
			return result;
		}

		ItemStack stack = slot.getItem();
		result = stack.copy();

		if (index < FORGE_SLOT_COUNT) {
			if (!this.moveItemStackTo(stack, FORGE_SLOT_COUNT, this.slots.size(), true)) {
				return ItemStack.EMPTY;
			}
		} else if (!this.moveItemStackTo(stack, 0, FORGE_SLOT_COUNT, false)) {
			if (index < PLAYER_INVENTORY_END) {
				if (!this.moveItemStackTo(stack, PLAYER_INVENTORY_END, this.slots.size(), false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(stack, FORGE_SLOT_COUNT, PLAYER_INVENTORY_END, false)) {
				return ItemStack.EMPTY;
			}
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
		return result;
	}

	@Override
	public void removed(Player player) {
		super.removed(player);
		this.container.stopOpen(player);
	}
}
