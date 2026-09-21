package net.mcreator.survivalreimagined.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GeologySpikeBlock extends Block {
	public enum Thickness implements StringRepresentable {
		BASE("base"),
		MIDDLE("middle"),
		TIP("tip");

		private final String name;

		Thickness(String name) {
			this.name = name;
		}

		@Override
		public String getSerializedName() {
			return this.name;
		}
	}

	public static final DirectionProperty VERTICAL_DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;
	public static final EnumProperty<Thickness> THICKNESS = EnumProperty.create("thickness", Thickness.class);

	public GeologySpikeBlock(SoundType sound, float strength, float resistance) {
		super(BlockBehaviour.Properties.of()
				.sound(sound)
				.strength(strength, resistance)
				.requiresCorrectToolForDrops()
				.noOcclusion()
				.isRedstoneConductor((state, level, pos) -> false));
		registerDefaultState(stateDefinition.any()
				.setValue(VERTICAL_DIRECTION, Direction.UP)
				.setValue(THICKNESS, Thickness.TIP));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(VERTICAL_DIRECTION, THICKNESS);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Direction direction = context.getClickedFace() == Direction.DOWN ? Direction.DOWN : Direction.UP;
		return updateThickness(defaultBlockState().setValue(VERTICAL_DIRECTION, direction), context.getLevel(), context.getClickedPos());
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
		return switch (state.getValue(THICKNESS)) {
			case BASE -> box(1, 0, 1, 15, 16, 15);
			case MIDDLE -> box(3, 0, 3, 13, 16, 13);
			case TIP -> box(5, 0, 5, 11, 16, 11);
		};
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		Direction direction = state.getValue(VERTICAL_DIRECTION);
		BlockPos supportPos = pos.relative(direction.getOpposite());
		BlockState support = level.getBlockState(supportPos);
		return !support.isAir() && (support.isSolidRender(level, supportPos) || isMatchingSpike(support, direction));
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
			LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
		if (!state.canSurvive(level, pos)) {
			return Blocks.AIR.defaultBlockState();
		}
		return updateThickness(state, level, pos);
	}

	private BlockState updateThickness(BlockState state, LevelReader level, BlockPos pos) {
		Direction direction = state.getValue(VERTICAL_DIRECTION);
		boolean sameTowardTip = isMatchingSpike(level.getBlockState(pos.relative(direction)), direction);
		boolean sameTowardBase = isMatchingSpike(level.getBlockState(pos.relative(direction.getOpposite())), direction);

		Thickness thickness;
		if (!sameTowardTip) {
			thickness = Thickness.TIP;
		} else if (!sameTowardBase) {
			thickness = Thickness.BASE;
		} else {
			thickness = Thickness.MIDDLE;
		}
		return state.setValue(THICKNESS, thickness);
	}

	private boolean isMatchingSpike(BlockState state, Direction direction) {
		return state.is(this) && state.getValue(VERTICAL_DIRECTION) == direction;
	}
}
