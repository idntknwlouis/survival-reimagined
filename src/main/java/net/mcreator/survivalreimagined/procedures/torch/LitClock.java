package net.mcreator.survivalreimagined.procedures.torch;

import net.mcreator.survivalreimagined.block.CrudeTorch;
import net.mcreator.survivalreimagined.block.entity.CrudeTorchBlockEntity;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class LitClock {
    public static void execute(LevelAccessor world, double x, double y, double z, BlockState state) {
        if (world.isClientSide()) return;
        if (!state.getValue(CrudeTorch.LIT)) return;

        BlockPos pos = BlockPos.containing(x,y,z);

        if (!(world.getBlockEntity(pos) instanceof CrudeTorchBlockEntity blockEntity)) return;

        int burnClock = blockEntity.getBurnClock() + 1;
        blockEntity.setBurnClock(burnClock);

        int burnTarget = blockEntity.getBurnTarget();
        if (burnTarget <= 0 || burnClock < burnTarget) return;

        Direction facing = state.getValue(CrudeTorch.FACING);
        boolean isWallTorch = facing.getAxis().isHorizontal();

        BlockState burntState = SurvivalReimaginedModBlocks.BURNT_TORCH.get().defaultBlockState().setValue(CrudeTorch.FACING, facing);
        world.setBlock(pos, burntState, 3);
    }
}
