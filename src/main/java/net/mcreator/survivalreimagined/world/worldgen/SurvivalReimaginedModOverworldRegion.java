package net.mcreator.survivalreimagined.world.worldgen;

import com.mojang.datafixers.util.Pair;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBiomes;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;


public class SurvivalReimaginedModOverworldRegion extends Region {
    public SurvivalReimaginedModOverworldRegion(ResourceLocation name, int weight) {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        Climate.Parameter temp = Climate.Parameter.span(-0.3F, 0.3F);
        Climate.Parameter humid = Climate.Parameter.span(-0.8F, 0.8F);
        Climate.Parameter cont = Climate.Parameter.span(0.0F, 1.0F);
        Climate.Parameter eros = Climate.Parameter.span(-1.0F, 0.0F);
        Climate.Parameter weird = Climate.Parameter.span(0.0F, 1.0F);

        Climate.ParameterPoint radiantForest = new Climate.ParameterPoint(
                temp, humid, cont, eros,
                Climate.Parameter.span(0.75F, 1.5F),
                weird, 0L
        );
        mapper.accept(Pair.of(radiantForest, SurvivalReimaginedModBiomes.RADIANT_FOREST));

        Climate.ParameterPoint surfaceCover = new Climate.ParameterPoint(
                temp, humid, cont, eros,
                Climate.Parameter.span(-0.3F, 0.3F),
                weird, 0L
        );
        mapper.accept(Pair.of(surfaceCover, Biomes.PLAINS));

        Climate.ParameterPoint skyCover = new Climate.ParameterPoint(
                temp, humid, cont, eros,
                Climate.Parameter.span(-2F, -0.16F),
                weird, 0L
        );
        mapper.accept(Pair.of(skyCover, Biomes.PLAINS));
    }
}
