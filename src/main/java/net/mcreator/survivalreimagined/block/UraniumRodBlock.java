package net.mcreator.survivalreimagined.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class UraniumRodBlock extends Block {
	private static final VoxelShape SHAPE = Shapes.or(
			box(4, 0, 4, 12, 1, 12),
			box(4, 15, 4, 12, 16, 12),
			box(5, 1, 5, 11, 15, 11)
	);

	public UraniumRodBlock() {
		super(BlockBehaviour.Properties.of()
				.strength(3.0F)
				.lightLevel(state -> 10)
				.requiresCorrectToolForDrops()
				.noOcclusion()
				.isRedstoneConductor((state, level, pos) -> false));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}
}
