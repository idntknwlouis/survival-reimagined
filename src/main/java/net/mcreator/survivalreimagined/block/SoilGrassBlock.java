package net.mcreator.survivalreimagined.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class SoilGrassBlock extends GrassBlock {
    private final Block dirt;

    public SoilGrassBlock(BlockBehaviour.Properties properties, Block dirt) {
        super(properties.randomTicks());
        this.dirt = dirt;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int lightAbove = level.getMaxLocalRawBrightness(pos.above());

        if (lightAbove < 4) {
            level.setBlockAndUpdate(pos, dirt.defaultBlockState());
            return;
        }

        if (lightAbove < 9) {
            return;
        }

        for (int i = 0; i < 4; i++) {
            BlockPos target = pos.offset(
                    random.nextInt(3) - 1,
                    random.nextInt(5) - 3,
                    random.nextInt(3) - 1
            );
            BlockState targetState = level.getBlockState(target);
            int targetLight = level.getMaxLocalRawBrightness(target.above());

            if (targetState.is(dirt) && targetLight >= 4) {
                level.setBlockAndUpdate(target, defaultBlockState());
            }
        }
    }
}
