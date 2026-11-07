package net.mcreator.survivalreimagined.block.fruit;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class FruitDropCondition {
    public static boolean execute(Level world, BlockPos pos, BlockState state, Supplier<? extends ItemLike> itemSupplier, int matureAge) {
        if (!state.hasProperty(FruitBlock.AGE)) {
            return false;
        }

        int currentAge = state.getValue(FruitBlock.AGE);

        if (currentAge == matureAge) {
            if (world instanceof ServerLevel serverLevel) {
                double spawnX = pos.getX() + 0.5;
                double spawnY = pos.getY() + 0.3;
                double spawnZ = pos.getZ() + 0.5;
                ItemLike resolvedItem = itemSupplier.get();

                ItemEntity entityToSpawn = new ItemEntity(serverLevel, spawnX, spawnY, spawnZ, new ItemStack(resolvedItem));
                entityToSpawn.setPickUpDelay(10);
                serverLevel.addFreshEntity(entityToSpawn);
            }
            return true;
        }
        return false;
    }
}
