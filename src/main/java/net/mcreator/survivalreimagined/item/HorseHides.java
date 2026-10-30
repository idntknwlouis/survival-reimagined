package net.mcreator.survivalreimagined.item;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.util.RegistryEntry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;

public class HorseHides {
    public static final Map<String, Item> HIDES = new HashMap<>();

    private static final String[] BASE_COLORS = {
            "white", "creamy", "chestnut", "brown", "dark_brown", "gray", "black"
    };

    private static final String[] VARIANTS = {
            "", "_stockings", "_field", "_white_dots", "_black_dots"
    };

    public static void registerItems() {
        for (String color : BASE_COLORS) {
            for (String variant : VARIANTS) {
                String itemId = color + "_horse_hide" + variant;
                Item hideItem = new Item(new Item.Properties());
                Registry.register(
                        BuiltInRegistries.ITEM,
                        ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, itemId),
                        hideItem
                );
                HIDES.put(itemId, hideItem);
            }
        }
    }
    public static Item get(String id) {
        return HIDES.get(id.toLowerCase());
    }
}
