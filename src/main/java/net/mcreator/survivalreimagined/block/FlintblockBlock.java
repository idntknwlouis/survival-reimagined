package net.mcreator.survivalreimagined.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.mcreator.survivalreimagined.procedures.FlintblockBlockIsPlacedByProcedure;
import net.mcreator.survivalreimagined.procedures.FlintblockBlockValidPlacementConditionProcedure;

public class FlintblockBlock extends Block {
	private static final VoxelShape SHAPE = Shapes.or(box(6, 0, 6, 9, 2, 9), box(6, 2, 6, 8, 3, 8));

	public FlintblockBlock() {
		super(BlockBehaviour.Properties.of()
				.sound(SoundType.DRIPSTONE_BLOCK)
				.instabreak()
				.noOcclusion()
				.isRedstoneConductor((state, getter, pos) -> false)
				.dynamicShape()
				.offsetType(Block.OffsetType.XZ)
				.instrument(NoteBlockInstrument.BASEDRUM));
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		Vec3 offset = state.getOffset(world, pos);
		return SHAPE.move(offset.x, offset.y, offset.z);
	}

	@Override
	protected VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}

	@Override
	protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		if (level instanceof LevelAccessor world) {
			return FlintblockBlockValidPlacementConditionProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
		}
		return super.canSurvive(state, level, pos);
	}

	@Override
	protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
			LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
		return state.canSurvive(world, pos)
				? super.updateShape(state, direction, neighborState, world, pos, neighborPos)
				: Blocks.AIR.defaultBlockState();
	}

	@Override
	protected ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
		return new ItemStack(Items.FLINT);
	}

	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		super.setPlacedBy(world, pos, state, placer, stack);
		FlintblockBlockIsPlacedByProcedure.execute(placer, stack);
	}
}
