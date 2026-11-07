package net.mcreator.survivalreimagined.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.LevelAccessor;

public class FlintGenAdditionalGenerationConditionProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z) {
		var biome = world.getBiome(BlockPos.containing(x, y, z));
		return biome.is(TagKey.create(Registries.BIOME, ResourceLocation.parse("minecraft:is_forest")))
				|| biome.is(TagKey.create(Registries.BIOME, ResourceLocation.parse("minecraft:is_jungle")))
				|| biome.is(TagKey.create(Registries.BIOME, ResourceLocation.parse("minecraft:is_taiga")))
				|| biome.is(TagKey.create(Registries.BIOME, ResourceLocation.parse("minecraft:is_desert")))
				|| biome.is(TagKey.create(Registries.BIOME, ResourceLocation.parse("minecraft:is_badlands")))
				|| biome.is(TagKey.create(Registries.BIOME, ResourceLocation.parse("minecraft:is_savanna")))
				|| biome.is(TagKey.create(Registries.BIOME, ResourceLocation.parse("minecraft:is_swamp")))
				|| biome.is(ResourceLocation.parse("minecraft:plains"));
	}
}
