package net.mcreator.survivalreimagined.procedures.loot_tables;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModCompostables implements ModInitializer {
    public static final TagKey<Item> COMPOSTABLE_SEEDS = TagKey.create(Registries.ITEM, ResourceLocation.parse("survival_reimagined:compostable_seeds"));
    public static final TagKey<Item> COMPOSTABLE_CROPS = TagKey.create(Registries.ITEM, ResourceLocation.parse("survival_reimagined:compostable_crops"));

    @Override
    public void onInitialize() {
        CompostingChanceRegistry.INSTANCE.add(COMPOSTABLE_CROPS, 0.65f);
        CompostingChanceRegistry.INSTANCE.add(COMPOSTABLE_SEEDS, 0.3f);
    }
}
