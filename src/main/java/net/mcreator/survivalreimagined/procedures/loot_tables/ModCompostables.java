package net.mcreator.survivalreimagined.procedures.loot_tables;

import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;

public class ModCompostables {
    public static void register() {
        //Seeds
        CompostingChanceRegistry.INSTANCE.add(SurvivalReimaginedModItems.WHEAT_SEEDS.get(), 0.3f);
        CompostingChanceRegistry.INSTANCE.add(SurvivalReimaginedModItems.HEMP_SEEDS.get(), 0.3f);
        CompostingChanceRegistry.INSTANCE.add(SurvivalReimaginedModItems.CORN_SEEDS.get(), 0.3f);
        CompostingChanceRegistry.INSTANCE.add(SurvivalReimaginedModItems.RYE_SEEDS.get(), 0.3f);
        CompostingChanceRegistry.INSTANCE.add(SurvivalReimaginedModItems.SPELT_SEEDS.get(), 0.3f);

        //Crops
        CompostingChanceRegistry.INSTANCE.add(SurvivalReimaginedModItems.CARROT.get(), 0.65f);
        CompostingChanceRegistry.INSTANCE.add(SurvivalReimaginedModItems.CORN.get(), 0.65f);
        CompostingChanceRegistry.INSTANCE.add(SurvivalReimaginedModItems.CORN_ON_THE_COB.get(), 0.65f);
        CompostingChanceRegistry.INSTANCE.add(SurvivalReimaginedModItems.POTATO.get(), 0.65f);
        CompostingChanceRegistry.INSTANCE.add(SurvivalReimaginedModItems.HEMP_LEAF.get(), 0.65f);
        CompostingChanceRegistry.INSTANCE.add(SurvivalReimaginedModItems.RYE.get(), 0.65f);
        CompostingChanceRegistry.INSTANCE.add(SurvivalReimaginedModItems.SPELT.get(), 0.65f);
        CompostingChanceRegistry.INSTANCE.add(SurvivalReimaginedModItems.STRAWBERRY.get(), 0.65f);
        CompostingChanceRegistry.INSTANCE.add(SurvivalReimaginedModItems.RASPBERRY.get(), 0.65f);
    }
}
