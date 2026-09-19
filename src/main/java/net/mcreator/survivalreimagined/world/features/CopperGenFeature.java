package net.mcreator.survivalreimagined.world.features;

import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.RandomPatchFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;

import net.mcreator.survivalreimagined.procedures.FlintGenAdditionalGenerationConditionProcedure;

public class CopperGenFeature extends RandomPatchFeature {
	public CopperGenFeature() {
		super(RandomPatchConfiguration.CODEC);
	}

	@Override
	public boolean place(FeaturePlaceContext<RandomPatchConfiguration> context) {
		WorldGenLevel world = context.level();
		int x = context.origin().getX();
		int y = context.origin().getY();
		int z = context.origin().getZ();
		return FlintGenAdditionalGenerationConditionProcedure.execute(world, x, y, z) && super.place(context);
	}
}
