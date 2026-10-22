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

import net.mcreator.survivalreimagined.block.entity.MineralProcessingTableBlockEntity;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModMenus;

public class MPTGUIMenu extends AbstractContainerMenu {
	private static final int TABLE_SLOT_COUNT = 3;
	private static final int PLAYER_INVENTORY_END = TABLE_SLOT_COUNT + 27;
	private final Container container;
	private final ContainerData data;
	private final BlockPos blockPos;

	public MPTGUIMenu(int id, Inventory inventory, BlockPos pos) {
		this(id, inventory, new SimpleContainer(MineralProcessingTableBlockEntity.CONTAINER_SIZE), new SimpleContainerData(3), pos);
	}

	public MPTGUIMenu(int id, Inventory inventory, MineralProcessingTableBlockEntity table) {
		this(id, inventory, table, table.getDataAccess(), table.getBlockPos());
	}

	private MPTGUIMenu(int id, Inventory inventory, Container container, ContainerData data, BlockPos pos) {
		super(SurvivalReimaginedModMenus.MPTGUI.get(), id);
		checkContainerSize(container, MineralProcessingTableBlockEntity.CONTAINER_SIZE);
		checkContainerDataCount(data, 3);
		this.container = container;
		this.data = data;
		this.blockPos = pos;
		this.container.startOpen(inventory.player);

		this.addSlot(new Slot(container, 0, 62, 35) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return MineralProcessingTableBlockEntity.isProcessingInput(stack);
			}
		});
		this.addSlot(new Slot(container, 2, 98, 35) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return false;
			}
		});
		this.addSlot(new Slot(container, 1, 80, 62) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return MineralProcessingTableBlockEntity.isReactorRod(stack) && !this.hasItem();
			}

			@Override
			public boolean mayPickup(Player player) {
				return !this.hasItem() || MineralProcessingTableBlockEntity.isDepletedRod(this.getItem());
			}

			@Override
			public int getMaxStackSize() {
				return 1;
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

	public BlockPos getBlockPos() { return this.blockPos; }
	public int getProgress() { return this.data.get(0); }
	public int getRodCapacity() { return this.data.get(1); }
	public boolean isPowered() { return this.data.get(2) != 0; }

	@Override
	public boolean stillValid(Player player) { return this.container.stillValid(player); }

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack result = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot == null || !slot.hasItem()) return result;
		ItemStack stack = slot.getItem();
		result = stack.copy();

		if (index < TABLE_SLOT_COUNT) {
			if (!this.moveItemStackTo(stack, TABLE_SLOT_COUNT, this.slots.size(), true)) return ItemStack.EMPTY;
		} else if (MineralProcessingTableBlockEntity.isReactorRod(stack)) {
			if (!this.moveItemStackTo(stack, 2, 3, false)) return ItemStack.EMPTY;
		} else if (!this.moveItemStackTo(stack, 0, 1, false)) {
			if (index < PLAYER_INVENTORY_END) {
				if (!this.moveItemStackTo(stack, PLAYER_INVENTORY_END, this.slots.size(), false)) return ItemStack.EMPTY;
			} else if (!this.moveItemStackTo(stack, TABLE_SLOT_COUNT, PLAYER_INVENTORY_END, false)) {
				return ItemStack.EMPTY;
			}
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
