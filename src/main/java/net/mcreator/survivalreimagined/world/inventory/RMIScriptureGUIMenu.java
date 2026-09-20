package net.mcreator.survivalreimagined.world.inventory;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModMenus;

public class RMIScriptureGUIMenu extends AbstractContainerMenu {
	private final BlockPos pos;

	public RMIScriptureGUIMenu(int id, Inventory inventory, BlockPos pos) {
		super(SurvivalReimaginedModMenus.RMI_SCRIPTURE_GUI.get(), id);
		this.pos = pos;
	}

	public BlockPos getPos() { return pos; }

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}
}
