package net.mcreator.survivalreimagined.init;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.entity.MobCategory;
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
		addUndergroundEverywhere("hematite");
		addUndergroundEverywhere("magnetite");
		addUndergroundEverywhere("calaverite_feature");
		addUndergroundToBiomes("extra_calaverite_feature",
				Biomes.DESERT,
				Biomes.BADLANDS,
				Biomes.ERODED_BADLANDS,
				Biomes.WOODED_BADLANDS);
		addUndergroundEverywhere("pyrolusite_feature");
		addUndergroundEverywhere("cassiterite_feature");
		addUndergroundEverywhere("manganite_feature");
		addUndergroundEverywhere("uranophane_feature");
		addRadiantForestUnderground("shale_uranophane");
		addRadiantForestUnderground("shale_uraninite");
		addRadiantForestUndergroundDecoration("thin_radiated_vines_feature");
		addRadiantForestUndergroundDecoration("thick_radiated_vines_feature");
		addRadiantForestVegetation("radiated_tree");
		addWisteriaForestVegetation("wisteria_tree_1");
		addWisteriaForestVegetation("wisteria_bushes");
		addWisteriaForestVegetation("wisteria_leaf_litter_patch");
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
		addPointedStonesUndergroundEverywhere("stone_stalagmite");
		addPointedStonesUndergroundEverywhere("stone_stalagtite");
		addPointedStonesUndergroundEverywhere("deepslate_stalagmite");
		addPointedStonesUndergroundEverywhere("deepslate_stalagtite");
		addRadiantForestUndergroundDecoration("shale_stalagmite");
		addRadiantForestUndergroundDecoration("shale_stalagtite");
		addUndergroundToBiomes("extra_native_gold",
				Biomes.BADLANDS,
				Biomes.ERODED_BADLANDS,
				Biomes.WOODED_BADLANDS);
		addLocalEverywhere("basalt_layer");
		addFluidSprings("lava_srpings");
		addUndergroundDecorationEverywhere("deltas");
		addPointedStonesUndergroundEverywhere("basalt_stalagmite");
		addPointedStonesUndergroundEverywhere("basalt_stalagtite");
		addLocalEverywhere("kimberlite_feature");
		addPointedStonesUndergroundEverywhere("kimberlite_stalagmite");
		addPointedStonesUndergroundEverywhere("kimberlite_stalagtite");
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

		BiomeModifications.addSpawn(
				BiomeSelectors.includeByKey(
						Biomes.BIRCH_FOREST, Biomes.DARK_FOREST, Biomes.FOREST,
						Biomes.OLD_GROWTH_BIRCH_FOREST, Biomes.OLD_GROWTH_PINE_TAIGA,
						Biomes.OLD_GROWTH_SPRUCE_TAIGA, Biomes.SNOWY_TAIGA, Biomes.TAIGA,
						Biomes.MEADOW, Biomes.JAGGED_PEAKS, Biomes.STONY_PEAKS),
				MobCategory.CREATURE, SurvivalReimaginedModEntities.BLACK_BEAR.get(), 20, 1, 2);

		BiomeModifications.addSpawn(
				BiomeSelectors.includeByKey(
						Biomes.TAIGA, Biomes.SNOWY_TAIGA,
						Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA),
				MobCategory.CREATURE, SurvivalReimaginedModEntities.BROWN_BEAR.get(), 20, 1, 2);
	}

	private static void addWisteriaForestVegetation(String path) {
		ResourceKey<PlacedFeature> featureKey = ResourceKey.create(
				Registries.PLACED_FEATURE,
				SurvivalReimaginedMod.asResource(path));
		BiomeModifications.addFeature(
				BiomeSelectors.includeByKey(SurvivalReimaginedModBiomes.WISTERIA_FOREST),
				GenerationStep.Decoration.VEGETAL_DECORATION,
				featureKey);
	}

	private static void addRadiantForestVegetation(String path) {
		ResourceKey<PlacedFeature> featureKey = ResourceKey.create(
				Registries.PLACED_FEATURE,
				SurvivalReimaginedMod.asResource(path));
		BiomeModifications.addFeature(
				BiomeSelectors.includeByKey(SurvivalReimaginedModBiomes.RADIANT_FOREST),
				GenerationStep.Decoration.VEGETAL_DECORATION,
				featureKey);
	}

	private static void addRadiantForestUndergroundDecoration(String path) {
		ResourceKey<PlacedFeature> featureKey = ResourceKey.create(
				Registries.PLACED_FEATURE,
				SurvivalReimaginedMod.asResource(path));
		BiomeModifications.addFeature(
				BiomeSelectors.includeByKey(SurvivalReimaginedModBiomes.RADIANT_FOREST),
				GenerationStep.Decoration.VEGETAL_DECORATION,
				featureKey);
	}
	@SafeVarargs
	private static void addFluidSprings(String path, ResourceKey<net.minecraft.world.level.biome.Biome>... biomes) {
		ResourceKey<PlacedFeature> featureKey = ResourceKey.create(
				Registries.PLACED_FEATURE,
				SurvivalReimaginedMod.asResource(path));
		BiomeModifications.addFeature(
				BiomeSelectors.includeByKey(biomes),
				GenerationStep.Decoration.FLUID_SPRINGS,
				featureKey);
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

	private static void addPointedStonesUndergroundEverywhere(String path) {
		ResourceKey<PlacedFeature> featureKey = ResourceKey.create(
				Registries.PLACED_FEATURE,
				SurvivalReimaginedMod.asResource(path));
		BiomeModifications.addFeature(
				BiomeSelectors.excludeByKey(SurvivalReimaginedModBiomes.RADIANT_FOREST),
				GenerationStep.Decoration.UNDERGROUND_DECORATION,
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
