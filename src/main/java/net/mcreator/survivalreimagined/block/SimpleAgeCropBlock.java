package net.mcreator.survivalreimagined.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SimpleAgeCropBlock extends BushBlock implements BonemealableBlock {
	public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 15);
	private final int maxAge;
	private final boolean farmlandOnly;

	public SimpleAgeCropBlock(int maxAge) {
		super(BlockBehaviour.Properties.of()
				.sound(SoundType.CROP)
				.instabreak()
				.noCollission()
				.randomTicks());
		this.maxAge = maxAge;
		this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> builder) {
		builder.add(AGE);
	}

	public int getAge(BlockState state) {
		return state.getValue(AGE);
	}

	public boolean isMaxAge(BlockState state) {
		return getAge(state) >= this.maxAge;
	}

	@Override
	protected boolean mayPlaceOn(BlockState floor, BlockGetter level, BlockPos pos) {
		return this.farmlandOnly
				? floor.is(Blocks.FARMLAND)
				: floor.is(Blocks.GRASS_BLOCK) || floor.is(Blocks.DIRT) || floor.is(Blocks.COARSE_DIRT) || floor.is(Blocks.PODZOL);
	}

	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return !isMaxAge(state);
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (level.getRawBrightness(pos, 0) >= 9 && !isMaxAge(state) && random.nextInt(5) == 0) {
			level.setBlock(pos, state.setValue(this.ageProperty, getAge(state) + 1), 2);
		}
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		double height = 2.0D + (14.0D * getAge(state) / Math.max(1, this.maxAge));
		return box(0.0D, 0.0D, 0.0D, 16.0D, Math.min(16.0D, height), 16.0D);
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
		return !isMaxAge(state);
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		int age = Math.min(this.maxAge, getAge(state) + 1 + random.nextInt(2));
		level.setBlock(pos, state.setValue(this.ageProperty, age), 2);
	}
}
