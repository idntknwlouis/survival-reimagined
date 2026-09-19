package net.mcreator.survivalreimagined.init;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;

public final class SurvivalReimaginedModBiomeModifications {
	private SurvivalReimaginedModBiomeModifications() {
	}

	public static void register() {
		addEverywhere("flint_gen");
		addEverywhere("stone_gen");
		addEverywhere("copper_gen");
		addUndergroundEverywhere("uraninite_feature");
		addUndergroundEverywhere("amber_ore_feature");
		addUndergroundEverywhere("ruby_ore_feature");
		addUndergroundEverywhere("sapphire_ore_feature");
		addUndergroundEverywhere("argentite_feature");
		addUndergroundEverywhere("native_silver");
	}

	private static void addUndergroundEverywhere(String path) {
		ResourceKey<PlacedFeature> featureKey = ResourceKey.create(
				Registries.PLACED_FEATURE,
				SurvivalReimaginedMod.asResource(path));
		BiomeModifications.addFeature(
				context -> true,
				GenerationStep.Decoration.UNDERGROUND_ORES,
				featureKey);
	}

	private static void addEverywhere(String path) {
		ResourceKey<PlacedFeature> featureKey = ResourceKey.create(
				Registries.PLACED_FEATURE,
				SurvivalReimaginedMod.asResource(path));

		BiomeModifications.addFeature(
				context -> true,
				GenerationStep.Decoration.VEGETAL_DECORATION,
				featureKey);
	}
}
