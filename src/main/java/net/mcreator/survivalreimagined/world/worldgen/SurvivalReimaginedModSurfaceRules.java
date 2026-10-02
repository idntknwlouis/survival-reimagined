package net.mcreator.survivalreimagined.world.worldgen;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBiomes;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class SurvivalReimaginedModSurfaceRules {
    public static SurfaceRules.RuleSource makeRules() {
        BlockState radiatedSurface = SurvivalReimaginedModBlocks.RADIATED_SHALE.get().defaultBlockState();
        BlockState shale = SurvivalReimaginedModBlocks.SHALE.get().defaultBlockState();
        BlockState moss = SurvivalReimaginedModBlocks.RADIATED_MOSS.get().defaultBlockState();

        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(SurvivalReimaginedModBiomes.RADIANT_FOREST),
                        SurfaceRules.ifTrue(
                                SurfaceRules.yBlockCheck(VerticalAnchor.aboveBottom(5), 0),
                                SurfaceRules.ifTrue(
                                        SurfaceRules.not(SurfaceRules.yBlockCheck(VerticalAnchor.belowTop(5), 0)),
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
                                                        SurfaceRules.ON_CEILING,
                                                        SurfaceRules.state(moss)
                                                ),
                                                SurfaceRules.ifTrue(
                                                        SurfaceRules.UNDER_FLOOR,
                                                        SurfaceRules.state(shale)
                                                )
                                        )
                                )
                        )
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(SurvivalReimaginedModBiomes.WISTERIA_FOREST),
                        SurfaceRules.ifTrue(
                                SurfaceRules.abovePreliminarySurface(),
                                SurfaceRules.sequence(
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.ON_FLOOR,
                                                SurfaceRules.sequence(
                                                        SurfaceRules.ifTrue(
                                                                SurfaceRules.waterBlockCheck(-1, 0),
                                                                SurfaceRules.state(Blocks.GRASS_BLOCK.defaultBlockState())
                                                        ),
                                                        SurfaceRules.state(Blocks.DIRT.defaultBlockState())
                                                )
                                        ),
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.UNDER_FLOOR,
                                                SurfaceRules.state(Blocks.DIRT.defaultBlockState())
                                        )
                                )
                        )
                )
        );
    }
}
