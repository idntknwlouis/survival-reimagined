package net.mcreator.survivalreimagined.util;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.minecraft.world.item.ItemStack;

public class BackpackTierType {
    public static String execute(ItemStack stack) {
        if (stack.getItem() == SurvivalReimaginedModItems.BACKPACK_T1.get()) {
            return "\u00A77 Tier 1";
        } else if (stack.getItem() == SurvivalReimaginedModItems.BACKPACK_T2.get()) {
            return "\u00A77 Tier 2";
        } else if (stack.getItem() == SurvivalReimaginedModItems.BACKPACK_T3.get()) {
            return "\u00A77 Tier 3";
        }
        return "\n";
    }
}
