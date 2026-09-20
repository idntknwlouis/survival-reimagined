package net.mcreator.survivalreimagined;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModParticles;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModScreens;
import net.mcreator.survivalreimagined.util.RuneInfusionTooltip;

public class SurvivalReimaginedModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		SurvivalReimaginedModParticles.register();
		SurvivalReimaginedModScreens.register();
		ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> RuneInfusionTooltip.append(stack, lines));
	}
}
