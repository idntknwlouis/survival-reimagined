package net.mcreator.survivalreimagined.block.entity;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CropGrowthBlockEntity extends BlockEntity implements GrowthClockAccess {
    private double growClock;

    public CropGrowthBlockEntity(BlockPos pos, BlockState state) {
        super(SurvivalReimaginedModBlockEntities.CROP_GROWTH.get(), pos, state);
    }

    @Override
    public double survivalReimagined$getGrowClock() {
        return this.growClock;
    }

    @Override
    public void survivalReimagined$setGrowClock(double value) {
        this.growClock = value;
        setChanged();
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.growClock = tag.getDouble("GrowClock");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putDouble("GrowClock", this.growClock);
    }
}
