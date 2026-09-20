package net.mcreator.survivalreimagined.world;

import com.mojang.datafixers.util.Pair;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBiomes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import java.util.ArrayList;
import java.util.List;

public final class SurvivalReimaginedMultiNoiseBiomeSource {
    private SurvivalReimaginedMultiNoiseBiomeSource() {}

    public static MultiNoiseBiomeSource create(
            HolderGetter<Biome> biomes
    ) {
        System.out.println("SURVIVAL REIMAINGED BIOME SOURCE CREATED");
        MultiNoiseBiomeSourceParameterList vanilla = new MultiNoiseBiomeSourceParameterList(
                MultiNoiseBiomeSourceParameterList.Preset.OVERWORLD, biomes
        );
        List<Pair<Climate.ParameterPoint, Holder<Biome>>> entries =
                new ArrayList<>(vanilla.parameters().values());

        Holder<Biome> radiantForest = biomes.getOrThrow(SurvivalReimaginedModBiomes.RADIANT_FOREST);

        Climate.ParameterPoint radiantForestParameters =
                Climate.parameters(
                        Climate.Parameter.span(-0.3f,0.3f), //Temp
                        Climate.Parameter.span(-0.8f,0.8f), //Humidity
                        Climate.Parameter.span(0f,1f),      //Continentalness
                        Climate.Parameter.span(-1f,0f),     //Erosion
                        Climate.Parameter.point(0.0f),         //Depth
                        Climate.Parameter.span(0f,1f), 0f   //Weirdness + Offset
                );

        entries.add(
                Pair.of(radiantForestParameters, radiantForest)
        );
        return MultiNoiseBiomeSource.createFromList(new Climate.ParameterList<>(entries));
    }
}
