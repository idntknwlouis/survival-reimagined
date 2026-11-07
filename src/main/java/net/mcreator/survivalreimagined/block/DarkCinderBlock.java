package net.mcreator.survivalreimagined.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mcreator.survivalreimagined.procedures.DarkCinderClientDisplayTick;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class DarkCinderBlock extends Block {
    public DarkCinderBlock() {
        super(BlockBehaviour.Properties.of().sound(SoundType.SAND).strength(0.3f));
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        super.animateTick(state, world, pos, random);
        DarkCinderClientDisplayTick.execute(world, pos.getX(), pos.getY(), pos.getZ());
    }
}
