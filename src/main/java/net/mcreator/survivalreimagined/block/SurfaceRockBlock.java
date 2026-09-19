package net.mcreator.survivalreimagined.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

public class SurfaceRockBlock extends Block {
	private static final VoxelShape SHAPE = box(6, 0, 5, 10, 2, 11);
	private final Supplier<? extends Item> droppedItem;

	public SurfaceRockBlock(Supplier<? extends Item> droppedItem) {
		super(BlockBehaviour.Properties.of()
				.instabreak()
				.noOcclusion()
				.isRedstoneConductor((state, getter, pos) -> false)
				.dynamicShape()
				.offsetType(Block.OffsetType.XZ));
		this.droppedItem = droppedItem;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		Vec3 offset = state.getOffset(world, pos);
		return SHAPE.move(offset.x, offset.y, offset.z);
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}

	@Override
	public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
		return new ItemStack(droppedItem.get());
	}

	@Override
	public void neighborChanged(BlockState state, Level world, BlockPos pos, Block neighborBlock, BlockPos fromPos, boolean moving) {
		super.neighborChanged(state, world, pos, neighborBlock, fromPos, moving);
		if (!world.isClientSide() && world.isEmptyBlock(pos.below())) {
			world.destroyBlock(pos, false);
			ItemEntity drop = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(droppedItem.get()));
			drop.setPickUpDelay(10);
			world.addFreshEntity(drop);
		}
	}
}
