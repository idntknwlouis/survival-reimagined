package net.mcreator.survivalreimagined.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.util.RegistryEntry;
import net.mcreator.survivalreimagined.world.features.CopperGenFeature;
import net.mcreator.survivalreimagined.world.features.FlintGenFeature;
import net.mcreator.survivalreimagined.world.features.StoneGenFeature;
import net.mcreator.survivalreimagined.world.features.WildBeetrootFeature;
import net.mcreator.survivalreimagined.world.features.WildCarrotsFeature;
import net.mcreator.survivalreimagined.world.features.WildPotatoFeature;

import java.util.function.Supplier;

public final class SurvivalReimaginedModFeatures {
	public static final RegistryEntry<Feature<?>> FLINT_GEN = register("flint_gen", FlintGenFeature::new);
	public static final RegistryEntry<Feature<?>> STONE_GEN = register("stone_gen", StoneGenFeature::new);
	public static final RegistryEntry<Feature<?>> COPPER_GEN = register("copper_gen", CopperGenFeature::new);
	public static final RegistryEntry<Feature<?>> WILD_CARROTS = register("wild_carrots", WildCarrotsFeature::new);
	public static final RegistryEntry<Feature<?>> WILD_POTATO = register("wild_potato", WildPotatoFeature::new);
	public static final RegistryEntry<Feature<?>> WILD_BEETROOT = register("wild_beetroot", WildBeetrootFeature::new);

	private SurvivalReimaginedModFeatures() {
	}

	private static RegistryEntry<Feature<?>> register(String path, Supplier<? extends Feature<?>> factory) {
		var id = SurvivalReimaginedMod.asResource(path);
		Feature<?> feature = Registry.register(BuiltInRegistries.FEATURE, id, factory.get());
		return new RegistryEntry<>(id, feature);
	}

	public static void register() {
		// Forces class initialization.
	}
}
