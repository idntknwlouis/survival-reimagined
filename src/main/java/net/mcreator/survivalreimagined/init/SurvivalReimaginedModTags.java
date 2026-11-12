package net.mcreator.survivalreimagined.init;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class SurvivalReimaginedModTags {
    public static final TagKey<Block> MINEABLE_WITH_KNIFE = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "carcasses"));
    public static final TagKey<Item> MERGEABLE_MEATS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("survival_reimagined", "mergeable_meats"));
    public static final TagKey<Block> INCORRECT_FOR_BRONZE_TOOL = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("fabric", "incorrect_for_bronze_tool"));
    public static final TagKey<Block> INCORRECT_FOR_STEEL_TOOL = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("fabric", "incorrect_for_steel_tool"));
}
