package net.mcreator.survivalreimagined.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlockEntities;

public class BlockOfCharcoalBlockEntity extends BlockEntity {
	private boolean canBurn;
	private int charcoalClock;

	public BlockOfCharcoalBlockEntity(BlockPos pos, BlockState state) {
		super(SurvivalReimaginedModBlockEntities.BLOCK_OF_CHARCOAL.get(), pos, state);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		this.canBurn = tag.getBoolean("CanBurn");
		this.charcoalClock = tag.getInt("CharcoalClock");
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		tag.putBoolean("CanBurn", this.canBurn);
		tag.putInt("CharcoalClock", this.charcoalClock);
	}

	public boolean canBurn() {
		return this.canBurn;
	}

	public void setCanBurn(boolean value) {
		if (this.canBurn != value) {
			this.canBurn = value;
			setChanged();
		}
	}

	public int incrementClock() {
		this.charcoalClock++;
		setChanged();
		return this.charcoalClock;
	}

	public void resetClock() {
		if (this.charcoalClock != 0) {
			this.charcoalClock = 0;
			setChanged();
		}
	}
}
