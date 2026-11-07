package net.mcreator.survivalreimagined.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class SkinnedHorse extends CarcassBlock {
    public SkinnedHorse() {
        super(Species.HORSE, null, false);
        registerDefaultState(stateDefinition.any().setValue(CARCASS_STATE_HORSE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CARCASS_STATE_HORSE);
    }

    @Override
    protected boolean isSkinnedHorse() {
        return true;
    }
}
