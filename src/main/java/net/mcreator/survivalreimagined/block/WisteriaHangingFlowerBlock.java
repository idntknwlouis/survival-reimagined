package net.mcreator.survivalreimagined.block;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WisteriaHangingFlowerBlock extends Block {
    private static final VoxelShape SHAPE = Block.box(3, 0, 3, 13, 16, 13);
    private final boolean upper;

    public WisteriaHangingFlowerBlock(boolean upper) {
        super(BlockBehaviour.Properties.of()
                .sound(SoundType.CAVE_VINES)
                .instabreak()
                .noCollission()
                .noOcclusion());
        this.upper = upper;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState above = level.getBlockState(pos.above());
        if (above.is(SurvivalReimaginedModBlocks.FLOWING_WISTERIA_LEAVES.get())
                || above.is(SurvivalReimaginedModBlocks.WISTERIA_LEAVES.get())
                || above.is(SurvivalReimaginedModBlocks.WISTERIA_LOG.get())) {
            return true;
        }
        if (upper) {
            return above.is(SurvivalReimaginedModBlocks.WISTERIA_FLOWER_UPPER.get());
        }
        return above.is(SurvivalReimaginedModBlocks.WISTERIA_FLOWER_UPPER.get())
                || above.is(SurvivalReimaginedModBlocks.WISTERIA_FLOWER_LOWER.get());
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
            LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return canSurvive(state, level, pos) ? state : Blocks.AIR.defaultBlockState();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
