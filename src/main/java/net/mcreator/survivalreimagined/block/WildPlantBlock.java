package net.mcreator.survivalreimagined.block;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class WildPlantBlock extends BushBlock {
	public WildPlantBlock() {
		super(BlockBehaviour.Properties.of()
				.sound(SoundType.GRASS)
				.instabreak()
				.noCollission());
	}

	@Override
	protected boolean mayPlaceOn(BlockState floor, BlockGetter level, BlockPos pos) {
		return floor.is(BlockTags.DIRT) || floor.is(Blocks.FARMLAND);
	}
}
