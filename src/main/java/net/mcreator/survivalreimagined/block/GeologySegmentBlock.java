package net.mcreator.survivalreimagined.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;

public class GeologySegmentBlock extends Block {
	public enum Segment {
		BASE, MIDDLE, TIP
	}

	private final Segment segment;
	private final String family;
	private final boolean growsUp;

	public GeologySegmentBlock(SoundType sound, Segment segment, String family, boolean growsUp) {
		super(BlockBehaviour.Properties.of()
				.sound(sound)
				.strength(1.25f, 4.2f)
				.requiresCorrectToolForDrops()
				.noOcclusion()
				.isRedstoneConductor((state, level, pos) -> false));
		this.segment = segment;
		this.family = family;
		this.growsUp = growsUp;
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
		return switch (segment) {
			case BASE -> box(1, 0, 1, 15, 16, 15);
			case MIDDLE -> box(3, 0, 3, 13, 16, 13);
			case TIP -> box(5, 0, 5, 11, 16, 11);
		};
	}

	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos fromPos, boolean moving) {
		super.neighborChanged(state, level, pos, neighborBlock, fromPos, moving);
		resolveSegment(level, pos);
		BlockPos supportSide = growsUp ? pos.below() : pos.above();
		if (level.getBlockState(supportSide).getBlock() instanceof GeologySegmentBlock) {
			resolveSegment(level, supportSide);
		}
	}

	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean moving) {
		super.onPlace(state, level, pos, oldState, moving);
		if (!level.isClientSide()) {
			resolveSegment(level, pos);
			BlockPos supportSide = growsUp ? pos.below() : pos.above();
			if (level.getBlockState(supportSide).getBlock() instanceof GeologySegmentBlock) {
				resolveSegment(level, supportSide);
			}
		}
	}

	private void resolveSegment(Level level, BlockPos pos) {
		BlockState current = level.getBlockState(pos);
		if (!(current.getBlock() instanceof GeologySegmentBlock currentSegment)
				|| !currentSegment.family.equals(this.family)
				|| currentSegment.growsUp != this.growsUp) {
			return;
		}

		BlockPos towardTip = growsUp ? pos.above() : pos.below();
		BlockState next = level.getBlockState(towardTip);

		Block base = block(family + "_base");
		Block middle = block(family + "_middle");
		Block tip = block(family + (growsUp ? "_top" : "_tip"));

		BlockState replacement = current;
		if (next.is(tip)) {
			replacement = middle.defaultBlockState();
		} else if (next.is(middle)) {
			replacement = base.defaultBlockState();
		} else if (next.isAir()) {
			replacement = tip.defaultBlockState();
		}

		if (replacement.getBlock() != current.getBlock()) {
			level.setBlock(pos, replacement, 2);
		}
	}

	private static Block block(String path) {
		return BuiltInRegistries.BLOCK.get(SurvivalReimaginedMod.asResource(path));
	}
}
