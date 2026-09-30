package net.mcreator.survivalreimagined.block.entity;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class BananaLeavesBlockEntity extends BlockEntity {
    public BananaLeavesBlockEntity(BlockPos pos, BlockState state) {
        this(SurvivalReimaginedModBlockEntities.BANANA_LEAVES::get, pos, state);
    }
    private BananaLeavesBlockEntity(Supplier<?> typeSupplier, BlockPos pos, BlockState state) {
        super((BlockEntityType<?>) typeSupplier.get(), pos, state);
    }
}
