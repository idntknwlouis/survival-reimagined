package net.mcreator.survivalreimagined.block;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class WisteriaLeavesBlock extends LeavesBlock {
    public WisteriaLeavesBlock() {
        super(BlockBehaviour.Properties.of()
                .sound(SoundType.CHERRY_LEAVES)
                .strength(0.2f)
                .noOcclusion()
                .isSuffocating((state, level, pos) -> false)
                .isViewBlocking((state, level, pos) -> false));
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);

        if (!level.isEmptyBlock(pos.below())) {
            return;
        }

        if (random.nextFloat() < 0.3f) {
            level.addParticle(
                    SurvivalReimaginedModParticleTypes.FALLING_WISTERIA.get(),
                    pos.getX() + random.nextDouble(),
                    pos.getY(),
                    pos.getZ() + random.nextDouble(),
                    0.0, 0.0, 0.0);
        }

        if (random.nextFloat() < 0.3f) {
            level.addParticle(
                    SurvivalReimaginedModParticleTypes.FALLING_WISTERIA_DARK.get(),
                    pos.getX() + random.nextDouble(),
                    pos.getY(),
                    pos.getZ() + random.nextDouble(),
                    0.0, 0.0, 0.0);
        }
    }
}
