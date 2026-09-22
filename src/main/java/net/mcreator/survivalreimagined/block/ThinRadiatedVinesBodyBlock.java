package net.mcreator.survivalreimagined.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;

public class ThinRadiatedVinesBodyBlock extends Block {
	public ThinRadiatedVinesBodyBlock() {
		super(BlockBehaviour.Properties.of()
				.noCollission()
				.noOcclusion()
				.instabreak()
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
	public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
			LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
		if (!canSurvive(state, level, pos)) {
			return Blocks.AIR.defaultBlockState();
		}
		if (direction == Direction.DOWN && level.getBlockState(pos.below()).isAir()) {
			return head().defaultBlockState();
		}
		return state;
	}
}
