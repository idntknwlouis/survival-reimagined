package net.mcreator.survivalreimagined.init;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
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
		addUndergroundEverywhere("embedded_obsidian_feature");
		addUndergroundEverywhere("buried_diamond_ore");
		addUndergroundEverywhere("emerald_ore_feature");
		addUndergroundEverywhere("cinnabar_ore_feature");
		addUndergroundEverywhere("lazurite_ore");
		addUndergroundEverywhere("azurite_feature");
		addUndergroundEverywhere("malachite_feature");
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
		addRadiantForestUndergroundDecoration("radiated_tree");
		//addRadiantForestUndergroundDecoration("ceiling_radiated_moss_feature"); <- This feature was done for NeoForge as I did not know about SurfaceRules, will be removed and SurfaceRules will be back-ported to NeoForge
		addRadiantForestUndergroundDecoration("radiated_grass_patch");
		addRadiantForestUndergroundDecoration("radiated_orchid_feature");
		addRadiantForestLocalModification("radiated_bush_feature");
		addWisteriaForestVegetation("wisteria_tree_1");
		addWisteriaForestVegetation("wisteria_bushes");
		addWisteriaForestVegetation("wisteria_leaf_litter_patch");
		addWisteriaForestVegetation("wsiteria_flower_feature");
		addTropicalCoastVegetation("bamboo");
		addTropicalCoastVegetation("palm_trees");
		addTropicalCoastVegetation("grass_patch");
		addTropicalCoastVegetation("grass_patches");
		addTropicalCoastVegetation("tropical_bushes");
		addTropicalCoastVegetation("aloe_vera_feature");
		addUndergroundEverywhere("liginite_ore_feature_surface");
		addUndergroundEverywhere("ilmenite_ore_feature");
		addUndergroundEverywhere("anthracite_ore_feature");
		addUndergroundEverywhere("basalt_anthracite_feature");
		addUndergroundEverywhere("basalt_calaverite_feature");
		addUndergroundEverywhere("basalt_hematite_feature");
		addUndergroundEverywhere("basalt_ilmenite_feature");
		addUndergroundEverywhere("basalt_liginite_feature");
		addUndergroundEverywhere("basalt_magnetite_feature");
		addUndergroundEverywhere("basalt_uranophane_feature");
		addUndergroundEverywhere("bituminous_ore_feature");
		addUndergroundEverywhere("liginite_ore_feature");
		addUndergroundEverywhere("uraninite_feature");
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
		addVegetationToBiomes("apple_oak_tree", Biomes.PLAINS, Biomes.MEADOW, Biomes.FOREST);
		addVegetationToBiomes("mandarin_trees", Biomes.PLAINS);
		addVegetationToBiomes("banana_trees", Biomes.JUNGLE, Biomes.BAMBOO_JUNGLE, Biomes.SPARSE_JUNGLE);
		addUndergroundToBiomes("mandarin_trees_cave", Biomes.LUSH_CAVES);
		addVegetationToBiomes("red_cherry_trees", Biomes.CHERRY_GROVE);
		addVegetationToBiomes("red_cherry_tree", Biomes.CHERRY_GROVE);
		addVegetationToBiomes("wild_strawberries", Biomes.MEADOW, Biomes.BIRCH_FOREST, Biomes.SAVANNA, Biomes.PLAINS, Biomes.TAIGA);
		addVegetationToBiomes("wild_raspberry", Biomes.BIRCH_FOREST, Biomes.CHERRY_GROVE, Biomes.FLOWER_FOREST, Biomes.FOREST, Biomes.GROVE, Biomes.TAIGA);
		addLocalEverywhere("medium_soil");
		addLocalEverywhere("nitre_feature");
		addLocalEverywhere("high_soil");
		addFluidSprings("lava_springs");
		addUndergroundDecorationEverywhere("deltas");
		addPointedStonesUndergroundEverywhere("basalt_stalagmite");
		addPointedStonesUndergroundEverywhere("basalt_stalagtite");
		addLocalEverywhere("kimberlite_feature");
		addPointedStonesUndergroundEverywhere("kimberlite_stalagmite");
		addPointedStonesUndergroundEverywhere("kimberlite_stalagtite");
		addNether("dark_cinder_blobs");
		addVegetationToBiomes("natural_rye", Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA, Biomes.TAIGA, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.WINDSWEPT_SAVANNA);
		addVegetationToBiomes("natural_spelt", Biomes.TAIGA, Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST);
		addVegetationToBiomes("wild_carrots", Biomes.PLAINS, Biomes.SUNFLOWER_PLAINS, Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA, Biomes.TAIGA);
		addVegetationToBiomes("wild_beetroot", Biomes.BEACH, Biomes.DESERT, Biomes.SAVANNA);
		addLocalToBiomes("sand_salt_feature", Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN);
		addUndergroundToBiomes("stone_salt_feature", Biomes.OCEAN, Biomes.WARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.DEEP_OCEAN, Biomes.COLD_OCEAN, Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_FROZEN_OCEAN, Biomes.FROZEN_OCEAN);
		addUndergroundEverywhere("sulfur_ore_feature");
		addLocalEverywhere("teosinte_feature");
		addUndergroundEverywhere("uraninite_feature");


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

	private static void addTropicalCoastVegetation(String path) {
		ResourceKey<PlacedFeature> featureKey = ResourceKey.create(
				Registries.PLACED_FEATURE,
				SurvivalReimaginedMod.asResource(path));
		BiomeModifications.addFeature(
				BiomeSelectors.includeByKey(SurvivalReimaginedModBiomes.TROPICAL_COAST),
				GenerationStep.Decoration.VEGETAL_DECORATION,
				featureKey);
	}

	private static void addLocalToBiomes(String path, ResourceKey<Biome>... biomes) {
		ResourceKey<PlacedFeature> featureKey = ResourceKey.create(
				Registries.PLACED_FEATURE,
				SurvivalReimaginedMod.asResource(path));
		BiomeModifications.addFeature(
				BiomeSelectors.includeByKey(biomes),
				GenerationStep.Decoration.LOCAL_MODIFICATIONS,
				featureKey);
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
				GenerationStep.Decoration.UNDERGROUND_DECORATION,
				featureKey);
	}

	private static void addRadiantForestLocalModification(String path) {
		ResourceKey<PlacedFeature> featureKey = ResourceKey.create(
				Registries.PLACED_FEATURE,
				SurvivalReimaginedMod.asResource(path));
		BiomeModifications.addFeature(
				BiomeSelectors.includeByKey(SurvivalReimaginedModBiomes.RADIANT_FOREST),
				GenerationStep.Decoration.LOCAL_MODIFICATIONS,
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
