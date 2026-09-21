package net.mcreator.survivalreimagined.world.worldgen;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBiomes;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class SurvivalReimaginedModSurfaceRules {
    public static SurfaceRules.RuleSource makeRules() {
        BlockState radiatedSurface = SurvivalReimaginedModBlocks.RADIATED_SHALE.get().defaultBlockState();
        BlockState shale = SurvivalReimaginedModBlocks.SHALE.get().defaultBlockState();

        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(SurvivalReimaginedModBiomes.RADIANT_FOREST),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(
                                        SurfaceRules.ON_FLOOR,
                                        SurfaceRules.sequence(
                                                SurfaceRules.ifTrue(
                                                        SurfaceRules.waterBlockCheck(-1, 0),
                                                        SurfaceRules.state(radiatedSurface)
                                                ),
                                                SurfaceRules.state(shale)
                                        )
                                ),
                                SurfaceRules.ifTrue(
                                        SurfaceRules.UNDER_FLOOR,
                                        SurfaceRules.state(shale)
                                ),
                                SurfaceRules.ifTrue(
                                        SurfaceRules.DEEP_UNDER_FLOOR,
                                        SurfaceRules.state(shale)
                                )
                        )
                )
        );
    }
}
