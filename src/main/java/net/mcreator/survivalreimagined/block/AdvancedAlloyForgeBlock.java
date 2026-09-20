package net.mcreator.survivalreimagined.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
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
		super(BlockBehaviour.Properties.of().strength(4.0F).requiresCorrectToolForDrops());
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
		if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
			if (isSetupComplete(level, pos)) {
				if (level.getBlockEntity(pos) instanceof AdvancedAlloyForgeBlockEntity forge) serverPlayer.openMenu(forge);
			} else {
				player.displayClientMessage(net.minecraft.network.chat.Component.literal("Incomplete Block Setup"), true);
			}
		}
		return InteractionResult.SUCCESS;
	}

	public static boolean isSetupComplete(Level level, BlockPos forgePos) {
		for (Direction depth : new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST}) {
			if (matchesSetup(level, forgePos, depth)) return true;
		}
		return false;
	}

	private static boolean matchesSetup(Level level, BlockPos origin, Direction depth) {
		Direction side = depth.getClockWise();

		for (int d = 0; d <= 2; d++) {
			for (int s = -1; s <= 1; s++) {
				if (!level.getBlockState(origin.relative(depth, d).relative(side, s).below())
						.is(SurvivalReimaginedModBlocks.BLOCK_OF_TITANIUM.get())) return false;
			}
		}

		for (int d = 0; d <= 2; d++) {
			for (int s = -1; s <= 1; s++) {
				BlockPos p = origin.relative(depth, d).relative(side, s).above();
				if (d == 1 && s == 0) {
					if (!level.getBlockState(p).isAir()) return false;
				} else if (!level.getBlockState(p).is(SurvivalReimaginedModBlocks.BLOCK_OF_TITANIUM.get())) return false;
			}
		}

		for (int d : new int[]{0, 2}) {
			for (int s : new int[]{-1, 1}) {
				if (!level.getBlockState(origin.relative(depth, d).relative(side, s))
						.is(SurvivalReimaginedModBlocks.URANIUM_ROD.get())) return false;
			}
		}

		for (int s : new int[]{-1, 1}) {
			if (!level.getBlockState(origin.relative(depth, 1).relative(side, s))
					.is(SurvivalReimaginedModBlocks.BLOCK_OF_TITANIUM.get())) return false;
		}

		if (!level.getBlockState(origin.relative(depth, 2)).is(SurvivalReimaginedModBlocks.BLOCK_OF_TITANIUM.get())) return false;
		return level.getBlockState(origin.relative(depth, 1)).isAir();
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
