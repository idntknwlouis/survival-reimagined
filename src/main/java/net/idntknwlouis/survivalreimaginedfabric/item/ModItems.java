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
    public static final Item ACACIAPLANK = registerItem("acacia_plank", new Item(new Item.Settings()));

    // TODO: then finish compat items
    public static final Item ALPHAOAKPLANK = registerItem("alpha_oak_plank", new Item(new Item.Settings()));
    public static final Item BAOBABPLANK = registerItem("baobab_plank", new Item(new Item.Settings()));
    public static final Item BLACKWOODPLANK = registerItem("blackwood_plank", new Item(new Item.Settings()));
    public static final Item BRIMWOODPLANK = registerItem("brimwood_plank", new Item(new Item.Settings()));




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
        });
    }
}
