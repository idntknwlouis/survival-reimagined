package net.mcreator.survivalreimagined.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class UraniumRodBlock extends Block {
	public static final IntegerProperty BLOCKSTATE = IntegerProperty.create("blockstate", 0, 3);

	public UraniumRodBlock() {
		super(BlockBehaviour.Properties.of()
				.sound(SoundType.METAL)
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
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState state = this.defaultBlockState();
		boolean below = context.getLevel().getBlockState(context.getClickedPos().below()).is(this);
		boolean above = context.getLevel().getBlockState(context.getClickedPos().above()).is(this);
		return state.setValue(BLOCKSTATE, stateFor(below, above));
	}

	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos fromPos, boolean moving) {
		super.neighborChanged(state, level, pos, neighborBlock, fromPos, moving);
		int next = stateFor(level.getBlockState(pos.below()).is(this), level.getBlockState(pos.above()).is(this));
		if (state.getValue(BLOCKSTATE) != next) {
			level.setBlock(pos, state.setValue(BLOCKSTATE, next), 3);
		}
	}

	private static int stateFor(boolean below, boolean above) {
		if (!below && above) return 1;
		if (below && !above) return 2;
		if (below) return 3;
		return 0;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return switch (state.getValue(BLOCKSTATE)) {
			case 1 -> Shapes.or(box(4, 0, 4, 12, 1, 12), box(6, 1, 6, 10, 16, 10), box(5, 1, 5, 11, 16, 11));
			case 2 -> Shapes.or(box(4, 15, 4, 12, 16, 12), box(6, 0, 6, 10, 15, 10), box(5, 0, 5, 11, 15, 11));
			case 3 -> Shapes.or(box(6, 0, 6, 10, 16, 10), box(5, 0, 5, 11, 16, 11));
			default -> Shapes.or(box(4, 15, 4, 12, 16, 12), box(4, 0, 4, 12, 1, 12), box(6, 1, 6, 10, 15, 10), box(5, 1, 5, 11, 15, 11));
		};
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}

	@Override
	public boolean skipRendering(BlockState state, BlockState adjacentState, net.minecraft.core.Direction side) {
		return adjacentState.is(this) || super.skipRendering(state, adjacentState, side);
	}
}
