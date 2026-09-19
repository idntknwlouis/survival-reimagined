package net.mcreator.survivalreimagined.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

public class ClayMoldBlock extends Block {
	public static final BooleanProperty CAN_BURN = BooleanProperty.create("can_burn");
	private static final VoxelShape SHAPE = box(0, 0, 0, 16, 2, 16);
	private final Supplier<? extends Block> driedBlock;

	public ClayMoldBlock(Supplier<? extends Block> driedBlock) {
		super(BlockBehaviour.Properties.of().sound(SoundType.MUD).instabreak().noOcclusion().isRedstoneConductor((state, level, pos) -> false));
		this.driedBlock = driedBlock;
		this.registerDefaultState(this.stateDefinition.any().setValue(CAN_BURN, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(CAN_BURN);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
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
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		return level.getBlockState(pos.below()).canOcclude();
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
		return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
	}

	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean moving) {
		super.onPlace(state, level, pos, oldState, moving);
		if (!level.isClientSide()) level.scheduleTick(pos, this, 40);
	}

	@Override
	protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		boolean drying = level.isDay() && !level.isRaining() && level.canSeeSkyFromBelowWater(pos);
		if (state.getValue(CAN_BURN) != drying) {
			level.setBlock(pos, state.setValue(CAN_BURN, drying), 3);
			state = level.getBlockState(pos);
		}
		if (drying) {
			boolean dryNow = random.nextFloat() < 0.025F;
			if (!dryNow && level.getBlockState(pos.below(2)).isAir()) dryNow = random.nextFloat() < 0.05F;
			if (dryNow) {
				level.setBlock(pos, this.driedBlock.get().defaultBlockState(), 3);
				level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
				return;
			}
		}
		level.scheduleTick(pos, this, 40);
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (!state.getValue(CAN_BURN)) return;
		double x = pos.getX() + random.nextDouble();
		double y = pos.getY() + random.nextDouble() * 0.25;
		double z = pos.getZ() + random.nextDouble();
		level.addParticle(ParticleTypes.WHITE_SMOKE, x, y, z, 0, 0.0025, 0);
		if (level.getBlockState(pos.below(2)).is(net.minecraft.world.level.block.Blocks.LAVA)) {
			level.addParticle(ParticleTypes.SMOKE, x, y, z, 0, 0.0025, 0);
		}
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (!level.isClientSide() && level.getBlockState(pos).is(this)) {
			int count = 2 + level.random.nextInt(2);
			level.destroyBlock(pos, false);
			popResource(level, pos, new ItemStack(Items.CLAY_BALL, count));
		}
		super.entityInside(state, level, pos, entity);
	}
}
