package net.mcreator.survivalreimagined.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.mcreator.survivalreimagined.util.RegistryEntry;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;


public class VineBodyBlock extends GrowingPlantBodyBlock {
    public static final VoxelShape SHAPE = Block.box(4.0, 2.0,4.0, 12.0, 16.0, 12.0);
    private final Supplier<RegistryEntry<Block>> headBlockSupplier;

    @Override
    protected MapCodec<? extends GrowingPlantBodyBlock> codec() {
        return RecordCodecBuilder.mapCodec(instance -> instance.point(this));
    }

    public VineBodyBlock(Supplier<RegistryEntry<Block>> headBlockSupplier) {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.PLANT)
                .randomTicks()
                .noCollission()
                .instabreak()
                .sound(SoundType.CAVE_VINES)
                .pushReaction(PushReaction.DESTROY),
                Direction.DOWN, SHAPE, false);
        this.headBlockSupplier = headBlockSupplier;
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) this.headBlockSupplier.get().get();
    }
}
