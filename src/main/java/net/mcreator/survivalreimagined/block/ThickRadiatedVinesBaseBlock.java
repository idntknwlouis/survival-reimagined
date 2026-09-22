package net.mcreator.survivalreimagined.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;

public class ThickRadiatedVinesBaseBlock extends Block {
    private static final VoxelShape SHAPE = box(3, 0, 3, 13, 16, 13);

    public ThickRadiatedVinesBaseBlock() {
        super(BlockBehaviour.Properties.of().sound(SoundType.MOSS).strength(1f).noCollission()
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
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState above = level.getBlockState(pos.above());
        return above.canOcclude()
                || above.is(SurvivalReimaginedModBlocks.THICK_RADIATED_VINES.get())
                || above.is(SurvivalReimaginedModBlocks.THICK_RADIATED_VINES_BASE.get());
    }

    @Override
    public BlockState updateShape(BlockState state, net.minecraft.core.Direction direction, BlockState neighborState,
            LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return canSurvive(state, level, pos) ? state : Blocks.AIR.defaultBlockState();
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos fromPos, boolean moving) {
        super.neighborChanged(state, level, pos, neighborBlock, fromPos, moving);
        if (level.isEmptyBlock(pos.below())) {
            level.setBlock(pos, SurvivalReimaginedModBlocks.THICK_RADIATED_VINES.get().defaultBlockState(), 3);
        }
    }
}
