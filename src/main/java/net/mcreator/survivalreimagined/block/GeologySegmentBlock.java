package net.mcreator.survivalreimagined.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GeologySegmentBlock extends Block {
	public enum Segment {
		BASE, MIDDLE, TIP
	}

	private final Segment segment;

	public GeologySegmentBlock(SoundType sound, Segment segment) {
		super(BlockBehaviour.Properties.of()
				.sound(sound)
				.strength(1.25f, 4.2f)
				.requiresCorrectToolForDrops()
				.noOcclusion()
				.isRedstoneConductor((state, level, pos) -> false));
		this.segment = segment;
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
}
