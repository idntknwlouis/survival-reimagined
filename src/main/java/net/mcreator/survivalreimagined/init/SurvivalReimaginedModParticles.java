package net.mcreator.survivalreimagined.init;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

import net.mcreator.survivalreimagined.client.particle.BlackSmokeParticle;
import net.mcreator.survivalreimagined.client.particle.FallingWisteriaDarkParticle;
import net.mcreator.survivalreimagined.client.particle.FallingWisteriaParticle;
import net.mcreator.survivalreimagined.client.particle.RadiatedParticle;
import net.mcreator.survivalreimagined.client.particle.RadiationParticle2Particle;
import net.mcreator.survivalreimagined.client.particle.RadiationParticleParticle;
import net.mcreator.survivalreimagined.client.particle.StoneGrindingParticle;

public final class SurvivalReimaginedModParticles {
	private SurvivalReimaginedModParticles() {
	}

	public static void register() {
		ParticleFactoryRegistry registry = ParticleFactoryRegistry.getInstance();
		registry.register(SurvivalReimaginedModParticleTypes.STONE_GRINDING.get(), StoneGrindingParticle::provider);
		registry.register(SurvivalReimaginedModParticleTypes.RADIATED.get(), RadiatedParticle::provider);
		registry.register(SurvivalReimaginedModParticleTypes.RADIATION_PARTICLE.get(), RadiationParticleParticle::provider);
		registry.register(SurvivalReimaginedModParticleTypes.RADIATION_PARTICLE_2.get(), RadiationParticle2Particle::provider);
		registry.register(SurvivalReimaginedModParticleTypes.FALLING_WISTERIA.get(), FallingWisteriaParticle::provider);
		registry.register(SurvivalReimaginedModParticleTypes.FALLING_WISTERIA_DARK.get(), FallingWisteriaDarkParticle::provider);
		registry.register(SurvivalReimaginedModParticleTypes.BLACK_SMOKE.get(), BlackSmokeParticle::provider);
	}
}
