package net.mcreator.survivalreimagined.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlockEntities;

public class PalmLeavesBlockEntity extends BlockEntity {
    public PalmLeavesBlockEntity(BlockPos pos, BlockState state) {
        super(SurvivalReimaginedModBlockEntities.PALM_LEAVES.get(), pos, state);
    }
}
