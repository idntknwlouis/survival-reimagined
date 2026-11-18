package net.mcreator.survivalreimagined.procedures.torch;

import net.mcreator.survivalreimagined.block.entity.CrudeTorchBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class TorchAdded {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        if (!world.isClientSide()) {
            BlockPos pos = BlockPos.containing(x,y,z);

            if (world.getBlockEntity(pos) instanceof CrudeTorchBlockEntity blockEntity) {
                blockEntity.setBurnTarget(Mth.nextInt(RandomSource.create(), 200, 400));
            }

            if (world instanceof Level level) {
                level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 3);
            }
        }
    }
}
