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
		addUndergroundEverywhere("diamond_ore_feature");
		addUndergroundEverywhere("emerald_ore_feature");
		addUndergroundEverywhere("lazurite_ore");
		addUndergroundEverywhere("basalt_hematite_feature");
		addUndergroundEverywhere("basalt_magnetite_feature");
		addUndergroundEverywhere("basalt_calaverite_feature");
		addUndergroundEverywhere("basalt_pyrolusite_feature");
		addUndergroundEverywhere("basalt_uranophane_feature");
		addUndergroundEverywhere("basalt_ilmenite_feature");
		addUndergroundEverywhere("basalt_anthracite_feature");
		addUndergroundEverywhere("basalt_liginite_feature");
		addUndergroundEverywhere("uraninite_feature");
		addUndergroundEverywhere("titanium_ore_feature");
		addUndergroundEverywhere("amber_ore_feature");
		addUndergroundEverywhere("ruby_ore_feature");
		addUndergroundEverywhere("sapphire_ore_feature");
		addUndergroundEverywhere("argentite_feature");
		addUndergroundEverywhere("native_silver");
		addLocalEverywhere("basalt_layer");
		addLocalEverywhere("kimberlite_feature");
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

	private static void addLocalEverywhere(String path) {
		ResourceKey<PlacedFeature> featureKey = ResourceKey.create(
				Registries.PLACED_FEATURE,
				SurvivalReimaginedMod.asResource(path));
		BiomeModifications.addFeature(
				context -> true,
				GenerationStep.Decoration.LOCAL_MODIFICATIONS,
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
