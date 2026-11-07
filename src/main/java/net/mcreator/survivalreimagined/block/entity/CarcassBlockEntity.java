package net.mcreator.survivalreimagined.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlockEntities;

public class CarcassBlockEntity extends BlockEntity {
	private int processingProgress;

	public CarcassBlockEntity(BlockPos pos, BlockState state) {
		super(SurvivalReimaginedModBlockEntities.CARCASS.get(), pos, state);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		processingProgress = tag.getInt("ProcessingProgress");
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		tag.putInt("ProcessingProgress", processingProgress);
	}

	public int incrementProgress() {
		processingProgress++;
		setChanged();
		return processingProgress;
	}

	public void resetProgress() {
		processingProgress = 0;
		setChanged();
	}
}
