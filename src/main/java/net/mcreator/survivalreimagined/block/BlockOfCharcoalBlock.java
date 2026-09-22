package net.mcreator.survivalreimagined.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;

import net.mcreator.survivalreimagined.block.entity.BlockOfCharcoalBlockEntity;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModParticleTypes;

import java.util.ArrayList;
import java.util.List;

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
		if (!level.isClientSide()) level.scheduleTick(pos, this, 20);
	}

	@Override
	protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		super.tick(state, level, pos, random);
		if (level.getBlockEntity(pos) instanceof BlockOfCharcoalBlockEntity charcoal) {
			if (level.getBlockState(pos.above()).is(SurvivalReimaginedModBlocks.CAMPFIRE.get())) {
				charcoal.setCanBurn(true);
			}

			if (isEnclosed(level, pos) && charcoal.canBurn()) {
				List<BlockPos> logs = neighboringLogs(level, pos);
				if (logs.isEmpty()) {
					charcoal.setCanBurn(false);
					charcoal.resetClock();
				} else if (charcoal.incrementClock() >= 500) {
					for (BlockPos logPos : logs) {
						level.setBlock(logPos, SurvivalReimaginedModBlocks.BLOCK_OF_CHARCOAL.get().defaultBlockState(), 3);
						if (level.getBlockEntity(logPos) instanceof BlockOfCharcoalBlockEntity next) {
							next.setCanBurn(true);
						}
					}
					charcoal.setCanBurn(false);
					charcoal.resetClock();
				}
			}
		}
		level.scheduleTick(pos, this, 20);
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		super.animateTick(state, level, pos, random);
		if (level.getBlockEntity(pos) instanceof BlockOfCharcoalBlockEntity charcoal && charcoal.canBurn()) {
			level.addParticle(SurvivalReimaginedModParticleTypes.BLACK_SMOKE.get(),
					pos.getX() + random.nextDouble(), pos.getY(), pos.getZ() + random.nextDouble(),
					0.0D, 0.1D, 0.0D);
		}
	}

	private static boolean isEnclosed(Level level, BlockPos pos) {
		for (Direction direction : Direction.values()) {
			BlockPos check = pos.relative(direction);
			if (direction == Direction.UP && level.getBlockState(check).is(SurvivalReimaginedModBlocks.CAMPFIRE.get())) continue;
			if (!level.getBlockState(check).canOcclude()) return false;
		}
		return true;
	}

	private static List<BlockPos> neighboringLogs(Level level, BlockPos pos) {
		List<BlockPos> logs = new ArrayList<>();
		for (Direction direction : Direction.values()) {
			BlockPos check = pos.relative(direction);
			if (level.getBlockState(check).is(BlockTags.LOGS) && level.getBlockState(check).canOcclude()) {
				logs.add(check);
			}
		}
		return logs;
	}
}
