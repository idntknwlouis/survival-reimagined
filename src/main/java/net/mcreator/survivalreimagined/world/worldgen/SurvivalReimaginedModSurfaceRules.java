package net.mcreator.survivalreimagined.world.worldgen;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBiomes;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class SurvivalReimaginedModSurfaceRules {
    public static SurfaceRules.RuleSource makeRules() {
        BlockState shaleSurfaceBlock = SurvivalReimaginedModBlocks.SHALE.get().defaultBlockState();

        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(SurvivalReimaginedModBiomes.RADIANT_FOREST),
                        SurfaceRules.ifTrue(
                                SurfaceRules.ON_FLOOR,
                                SurfaceRules.state(shaleSurfaceBlock)
                        )
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(SurvivalReimaginedModBiomes.RADIANT_FOREST),
                        SurfaceRules.ifTrue(
                                SurfaceRules.DEEP_UNDER_FLOOR,
                                SurfaceRules.state(shaleSurfaceBlock)
                        )
                )
        );
    }
}
