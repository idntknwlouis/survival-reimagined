package net.mcreator.survivalreimagined.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;

public class UraniumRodBlock extends Block {
	public static final IntegerProperty BLOCKSTATE = IntegerProperty.create("blockstate", 0, 3);

	private static final VoxelShape SHAPE_STANDALONE = Shapes.or(
			box(4, 0, 4, 12, 1, 12),
			box(4, 15, 4, 12, 16, 12),
			box(5, 1, 5, 11, 15, 11)
	);
	private static final VoxelShape SHAPE_BOTTOM = Shapes.or(
			box(4, 0, 4, 12, 1, 12),
			box(5, 1, 5, 11, 16, 11)
	);
	private static final VoxelShape SHAPE_TOP = Shapes.or(
			box(4, 15, 4, 12, 16, 12),
			box(5, 0, 5, 11, 15, 11)
	);
	private static final VoxelShape SHAPE_MIDDLE = box(5, 0, 5, 11, 16, 11);

	public UraniumRodBlock() {
		super(BlockBehaviour.Properties.of()
				.strength(3.0F)
				.lightLevel(state -> 10)
				.requiresCorrectToolForDrops()
				.noOcclusion()
				.isRedstoneConductor((state, level, pos) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(BLOCKSTATE, 0));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BLOCKSTATE);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return switch (state.getValue(BLOCKSTATE)) {
			case 1 -> SHAPE_BOTTOM;
			case 2 -> SHAPE_TOP;
			case 3 -> SHAPE_MIDDLE;
			default -> SHAPE_STANDALONE;
		};
	}

	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos fromPos, boolean moving) {
		super.neighborChanged(state, level, pos, neighborBlock, fromPos, moving);
		updateConnections(level, pos);
	}

	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean moving) {
		super.onPlace(state, level, pos, oldState, moving);
		updateConnections(level, pos);
		if (level.getBlockState(pos.above()).is(this)) updateConnections(level, pos.above());
		if (level.getBlockState(pos.below()).is(this)) updateConnections(level, pos.below());
	}

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moving) {
		super.onRemove(state, level, pos, newState, moving);
		if (state.getBlock() != newState.getBlock()) {
			if (level.getBlockState(pos.above()).is(this)) updateConnections(level, pos.above());
			if (level.getBlockState(pos.below()).is(this)) updateConnections(level, pos.below());
		}
	}

	private static void updateConnections(Level level, BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		if (!state.is(SurvivalReimaginedModBlocks.URANIUM_ROD.get())) return;

		boolean above = level.getBlockState(pos.above()).is(SurvivalReimaginedModBlocks.URANIUM_ROD.get());
		boolean below = level.getBlockState(pos.below()).is(SurvivalReimaginedModBlocks.URANIUM_ROD.get());

		int value = above ? (below ? 3 : 1) : (below ? 2 : 0);
		if (state.getValue(BLOCKSTATE) != value) level.setBlock(pos, state.setValue(BLOCKSTATE, value), 3);
	}
}
