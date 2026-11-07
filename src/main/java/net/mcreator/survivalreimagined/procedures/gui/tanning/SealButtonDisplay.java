package net.mcreator.survivalreimagined.procedures.gui.tanning;

import net.mcreator.survivalreimagined.block.entity.TanningBarrelBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;

public class SealButtonDisplay {
    public static boolean execute(LevelAccessor world, double x, double y, double z) {
        BlockEntity blockEntity = world.getBlockEntity(BlockPos.containing(x,y,z));

        if (blockEntity instanceof TanningBarrelBlockEntity barrel) {
            return barrel.getCustomData().getBoolean("CanSeal");
        }
        return false;
    }
}
