package net.mcreator.survivalreimagined.world.features;

import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.RandomPatchFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;

import net.mcreator.survivalreimagined.procedures.DisablePlacementFavorFarmersDelightProcedure;

public class WildBeetrootFeature extends RandomPatchFeature {
	public WildBeetrootFeature() {
		super(RandomPatchConfiguration.CODEC);
	}

	@Override
	public boolean place(FeaturePlaceContext<RandomPatchConfiguration> context) {
		return DisablePlacementFavorFarmersDelightProcedure.execute() && super.place(context);
	}
}
