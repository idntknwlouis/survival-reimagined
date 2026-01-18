package net.idntknwlouis.survivalreimaginedfabric.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.idntknwlouis.survivalreimaginedfabric.SurvivalReimaginedFabric;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    // TODO: finish base mod items
    // Planks
    public static final Item OAKPLANK = registerItem("oak_plank", new Item(new Item.Settings()));
    public static final Item DARKOAKPLANK = registerItem("dark_oak_plank", new Item(new Item.Settings()));
    public static final Item SPRUCEPLANK = registerItem("spruce_plank", new Item(new Item.Settings()));
    public static final Item BIRCHPLANK = registerItem("birch_plank", new Item(new Item.Settings()));
    public static final Item ACACIAPLANK = registerItem("acacia_plank", new Item(new Item.Settings()));
    public static final Item JUNGLEPLANK = registerItem("jungle_plank", new Item(new Item.Settings()));
    public static final Item CHERRYPLANK = registerItem("cherry_plank", new Item(new Item.Settings()));
    public static final Item MANGROVEPLANK = registerItem("mangrove_plank", new Item(new Item.Settings()));
    public static final Item CRIMSONPLANK = registerItem("crimson_plank", new Item(new Item.Settings()));
    public static final Item WARPEDPLANK = registerItem("warped_plank", new Item(new Item.Settings()));

    // TODO: then finish compat items
    // Planks
    public static final Item ALPHAOAKPLANK = registerItem("alpha_oak_plank", new Item(new Item.Settings()));
    public static final Item BAOBABPLANK = registerItem("baobab_plank", new Item(new Item.Settings()));
    public static final Item BLACKWOODPLANK = registerItem("blackwood_plank", new Item(new Item.Settings()));
    public static final Item BRIMWOODPLANK = registerItem("brimwood_plank", new Item(new Item.Settings()));
    public static final Item COBALTPLANK = registerItem("cobalt_plank", new Item(new Item.Settings()));
    public static final Item CYPRESSPLANK = registerItem("cypress_plank", new Item(new Item.Settings()));
    public static final Item DEADPLANK = registerItem("dead_plank", new Item(new Item.Settings()));
    public static final Item EUCALYPTUSPLANK = registerItem("eucalyptus_plank", new Item(new Item.Settings()));
    public static final Item JOSHUAPLANK = registerItem("joshua_plank", new Item(new Item.Settings()));




    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(SurvivalReimaginedFabric.MOD_ID, name), item);
    }

    public static void registerModItems() {
        SurvivalReimaginedFabric.LOGGER.info("Registering Mod Items " + SurvivalReimaginedFabric.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(ACACIAPLANK);
            entries.add(ALPHAOAKPLANK);
            entries.add(BAOBABPLANK);
            entries.add(BLACKWOODPLANK);
            entries.add(BRIMWOODPLANK);
            entries.add(COBALTPLANK);
            entries.add(CYPRESSPLANK);
            entries.add(DEADPLANK);
            entries.add(EUCALYPTUSPLANK);
            entries.add(JOSHUAPLANK);
        });
    }
}
