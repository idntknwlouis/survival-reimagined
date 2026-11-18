package net.mcreator.survivalreimagined.procedures.torch;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;

public class TorchPlacement {
    public static boolean execute(LevelAccessor world, double x, double y, double z) {
        if ((world.getBlockState(BlockPos.containing(x,y,z))).getBlock() == SurvivalReimaginedModBlocks.CRUDE_TORCH.get()
                || (world.getBlockState(BlockPos.containing(x,y,z))).getBlock() == SurvivalReimaginedModBlocks.BURNT_TORCH.get()
            || world.getBlockState(BlockPos.containing(x,y - 1,z)).canOcclude()
            || world.getBlockState(BlockPos.containing(x + 1, y, z)).canOcclude()
            || world.getBlockState(BlockPos.containing(x - 1, y, z)).canOcclude()
            || world.getBlockState(BlockPos.containing(x, y, z + 1)).canOcclude()
            || world.getBlockState(BlockPos.containing(x, y, z - 1)).canOcclude()
        ) return true;
        return false;
    }
}
