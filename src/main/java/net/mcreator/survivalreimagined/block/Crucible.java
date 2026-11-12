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

public class Crucible extends Block {
    private static final VoxelShape SHAPE = Shapes.or(
            box(4, 0, 4, 12, 1, 12),
            box(3, 3, 3, 13, 9, 4), box(3, 3, 12, 13, 9, 13),
            box(3, 3, 4, 4, 9, 12), box(12, 3, 4, 13, 9, 12),
            box(11, 1, 4, 12, 3, 12), box(5, 1, 11, 11, 3, 12),
            box(4, 1, 4, 5, 3, 12), box(5, 1, 4, 11, 3, 5)
    );


    public Crucible() {
        super(BlockBehaviour.Properties.of()
                .sound(SoundType.DECORATED_POT)
                .instabreak()
                .noOcclusion()
                .isRedstoneConductor((state, level, pos) -> false));
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
        return SHAPE;
    }
}
