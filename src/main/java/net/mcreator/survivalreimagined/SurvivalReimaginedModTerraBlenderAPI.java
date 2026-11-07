package net.mcreator.survivalreimagined;

import net.mcreator.survivalreimagined.world.worldgen.SurvivalReimaginedModOverworldRegion;
import net.mcreator.survivalreimagined.world.worldgen.SurvivalReimaginedModSurfaceRules;
import net.minecraft.resources.ResourceLocation;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class SurvivalReimaginedModTerraBlenderAPI implements TerraBlenderApi {
    @Override
    public void onTerraBlenderInitialized() {
        Regions.register(new SurvivalReimaginedModOverworldRegion(
                ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "radiated_forest"),
                4
                )
        );
        SurfaceRuleManager.addSurfaceRules(
                SurfaceRuleManager.RuleCategory.OVERWORLD,
                "survival_reimagined",
                SurvivalReimaginedModSurfaceRules.makeRules()
        );
    }
}
