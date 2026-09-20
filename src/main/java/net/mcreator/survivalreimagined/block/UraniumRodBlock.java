package net.mcreator.survivalreimagined.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
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
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModParticleTypes;

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
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		super.animateTick(state, level, pos, random);

		double x = pos.getX() + 0.25 + random.nextDouble() * 0.5;
		double y = pos.getY() + random.nextDouble();
		double z = pos.getZ() + 0.25 + random.nextDouble() * 0.5;
		level.addParticle(SurvivalReimaginedModParticleTypes.RADIATION_PARTICLE.get(), x, y, z, 0.0, 0.0, 0.0);

		x = pos.getX() + 0.25 + random.nextDouble() * 0.5;
		y = pos.getY() + random.nextDouble();
		z = pos.getZ() + 0.25 + random.nextDouble() * 0.5;
		level.addParticle(SurvivalReimaginedModParticleTypes.RADIATION_PARTICLE_2.get(), x, y, z, 0.0, 0.0, 0.0);

		if (random.nextFloat() < 0.1F) {
			level.playLocalSound(
					pos.getX() + 0.5,
					pos.getY() + 0.5,
					pos.getZ() + 0.5,
					SoundEvents.BEACON_AMBIENT,
					SoundSource.BLOCKS,
					1.0F,
					1.0F,
					false
			);
		}
	}

	@Override
	public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, net.minecraft.world.entity.player.Player player) {
		if (!level.isClientSide()) {
			level.playSound(null, pos, SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 0.8F, 1.0F);
		}
		return super.playerWillDestroy(level, pos, state, player);
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
