package net.mcreator.survivalreimagined.compat;


import dev.lambdaurora.lambdynlights.api.DynamicLightsInitializer;
import dev.lambdaurora.lambdynlights.api.item.ItemLightSourceManager;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;

public class SurvivalReimaginedDynamicLights implements DynamicLightsInitializer {
    @Override
    @SuppressWarnings("removal") // Deprecated method for version 1.21.4+, SR uses 1.21.1. Skipping warning.
    public void onInitializeDynamicLights(ItemLightSourceManager itemLightSourceManager) {
        itemLightSourceManager.onRegisterEvent().register(context -> {
            context.register(SurvivalReimaginedModItems.CRUDE_TORCH.get(), 0);
        });
    }
}
