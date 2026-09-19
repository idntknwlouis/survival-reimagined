package net.mcreator.survivalreimagined.world.inventory;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import net.mcreator.survivalreimagined.block.entity.MetalRefiningTableBlockEntity;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModMenus;

public class MetalRefiningTableGUIMenu extends AbstractContainerMenu {
	private static final int TABLE_SLOT_COUNT = 4;
	private static final int PLAYER_INVENTORY_END = TABLE_SLOT_COUNT + 27;
	private final Container container;
	private final BlockPos blockPos;

	public MetalRefiningTableGUIMenu(int id, Inventory inventory, BlockPos pos) {
		this(id, inventory, new SimpleContainer(MetalRefiningTableBlockEntity.CONTAINER_SIZE), pos);
	}

	public MetalRefiningTableGUIMenu(int id, Inventory inventory, MetalRefiningTableBlockEntity table) {
		this(id, inventory, table, table.getBlockPos());
	}

	private MetalRefiningTableGUIMenu(int id, Inventory inventory, Container container, BlockPos pos) {
		super(SurvivalReimaginedModMenus.METAL_REFINING_TABLE_GUI.get(), id);
		checkContainerSize(container, MetalRefiningTableBlockEntity.CONTAINER_SIZE);
		this.container = container;
		this.blockPos = pos;
		this.container.startOpen(inventory.player);

		this.addSlot(new Slot(container, 0, 44, 38));
		this.addSlot(new Slot(container, 2, 152, 63) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return MetalRefiningTableBlockEntity.isHammer(stack);
			}
		});
		this.addSlot(new Slot(container, 3, 116, 38) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return false;
			}

			@Override
			public void onTake(Player player, ItemStack stack) {
				super.onTake(player, stack);
				if (MetalRefiningTableGUIMenu.this.container instanceof MetalRefiningTableBlockEntity table) {
					table.takeResult(player);
				}
			}
		});
		this.addSlot(new Slot(container, 1, 80, 38));

		for (int row = 0; row < 3; ++row) {
			for (int column = 0; column < 9; ++column) {
				this.addSlot(new Slot(inventory, column + (row + 1) * 9, 8 + column * 18, 84 + row * 18));
			}
		}
		for (int column = 0; column < 9; ++column) {
			this.addSlot(new Slot(inventory, column, 8 + column * 18, 142));
		}
	}

	public BlockPos getBlockPos() {
		return this.blockPos;
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

		if (index < TABLE_SLOT_COUNT) {
			if (!this.moveItemStackTo(stack, TABLE_SLOT_COUNT, this.slots.size(), true)) return ItemStack.EMPTY;
		} else {
			int targetStart = MetalRefiningTableBlockEntity.isHammer(stack) ? 1 : 0;
			int targetEnd = MetalRefiningTableBlockEntity.isHammer(stack) ? 2 : 4;
			if (!this.moveItemStackTo(stack, targetStart, targetEnd, false)) {
				if (index < PLAYER_INVENTORY_END) {
					if (!this.moveItemStackTo(stack, PLAYER_INVENTORY_END, this.slots.size(), false)) return ItemStack.EMPTY;
				} else if (!this.moveItemStackTo(stack, TABLE_SLOT_COUNT, PLAYER_INVENTORY_END, false)) {
					return ItemStack.EMPTY;
				}
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
