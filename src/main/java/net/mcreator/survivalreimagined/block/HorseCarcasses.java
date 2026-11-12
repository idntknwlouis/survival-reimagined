package net.mcreator.survivalreimagined.block;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.util.RegistryEntry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;

import static net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems.registerBlockItem;

public class HorseCarcasses {
    public static final Map<String, Block> HORSE_CARCASSES_BLOCK = new HashMap<>();
    public static final Map<String, RegistryEntry<Item>> HORSE_CARCASSES_ITEM = new HashMap<>();

    private static final String[] BASE_COLORS = {
            "white", "creamy", "chestnut", "brown", "dark_brown", "gray", "black"
    };

    private static final String[] VARIANTS = {
            "", "_stockings", "_field", "_white_dots", "_black_dots"
    };

    public static void registerBlocks() {
        for (String color : BASE_COLORS) {
            for (String variant : VARIANTS) {
                String ID = color + "_horse" + variant;
                String hideID = color + "_horse_hide" + variant;

                CarcassBlock carcass = new CarcassBlock(CarcassBlock.Species.HORSE, hideID);
                Registry.register(BuiltInRegistries.BLOCK, SurvivalReimaginedMod.asResource(ID), carcass);
                HORSE_CARCASSES_BLOCK.put(ID, carcass);
            }
        }
    }

    public static void registerItems() {
        for (var entry : HorseCarcasses.HORSE_CARCASSES_BLOCK.entrySet()) {
            String ID = entry.getKey();
            Block block = entry.getValue();

            RegistryEntry<Item> item = registerBlockItem(ID, () -> block);
            HORSE_CARCASSES_ITEM.put(ID, item);
        }
    }

    public static Item getCarcassItemForHorse(Horse horse) {
        int color = horse.getVariant().getId();
        int markings = horse.getMarkings().getId();

        if (color >= BASE_COLORS.length || markings >= VARIANTS.length) {
            color = 0;
            markings = 0;
        }

        String ID = BASE_COLORS[color] + "_horse" + VARIANTS[markings];
        RegistryEntry<Item> entry = HORSE_CARCASSES_ITEM.get(ID);

        return entry != null ? entry.get() : null;
    }
}
