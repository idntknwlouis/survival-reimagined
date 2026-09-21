package net.mcreator.survivalreimagined.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

public class GeologySpikeBlock extends Block {
	public enum Segment {
		BASE, MIDDLE, TIP
	}

	private final boolean growsUp;
	private final Segment segment;
	private final Supplier<? extends Block> base;
	private final Supplier<? extends Block> middle;
	private final Supplier<? extends Block> tip;

	public GeologySpikeBlock(SoundType sound, float strength, float resistance, boolean growsUp, Segment segment,
			Supplier<? extends Block> base, Supplier<? extends Block> middle, Supplier<? extends Block> tip) {
		super(BlockBehaviour.Properties.of()
				.sound(sound)
				.strength(strength, resistance)
				.requiresCorrectToolForDrops()
				.noOcclusion()
				.isRedstoneConductor((state, level, pos) -> false));
		this.growsUp = growsUp;
		this.segment = segment;
		this.base = base;
		this.middle = middle;
		this.tip = tip;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
		return true;
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter level, BlockPos pos) {
		return 0;
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return switch (segment) {
			case BASE -> box(1, 0, 1, 15, 16, 15);
			case MIDDLE -> box(3, 0, 3, 13, 16, 13);
			case TIP -> box(5, 0, 5, 11, 16, 11);
		};
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		BlockPos supportPos = growsUp ? pos.below() : pos.above();
		return !level.isEmptyBlock(supportPos);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
			LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
		return !state.canSurvive(level, pos)
				? Blocks.AIR.defaultBlockState()
				: super.updateShape(state, direction, neighborState, level, pos, neighborPos);
	}

	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos fromPos, boolean moving) {
		super.neighborChanged(state, level, pos, neighborBlock, fromPos, moving);
		if (level.isClientSide()) return;

		BlockPos tipSide = growsUp ? pos.above() : pos.below();
		Block next = level.getBlockState(tipSide).getBlock();
		Block desired = null;

		if (next == tip.get()) {
			desired = middle.get();
		} else if (next == middle.get()) {
			desired = base.get();
		} else if (level.isEmptyBlock(tipSide)) {
			desired = tip.get();
		}

		if (desired != null && desired != state.getBlock()) {
			level.setBlock(pos, desired.defaultBlockState(), 3);
		}
	}
}
