package net.mcreator.survivalreimagined.world.inventory;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModMenus;
import net.mcreator.survivalreimagined.network.gui.AAFScriptureGUIButtonMessage;
import net.mcreator.survivalreimagined.procedures.gui.AAF.AAFScriptureGUIOpened;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AAFScriptureGUIMenu extends AbstractContainerMenu {
    public final Level level;
    public final Player player;
    public int x,y,z;

    public AAFScriptureGUIMenu(int id, Inventory inventory, int handCode) {
        super(SurvivalReimaginedModMenus.AAF_SCRIPTURE_GUI.get(), id);
        this.player = inventory.player;
        this.level = inventory.player.level();

        BlockPos pos = this.player.blockPosition();
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();

        AAFScriptureGUIOpened.execute(level, x, y, z, player);
    }

    public AAFScriptureGUIMenu(int id, Inventory inventory, BlockPos pos) {
        super(SurvivalReimaginedModMenus.AAF_SCRIPTURE_GUI.get(), id);
        this.player = inventory.player;
        this.level = inventory.player.level();

        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();

        if (!this.level.isClientSide()) {
            AAFScriptureGUIOpened.execute(this.level, this.x, this.y, this.z, this.player);
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        return ItemStack.EMPTY;
    }
    @Override
    public void removed(Player playerIn) {
        super.removed(playerIn);
    }
}
