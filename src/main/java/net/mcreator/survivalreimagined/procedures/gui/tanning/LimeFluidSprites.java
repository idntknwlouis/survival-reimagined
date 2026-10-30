package net.mcreator.survivalreimagined.procedures.gui.tanning;

import net.mcreator.survivalreimagined.block.entity.TanningBarrelBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;

public class LimeFluidSprites {
    public static boolean execute(LevelAccessor world, double x, double y, double z, double minFluid, double maxFluid) {
        BlockPos pos = BlockPos.containing(x,y,z);
        if (getBlockNBTLogic(world, pos, "Lime")) {
            double fluidAmount = getBlockNBTNumber(world, pos, "Fluid");
            return fluidAmount >= minFluid && fluidAmount <= maxFluid;
        }
        return false;
    }
    public static boolean execute1000(LevelAccessor world, double x, double y, double z) {
        return execute(world, x,y,z,1,1000);
    }
    public static boolean execute2000(LevelAccessor world, double x, double y, double z) {
        return execute(world, x,y,z,1001,2000);
    }
    public static boolean execute3000(LevelAccessor world, double x, double y, double z) {
        return execute(world, x,y,z,2001,3000);
    }
    public static boolean execute4000(LevelAccessor world, double x, double y, double z) {
        return execute(world, x,y,z,3001,4000);
    }
    public static boolean execute5000(LevelAccessor world, double x, double y, double z) {
        return execute(world, x,y,z,4001,5000);
    }
    public static boolean execute6000(LevelAccessor world, double x, double y, double z) {
        return execute(world, x,y,z,5001,6000);
    }

    public static boolean getBlockNBTLogic(LevelAccessor world, BlockPos pos, String tag) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof TanningBarrelBlockEntity barrel) {
            return barrel.getCustomData().getBoolean(tag);
        }
        return false;
    }

    private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof TanningBarrelBlockEntity barrel) {
            return barrel.getCustomData().getDouble(tag);
        }
        return 0;
    }
}
