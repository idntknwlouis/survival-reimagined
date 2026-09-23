package net.mcreator.survivalreimagined.init;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class SurvivalReimaginedModBiomes {
    public static final ResourceKey<Biome> RADIANT_FOREST = ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "radiated_forest"));
}
