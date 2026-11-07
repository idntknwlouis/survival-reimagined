package net.mcreator.survivalreimagined.procedures;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.block.entity.BlockOfCharcoalBlockEntity;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;

public class BlockOfCharcoalRandomClientDisplayTick {
    public static void execute(Level world, double x, double y, double z) {
        BlockPos pos = BlockPos.containing(x,y,z);
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof BlockOfCharcoalBlockEntity charcoal && charcoal.canBurn()) {
            world.addParticle(
                    SurvivalReimaginedModParticleTypes.BLACK_SMOKE.get(),
                    x + Math.random(), y, z + Math.random(), 0.0, 0.1, 0.0
            );
        }
    }
}
