package net.mcreator.survivalreimagined.procedures;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;

public class DarkCinderClientDisplayTick {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        world.addParticle((SimpleParticleType) (SurvivalReimaginedModParticleTypes.DARK_CINDER.get()), (x + Math.random()), (y + Math.random()), (z + Math.random()), (Mth.nextDouble(RandomSource.create(), -0.1, 0.1)),
                (Mth.nextDouble(RandomSource.create(), -0.1, 0.1)), (Mth.nextDouble(RandomSource.create(), -0.1, 0.1)));
    }
}
