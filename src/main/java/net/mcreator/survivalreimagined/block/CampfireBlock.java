package net.mcreator.survivalreimagined.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.mcreator.survivalreimagined.block.entity.CampfireBlockEntity;

public class CampfireBlock extends Block implements EntityBlock {
	public static final BooleanProperty LIT = BooleanProperty.create("lit");
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	private static final TagKey<Item> STARTERS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "campfire_starters"));
	private static final VoxelShape SHAPE_NORTH_SOUTH = Shapes.or(
			box(1, 0, 0, 5, 4, 16),
			box(0, 3, 11, 16, 7, 15),
			box(11, 0, 0, 15, 4, 16),
			box(0, 3, 1, 16, 7, 5),
			box(5, 0, 0, 11, 1, 16));
	private static final VoxelShape SHAPE_EAST_WEST = Shapes.or(
			box(0, 0, 11, 16, 4, 15),
			box(11, 3, 0, 15, 7, 16),
			box(0, 0, 1, 16, 4, 5),
			box(1, 3, 0, 5, 7, 16),
			box(0, 0, 5, 16, 1, 11));

	public CampfireBlock() {
		super(BlockBehaviour.Properties.of()
				.sound(SoundType.WOOD)
				.strength(0.2f)
				.lightLevel(state -> state.getValue(LIT) ? 15 : 0)
				.noOcclusion()
				.isRedstoneConductor((state, level, pos) -> false));
		registerDefaultState(stateDefinition.any().setValue(LIT, false).setValue(FACING, Direction.NORTH));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> builder) {
		builder.add(LIT, FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState()
				.setValue(LIT, false)
				.setValue(FACING, context.getHorizontalDirection());
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return state.getValue(FACING).getAxis() == Direction.Axis.X ? SHAPE_EAST_WEST : SHAPE_NORTH_SOUTH;
	}

	@Override
	protected VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}

	@Override
	protected boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
		return true;
	}

	@Override
	protected int getLightBlock(BlockState state, BlockGetter level, BlockPos pos) {
		return 0;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new CampfireBlockEntity(pos, state);
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		super.animateTick(state, level, pos, random);
		if (!state.getValue(LIT)) return;

		boolean signal = level.getBlockState(pos.below()).is(Blocks.HAY_BLOCK);
		for (int i = 0; i < 3; i++) {
			double y = pos.getY() + 0.5D + random.nextDouble() * 0.2D;
			level.addParticle(signal ? ParticleTypes.CAMPFIRE_SIGNAL_SMOKE : ParticleTypes.CAMPFIRE_COSY_SMOKE,
					pos.getX() + 0.5D, y, pos.getZ() + 0.5D, 0.0D, 0.075D, 0.0D);
		}
		if (random.nextFloat() < 0.3F) {
			level.playLocalSound(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D,
					SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
		}
	}

	@Override
	protected boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
		BlockEntity blockEntity = level.getBlockEntity(pos);
		return blockEntity instanceof CampfireBlockEntity campfire
				? AbstractContainerMenu.getRedstoneSignalFromContainer(campfire)
				: 0;
	}

	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean moving) {
		super.onPlace(state, level, pos, oldState, moving);
		if (!level.isClientSide()) level.scheduleTick(pos, this, 1);
	}

	@Override
	protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		super.tick(state, level, pos, random);
		if (level.getBlockEntity(pos) instanceof CampfireBlockEntity campfire) CampfireBlockEntity.serverTick(level, pos, state, campfire);
		level.scheduleTick(pos, this, 1);
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
		open(level, pos, player);
		return InteractionResult.SUCCESS;
	}

	@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
			Player player, InteractionHand hand, BlockHitResult hit) {
		if (stack.is(STARTERS) && level.getBlockEntity(pos) instanceof CampfireBlockEntity campfire && campfire.hasFuel()) {
			if (!level.isClientSide()) {
				level.setBlock(pos, state.setValue(LIT, true), 3);
				level.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
			}
			return ItemInteractionResult.SUCCESS;
		}
		open(level, pos, player);
		return ItemInteractionResult.SUCCESS;
	}

	private static void open(Level level, BlockPos pos, Player player) {
		if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer
				&& level.getBlockEntity(pos) instanceof CampfireBlockEntity campfire) {
			serverPlayer.openMenu(campfire);
		}
	}

	@Override
	protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moving) {
		if (state.getBlock() != newState.getBlock()) {
			if (level.getBlockEntity(pos) instanceof CampfireBlockEntity campfire) {
				Containers.dropContents(level, pos, campfire);
				level.updateNeighbourForOutputSignal(pos, this);
			}
			super.onRemove(state, level, pos, newState, moving);
		}
	}
}
