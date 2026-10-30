package net.mcreator.survivalreimagined.procedures.gui.tanning;

import net.mcreator.survivalreimagined.block.entity.TanningBarrelBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SpriteIndexHandler {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        BlockPos pos = BlockPos.containing(x,y,z);
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (!world.isClientSide() && blockEntity instanceof TanningBarrelBlockEntity barrel) {
            double spriteIndex = barrel.getCustomData().getDouble("SpriteIndex") + 1;

            if (spriteIndex >= 31) {
                spriteIndex = 0;
            }

            barrel.getCustomData().putDouble("SpriteIndex", spriteIndex);
            barrel.setChanged();

            if (world instanceof Level level) {
                BlockState state = level.getBlockState(pos);
                level.sendBlockUpdated(pos, state, state, 3);
            }
        }
    }
}
