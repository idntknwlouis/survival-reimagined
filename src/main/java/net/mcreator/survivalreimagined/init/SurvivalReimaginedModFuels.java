package net.mcreator.survivalreimagined.init;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;

public class SurvivalReimaginedModFuels {
    public static void register() {
        Map<Item, Integer> fuels = new HashMap<>();

        //Barks
        fuels.put(SurvivalReimaginedModItems.OAK_BARK, 400);
        fuels.put(SurvivalReimaginedModItems.DARK_OAK_BARK, 400);
        fuels.put(SurvivalReimaginedModItems.SPRUCE_BARK, 400);
        fuels.put(SurvivalReimaginedModItems.ACACIA_BARK, 400);
        fuels.put(SurvivalReimaginedModItems.BIRCH_BARK, 400);
        fuels.put(SurvivalReimaginedModItems.MANGROVE_BARK, 400);
        fuels.put(SurvivalReimaginedModItems.MANDARIN_BARK, 400);
        fuels.put(SurvivalReimaginedModItems.RADIATED_BARK, 400);
        fuels.put(SurvivalReimaginedModItems.WISTERIA_BARK, 400);
        fuels.put(SurvivalReimaginedModItems.CHERRY_BARK, 400);
        fuels.put(SurvivalReimaginedModItems.JUNGLE_BARK, 400);

        //Planks
        fuels.put(SurvivalReimaginedModItems.OAK_PLANK, 400);
        fuels.put(SurvivalReimaginedModItems.DARK_OAK_PLANK, 400);
        fuels.put(SurvivalReimaginedModItems.SPRUCE_PLANK, 400);
        fuels.put(SurvivalReimaginedModItems.BIRCH_PLANK, 400);
        fuels.put(SurvivalReimaginedModItems.JUNGLE_PLANK, 400);
        fuels.put(SurvivalReimaginedModItems.MANGROVE_PLANK, 400);
        fuels.put(SurvivalReimaginedModItems.ACACIA_PLANK, 400);
        fuels.put(SurvivalReimaginedModItems.RADIATED_PLANK, 400);
        fuels.put(SurvivalReimaginedModItems.WISTERIA_PLANK, 400);
        fuels.put(SurvivalReimaginedModItems.MANDARIN_PLANK, 400);
        fuels.put(SurvivalReimaginedModItems.CHERRY_PLANK, 400);

        fuels.forEach(FuelRegistry.INSTANCE::add);
    }
}
