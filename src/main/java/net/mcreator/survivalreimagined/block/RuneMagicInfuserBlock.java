package net.mcreator.survivalreimagined.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.mcreator.survivalreimagined.block.entity.RuneMagicInfuserBlockEntity;

public class RuneMagicInfuserBlock extends Block implements EntityBlock {
	private static final VoxelShape SHAPE = box(0, 0, 0, 16, 12, 16);

	public RuneMagicInfuserBlock() {
		super(BlockBehaviour.Properties.of().strength(5f, 1200f).requiresCorrectToolForDrops().noOcclusion()
				.isRedstoneConductor((state, level, pos) -> false));
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
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
		if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer
				&& level.getBlockEntity(pos) instanceof RuneMagicInfuserBlockEntity infuser) {
			serverPlayer.openMenu(infuser);
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
		BlockEntity blockEntity = level.getBlockEntity(pos);
		return blockEntity instanceof MenuProvider provider ? provider : null;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new RuneMagicInfuserBlockEntity(pos, state);
	}

	@Override
	protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moving) {
		if (state.getBlock() != newState.getBlock()) {
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if (blockEntity instanceof RuneMagicInfuserBlockEntity infuser) {
				Containers.dropContents(level, pos, infuser);
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
		return level.getBlockEntity(pos) instanceof RuneMagicInfuserBlockEntity infuser
				? AbstractContainerMenu.getRedstoneSignalFromContainer(infuser) : 0;
	}
}
