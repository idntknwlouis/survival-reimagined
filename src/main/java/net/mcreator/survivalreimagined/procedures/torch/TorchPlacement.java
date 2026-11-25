package net.mcreator.survivalreimagined.procedures.torch;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TorchPlacement {
    public static boolean execute(LevelAccessor world, double x, double y, double z) {
        BlockPos pos = BlockPos.containing(x,y,z);

        Direction[] directions = {
          Direction.DOWN,
          Direction.NORTH,
          Direction.SOUTH,
          Direction.WEST,
          Direction.EAST
        };

        for (Direction direction : directions) {
            BlockPos supportPos = pos.relative(direction);
            var state = world.getBlockState(supportPos);
            VoxelShape shape = state.getCollisionShape(world, supportPos);

            if (Shapes.block().equals(shape)) {
                return true;
            }
        }
        return false;
    }
}
