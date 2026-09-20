package net.mcreator.survivalreimagined.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ShaleRockBlock extends Block {
	private static final VoxelShape SHAPE = box(6, 0, 5, 10, 2, 11);

	public ShaleRockBlock() {
		super(BlockBehaviour.Properties.of()
				.sound(SoundType.STONE)
				.instabreak()
				.noOcclusion()
				.isRedstoneConductor((state, getter, pos) -> false)
				.dynamicShape()
				.offsetType(Block.OffsetType.XZ));
	}

	@Override
	protected boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return true;
	}

	@Override
	protected int getLightBlock(BlockState state, BlockGetter world, BlockPos pos) {
		return 0;
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		Vec3 offset = state.getOffset(world, pos);
		return SHAPE.move(offset.x, offset.y, offset.z);
	}
}
