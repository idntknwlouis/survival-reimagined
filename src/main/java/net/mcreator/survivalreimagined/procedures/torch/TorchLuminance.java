package net.mcreator.survivalreimagined.procedures.torch;

import net.mcreator.survivalreimagined.block.CrudeTorch;
import net.minecraft.world.level.block.state.BlockState;

public class TorchLuminance {
    public static double execute(BlockState state) {
        return state.getValue(CrudeTorch.LIT) ? 14 : 0;
    }
}
