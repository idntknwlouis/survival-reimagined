package net.mcreator.survivalreimagined.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class CornCropBlock extends SimpleAgeCropBlock {
	private final Supplier<? extends Block> middle;
	private final Supplier<? extends Block> top;

	public CornCropBlock(Supplier<? extends Block> middle, Supplier<? extends Block> top) {
		super(11);
		this.middle = middle;
		this.top = top;
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		super.randomTick(state, level, pos, random);
		syncUpper(level, pos, level.getBlockState(pos));
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		super.performBonemeal(level, random, pos, state);
		syncUpper(level, pos, level.getBlockState(pos));
	}

	private void syncUpper(ServerLevel level, BlockPos pos, BlockState state) {
		int age = getAge(state);
		BlockPos middlePos = pos.above();
		BlockPos topPos = pos.above(2);

		if (age >= 4) {
			BlockState middleState = ((CornUpperBlock) middle.get()).withAge(Math.min(7, age - 4));
			if (level.getBlockState(middlePos).canBeReplaced() || level.getBlockState(middlePos).is(middle.get())) {
				level.setBlock(middlePos, middleState, 2);
			}
		}

		if (age >= 8) {
			BlockState topState = ((CornUpperBlock) top.get()).withAge(Math.min(4, age - 7));
			if (level.getBlockState(topPos).canBeReplaced() || level.getBlockState(topPos).is(top.get())) {
				level.setBlock(topPos, topState, 2);
			}
		}
	}
}
