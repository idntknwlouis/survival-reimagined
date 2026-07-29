package net.idntknwlouis.survivalreimaginedfabric.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.idntknwlouis.survivalreimaginedfabric.SurvivalReimaginedFabric;
import net.minecraft.item.Item;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    // TODO: Make at least a functional block
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

    // Barks
    public static final Item LARGEOAKBARK = registerItem("large_oak_bark", new Item(new Item.Settings()));
    public static final Item LARGEDARKOAKBARK = registerItem("large_dark_oak_bark", new Item(new Item.Settings()));
    public static final Item LARGESPRUCEBARK = registerItem("large_spruce_bark", new Item(new Item.Settings()));
    public static final Item LARGEBIRCHBARK = registerItem("large_birch_bark", new Item(new Item.Settings()));
    public static final Item LARGEACACIABARK = registerItem("large_acacia_bark", new Item(new Item.Settings()));
    public static final Item LARGEJUNGLEBARK = registerItem("large_jungle_bark", new Item(new Item.Settings()));
    public static final Item LARGECHERRYBARK = registerItem("large_cherry_bark", new Item(new Item.Settings()));
    public static final Item LARGEMANGROVEBARK = registerItem("large_mangrove_bark", new Item(new Item.Settings()));
    public static final Item LARGECRIMSONBARK = registerItem("large_crimson_bark", new Item(new Item.Settings()));
    public static final Item LARGEWARPEDBARK = registerItem("large_warped_bark", new Item(new Item.Settings()));

    // Blocks




    // TODO: finish compat items
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

            entries.add(OAKPLANK);
            entries.add(DARKOAKPLANK);
            entries.add(SPRUCEPLANK);
            entries.add(ACACIAPLANK);
            entries.add(JUNGLEPLANK);
            entries.add(CHERRYPLANK);
            entries.add(MANGROVEPLANK);
            entries.add(CRIMSONPLANK);
            entries.add(WARPEDPLANK);

            entries.add(LARGEOAKBARK);
            entries.add(LARGEDARKOAKBARK);
            entries.add(LARGESPRUCEBARK);
            entries.add(LARGEBIRCHBARK);
            entries.add(LARGEJUNGLEBARK);
            entries.add(LARGECHERRYBARK);
            entries.add(LARGEMANGROVEBARK);
            entries.add(LARGECRIMSONBARK);
            entries.add(LARGEWARPEDBARK);


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
