package net.mcreator.survivalreimagined.procedures.gui.tanning;

import net.mcreator.survivalreimagined.block.entity.TanningBarrelBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;

public class SpriteIndex {
    public static double execute(LevelAccessor world, double x, double y, double z) {
        BlockPos pos = BlockPos.containing(x,y,z);
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof TanningBarrelBlockEntity barrel) {
            return barrel.getCustomData().getDouble("SpriteIndex");
        }
        return 0;
    }
}
