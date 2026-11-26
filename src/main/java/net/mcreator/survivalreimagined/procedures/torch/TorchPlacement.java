package net.mcreator.survivalreimagined.procedures.torch;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;

public class TorchPlacement {
    public static boolean execute(LevelAccessor world, double x, double y, double z, Direction facing) {
        BlockPos pos = BlockPos.containing(x,y,z);
        BlockPos supportPos = pos.relative(facing.getOpposite());
        return Block.canSupportCenter(world, supportPos, facing);
    }
}
