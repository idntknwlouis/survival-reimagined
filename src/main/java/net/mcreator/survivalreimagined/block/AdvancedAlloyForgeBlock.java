package net.mcreator.survivalreimagined.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;

import net.mcreator.survivalreimagined.block.entity.AdvancedAlloyForgeBlockEntity;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;

public class AdvancedAlloyForgeBlock extends Block implements EntityBlock {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

	public AdvancedAlloyForgeBlock() {
		super(BlockBehaviour.Properties.of().sound(net.mcreator.survivalreimagined.init.SurvivalReimaginedModSoundTypes.STEEL).strength(4.0F).requiresCorrectToolForDrops());
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState state = super.getStateForPlacement(context);
		return state == null ? null : state.setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}

	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean moving) {
		super.onPlace(state, level, pos, oldState, moving);
		if (!level.isClientSide()) level.scheduleTick(pos, this, 2);
	}

	@Override
	protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		super.tick(state, level, pos, random);
		if (level.getBlockEntity(pos) instanceof AdvancedAlloyForgeBlockEntity forge) {
			AdvancedAlloyForgeBlockEntity.serverTick(level, pos, state, forge);
		}
		level.scheduleTick(pos, this, 2);
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
		handleUse(level, pos, player);
		return InteractionResult.SUCCESS;
	}

	@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
			Player player, InteractionHand hand, BlockHitResult hit) {
		handleUse(level, pos, player);
		return ItemInteractionResult.SUCCESS;
	}

	private static void handleUse(Level level, BlockPos pos, Player player) {
		if (level.isClientSide() || !(player instanceof ServerPlayer serverPlayer)
				|| !(level.getBlockEntity(pos) instanceof AdvancedAlloyForgeBlockEntity forge)) {
			return;
		}
		if (hasValidSetup(level, pos)) {
			serverPlayer.openMenu(forge);
			awardAdvancement(serverPlayer, "build_aaf");
		} else {
			serverPlayer.displayClientMessage(Component.literal("Incomplete Block Setup"), true);
		}
	}

	public static boolean hasValidSetup(Level level, BlockPos forgePos) {
		for (Direction forward : Direction.Plane.HORIZONTAL) {
			if (matchesSetup(level, forgePos, forward)) return true;
		}
		return false;
	}

	private static boolean matchesSetup(Level level, BlockPos origin, Direction forward) {
		Direction right = forward.getClockWise();

		// 3x3 titanium floor.
		for (int depth = 0; depth <= 2; depth++) {
			for (int side = -1; side <= 1; side++) {
				if (!isTitanium(level, offset(origin, forward, right, depth, side, -1))) return false;
			}
		}

		// Titanium ceiling: full rows at front/back, open center over the chamber.
		for (int side = -1; side <= 1; side++) {
			if (!isTitanium(level, offset(origin, forward, right, 0, side, 1))) return false;
			if (!isTitanium(level, offset(origin, forward, right, 2, side, 1))) return false;
		}
		if (!isTitanium(level, offset(origin, forward, right, 1, -1, 1))
				|| !isTitanium(level, offset(origin, forward, right, 1, 1, 1))) return false;
		if (!level.getBlockState(offset(origin, forward, right, 1, 0, 1)).isAir()) return false;

		// Middle layer: uranium rods on the four corners, titanium side walls, open chamber.
		if (!isUraniumRod(level, offset(origin, forward, right, 0, -1, 0))
				|| !isUraniumRod(level, offset(origin, forward, right, 0, 1, 0))
				|| !isUraniumRod(level, offset(origin, forward, right, 2, -1, 0))
				|| !isUraniumRod(level, offset(origin, forward, right, 2, 1, 0))) return false;
		if (!isTitanium(level, offset(origin, forward, right, 1, -1, 0))
				|| !isTitanium(level, offset(origin, forward, right, 1, 1, 0))
				|| !isTitanium(level, offset(origin, forward, right, 2, 0, 0))) return false;
		return level.getBlockState(offset(origin, forward, right, 1, 0, 0)).isAir();
	}

	private static BlockPos offset(BlockPos origin, Direction forward, Direction right, int depth, int side, int y) {
		return origin.relative(forward, depth).relative(right, side).offset(0, y, 0);
	}

	private static boolean isTitanium(Level level, BlockPos pos) {
		return level.getBlockState(pos).is(SurvivalReimaginedModBlocks.BLOCK_OF_TITANIUM.get());
	}

	private static boolean isUraniumRod(Level level, BlockPos pos) {
		return level.getBlockState(pos).is(SurvivalReimaginedModBlocks.URANIUM_ROD.get());
	}

	private static void awardAdvancement(ServerPlayer player, String id) {
		AdvancementHolder advancement = player.server.getAdvancements()
				.get(ResourceLocation.fromNamespaceAndPath("survival_reimagined", id));
		if (advancement == null) return;
		var progress = player.getAdvancements().getOrStartProgress(advancement);
		if (progress.isDone()) return;
		for (String criterion : progress.getRemainingCriteria()) {
			player.getAdvancements().award(advancement, criterion);
		}
	}

	@Override
	public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
		BlockEntity blockEntity = level.getBlockEntity(pos);
		return blockEntity instanceof MenuProvider provider ? provider : null;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new AdvancedAlloyForgeBlockEntity(pos, state);
	}

	@Override
	protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moving) {
		if (state.getBlock() != newState.getBlock()) {
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if (blockEntity instanceof AdvancedAlloyForgeBlockEntity forge) {
				Containers.dropContents(level, pos, forge);
				level.updateNeighbourForOutputSignal(pos, this);
			}
		}
		super.onRemove(state, level, pos, newState, moving);
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
		return level.getBlockEntity(pos) instanceof AdvancedAlloyForgeBlockEntity forge
				? AbstractContainerMenu.getRedstoneSignalFromContainer(forge) : 0;
	}
}
