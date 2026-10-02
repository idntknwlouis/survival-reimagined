package net.mcreator.survivalreimagined.block.fruit;


import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;

public class FruitValidPlacement {
    public static boolean execute(LevelAccessor world, double x, double y, double z) {
        if ((world.getBlockState(BlockPos.containing(x,y + 1 ,z))).is(BlockTags.LEAVES)) {
            return true;
        }
        return false;
    }
}
