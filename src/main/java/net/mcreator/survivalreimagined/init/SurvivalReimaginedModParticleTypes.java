package net.mcreator.survivalreimagined.init;

import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.util.RegistryEntry;

public final class SurvivalReimaginedModParticleTypes {
	public static final RegistryEntry<SimpleParticleType> STONE_GRINDING = register("stone_grinding", true);
	public static final RegistryEntry<SimpleParticleType> RADIATED = register("radiated", false);
	public static final RegistryEntry<SimpleParticleType> RADIATION_PARTICLE = register("radiation_particle", false);
	public static final RegistryEntry<SimpleParticleType> RADIATION_PARTICLE_2 = register("radiation_particle_2", false);
	public static final RegistryEntry<SimpleParticleType> FALLING_WISTERIA = register("falling_wisteria", false);
	public static final RegistryEntry<SimpleParticleType> FALLING_WISTERIA_DARK = register("falling_wisteria_dark", false);
	public static final RegistryEntry<SimpleParticleType> BLACK_SMOKE = register("black_smoke", false);

	private SurvivalReimaginedModParticleTypes() {
	}

	private static RegistryEntry<SimpleParticleType> register(String path, boolean overrideLimiter) {
		var id = SurvivalReimaginedMod.asResource(path);
		SimpleParticleType particle = FabricParticleTypes.simple(overrideLimiter);
		Registry.register(BuiltInRegistries.PARTICLE_TYPE, id, particle);
		return new RegistryEntry<>(id, particle);
	}

	public static void register() {
		// Forces class initialization.
	}
}
