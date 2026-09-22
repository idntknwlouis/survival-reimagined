package net.mcreator.survivalreimagined.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;

public class ThinRadiatedVinesHeadBlock extends Block {
	public ThinRadiatedVinesHeadBlock() {
		super(BlockBehaviour.Properties.of()
				.noCollission()
				.noOcclusion()
				.instabreak()
				.randomTicks()
				.sound(SoundType.VINE)
				.isRedstoneConductor((state, level, pos) -> false));
	}

	private Block head() {
		return BuiltInRegistries.BLOCK.get(SurvivalReimaginedMod.asResource("thin_radiated_vines_tip"));
	}

	private Block body() {
		return BuiltInRegistries.BLOCK.get(SurvivalReimaginedMod.asResource("thin_radiated_vines"));
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		BlockState above = level.getBlockState(pos.above());
		return above.isSolidRender(level, pos.above()) || above.is(head()) || above.is(body());
	}

	@Override
	public BlockState updateShape(BlockState state, net.minecraft.core.Direction direction, BlockState neighborState,
			LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
		return canSurvive(state, level, pos) ? state : Blocks.AIR.defaultBlockState();
	}

	@Override
	protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (random.nextFloat() < 0.15F && level.isEmptyBlock(pos.below())) {
			level.setBlock(pos, body().defaultBlockState(), 3);
			level.setBlock(pos.below(), head().defaultBlockState(), 3);
		}
	}
}
