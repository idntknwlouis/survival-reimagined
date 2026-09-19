package net.mcreator.survivalreimagined;

import net.fabricmc.api.ClientModInitializer;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModParticles;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModScreens;

public class SurvivalReimaginedModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		SurvivalReimaginedModParticles.register();
		SurvivalReimaginedModScreens.register();
	}
}
