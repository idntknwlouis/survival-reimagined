package net.mcreator.survivalreimagined.init;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biomes;
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
		addUndergroundEverywhere("hematite");
		addUndergroundEverywhere("magnetite");
		addUndergroundEverywhere("calaverite_feature");
		addUndergroundEverywhere("pyrolusite_feature");
		addUndergroundEverywhere("uranophane_feature");
		addRadiantForestUnderground("shale_uranophane");
		addRadiantForestUnderground("shale_uraninite");
		addUndergroundEverywhere("liginite_ore_feature_surface");
		addUndergroundEverywhere("ilmenite_ore_feature");
		addUndergroundEverywhere("anthracite_ore_feature");
		addUndergroundEverywhere("liginite_ore_feature");
		addUndergroundEverywhere("uraninite_feature");
		addUndergroundEverywhere("titanium_ore_feature");
		addUndergroundEverywhere("amber_ore_feature");
		addUndergroundEverywhere("ruby_ore_feature");
		addUndergroundEverywhere("sapphire_ore_feature");
		addUndergroundEverywhere("argentite_feature");
		addUndergroundEverywhere("native_copper");
		addUndergroundEverywhere("native_gold");
		addUndergroundEverywhere("native_silver");
		addUndergroundToBiomes("extra_native_gold",
				Biomes.BADLANDS,
				Biomes.ERODED_BADLANDS,
				Biomes.WOODED_BADLANDS);
		addLocalEverywhere("basalt_layer");
		addUndergroundDecorationEverywhere("basalt_stalagmite");
		addUndergroundDecorationEverywhere("basalt_stalagtite");
		addLocalEverywhere("kimberlite_feature");
		addUndergroundDecorationEverywhere("kimberlite_stalagmite");
		addUndergroundDecorationEverywhere("kimberlite_stalagtite");
		addNether("dark_cinder_blobs");
		addVegetationToBiomes("natural_rye",
				Biomes.OLD_GROWTH_PINE_TAIGA,
				Biomes.OLD_GROWTH_SPRUCE_TAIGA,
				Biomes.TAIGA,
				Biomes.SAVANNA,
				Biomes.SAVANNA_PLATEAU,
				Biomes.WINDSWEPT_SAVANNA);
		addVegetationToBiomes("natural_spelt",
				Biomes.TAIGA,
				Biomes.BIRCH_FOREST,
				Biomes.OLD_GROWTH_BIRCH_FOREST);
		addVegetationToBiomes("wild_carrots",
				Biomes.PLAINS,
				Biomes.SUNFLOWER_PLAINS,
				Biomes.OLD_GROWTH_PINE_TAIGA,
				Biomes.OLD_GROWTH_SPRUCE_TAIGA,
				Biomes.TAIGA);
	}

	private static void addRadiantForestUnderground(String path) {
		ResourceKey<PlacedFeature> featureKey = ResourceKey.create(
				Registries.PLACED_FEATURE,
				SurvivalReimaginedMod.asResource(path));
		BiomeModifications.addFeature(
				BiomeSelectors.includeByKey(SurvivalReimaginedModBiomes.RADIANT_FOREST),
				GenerationStep.Decoration.UNDERGROUND_ORES,
				featureKey);
	}

	@SafeVarargs
	private static void addVegetationToBiomes(String path, ResourceKey<net.minecraft.world.level.biome.Biome>... biomes) {
		ResourceKey<PlacedFeature> featureKey = ResourceKey.create(
				Registries.PLACED_FEATURE,
				SurvivalReimaginedMod.asResource(path));
		BiomeModifications.addFeature(
				BiomeSelectors.includeByKey(biomes),
				GenerationStep.Decoration.VEGETAL_DECORATION,
				featureKey);
	}

	private static void addNether(String path) {
		ResourceKey<PlacedFeature> featureKey = ResourceKey.create(
				Registries.PLACED_FEATURE,
				SurvivalReimaginedMod.asResource(path));
		BiomeModifications.addFeature(
				BiomeSelectors.foundInTheNether(),
				GenerationStep.Decoration.UNDERGROUND_DECORATION,
				featureKey);
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

	@SafeVarargs
	private static void addUndergroundToBiomes(String path, ResourceKey<net.minecraft.world.level.biome.Biome>... biomes) {
		ResourceKey<PlacedFeature> featureKey = ResourceKey.create(
				Registries.PLACED_FEATURE,
				SurvivalReimaginedMod.asResource(path));
		BiomeModifications.addFeature(
				BiomeSelectors.includeByKey(biomes),
				GenerationStep.Decoration.UNDERGROUND_ORES,
				featureKey);
	}

	private static void addUndergroundDecorationEverywhere(String path) {
		ResourceKey<PlacedFeature> featureKey = ResourceKey.create(
				Registries.PLACED_FEATURE,
				SurvivalReimaginedMod.asResource(path));
		BiomeModifications.addFeature(
				context -> true,
				GenerationStep.Decoration.UNDERGROUND_DECORATION,
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
