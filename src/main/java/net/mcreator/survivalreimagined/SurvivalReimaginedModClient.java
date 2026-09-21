package net.mcreator.survivalreimagined;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModParticles;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModScreens;
import net.mcreator.survivalreimagined.util.RuneInfusionTooltip;
import net.mcreator.survivalreimagined.util.AAFUpgradeTooltip;

public class SurvivalReimaginedModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		SurvivalReimaginedModParticles.register();
		SurvivalReimaginedModScreens.register();
		BlockRenderLayerMap.INSTANCE.putBlock(SurvivalReimaginedModBlocks.URANIUM_ROD.get(), RenderType.translucent());
		ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> {
			RuneInfusionTooltip.append(stack, lines);
			AAFUpgradeTooltip.append(stack, lines);
		});
	}
}
