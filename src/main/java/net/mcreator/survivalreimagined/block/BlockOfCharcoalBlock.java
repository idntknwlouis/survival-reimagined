package net.mcreator.survivalreimagined.block;


import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.server.level.ServerLevel;

import net.mcreator.survivalreimagined.block.entity.BlockOfCharcoalBlockEntity;
import net.mcreator.survivalreimagined.procedures.BlockOfCharcoalRandomClientDisplayTick;
import net.mcreator.survivalreimagined.procedures.BlockOfCharcoalTickUpdate;

public class BlockOfCharcoalBlock extends Block implements EntityBlock {
	public BlockOfCharcoalBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.TUFF).strength(1.5f, 3.0f));
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new BlockOfCharcoalBlockEntity(pos, state);
	}

	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean moving) {
		super.onPlace(state, level, pos, oldState, moving);
		if (!level.isClientSide() && state.getBlock() != oldState.getBlock()) {
			level.scheduleTick(pos, this, 20);
		}
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		BlockOfCharcoalTickUpdate.execute(level, pos.getX(), pos.getY(), pos.getZ());
		if (level.getBlockState(pos).is(this)) {
			level.scheduleTick(pos, this, 20);
		}
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		BlockOfCharcoalRandomClientDisplayTick.execute(level, pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public boolean triggerEvent(BlockState state, Level level, BlockPos pos, int eventId, int eventParam) {
		super.triggerEvent(state, level, pos, eventId, eventParam);
		BlockEntity blockEntity = level.getBlockEntity(pos);
		return blockEntity != null && blockEntity.triggerEvent(eventId, eventParam);
	}
}
