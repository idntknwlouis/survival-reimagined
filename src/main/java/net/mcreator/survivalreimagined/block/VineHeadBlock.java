package net.mcreator.survivalreimagined.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.mcreator.survivalreimagined.util.RegistryEntry;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

public class VineHeadBlock extends GrowingPlantHeadBlock {
    public static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
    private final Supplier<RegistryEntry<Block>> bodyBlockSupplier;

    @Override
    protected MapCodec<? extends GrowingPlantHeadBlock> codec() {
        return RecordCodecBuilder.mapCodec(instance -> instance.point(this));
    }


    public VineHeadBlock(Supplier<RegistryEntry<Block>> bodyBlockSupplier) {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.PLANT)
                .randomTicks()
                .noCollission()
                .instabreak()
                .sound(SoundType.CAVE_VINES)
                .pushReaction(PushReaction.DESTROY),
                Direction.DOWN, SHAPE, false, 0.1);
        this.bodyBlockSupplier = bodyBlockSupplier;
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource  random) {
        return NetherVines.getBlocksToGrowWhenBonemealed(random);
    }

    @Override
    protected Block getBodyBlock() {
        return this.bodyBlockSupplier.get().get();
    }

    @Override
    protected boolean canGrowInto(BlockState state) {
        return NetherVines.isValidGrowthState(state);
    }
}
