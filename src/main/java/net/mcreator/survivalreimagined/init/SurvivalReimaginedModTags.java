package net.mcreator.survivalreimagined.init;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class SurvivalReimaginedModTags {
    public static final TagKey<Block> MINEABLE_WITH_KNIFE = TagKey.create(
            Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "carcasses")
    );
}
