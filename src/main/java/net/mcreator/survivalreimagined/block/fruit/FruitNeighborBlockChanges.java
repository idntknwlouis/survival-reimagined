package net.mcreator.survivalreimagined.block.fruit;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class FruitNeighborBlockChanges {
    public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockState) {
        BlockPos pos = BlockPos.containing(x,y,z);

        if (world.isEmptyBlock(pos.above())) {
            Property<?> ageProperty = getPropertyByName(blockState, "age");
            int currentAge = (ageProperty instanceof IntegerProperty intProp) ? blockState.getValue(intProp) : -1;
            int maxAge = (blockState.getBlock().getStateDefinition().getProperty("age") instanceof IntegerProperty intProp)
                    ? intProp.getPossibleValues().stream().max(Integer::compareTo).orElse(-1)
                    : -1;

            world.destroyBlock(pos, false);

            if (currentAge == maxAge && maxAge != -1) {
                if (blockState.is(SurvivalReimaginedModBlocks.APPLE_FRUIT.get())) {
                    if (world instanceof ServerLevel _level) {
                        ItemEntity entitySpawn = new ItemEntity(_level, (x + 0.5), (y + 0.3),(z + 0.5), new ItemStack(Items.APPLE));
                        entitySpawn.setPickUpDelay(10);
                        _level.addFreshEntity(entitySpawn);
                    }
                } else if (blockState.is(SurvivalReimaginedModBlocks.MANDARIN_FRUIT.get())) {
                    if (world instanceof ServerLevel _level) {
                        ItemEntity entitySpawn = new ItemEntity(_level, (x + 0.5), (y + 0.3),(z + 0.5), new ItemStack(SurvivalReimaginedModItems.MANDARIN.get()));
                        entitySpawn.setPickUpDelay(10);
                        _level.addFreshEntity(entitySpawn);
                    }
                } else if (blockState.is(SurvivalReimaginedModBlocks.BANANA_FRUIT.get())) {
                    if (world instanceof ServerLevel _level) {
                        ItemEntity entitySpawn = new ItemEntity(_level, (x + 0.5), (y + 0.3),(z + 0.5), new ItemStack(SurvivalReimaginedModItems.BANANA_FRUIT.get()));
                        entitySpawn.setPickUpDelay(10);
                        _level.addFreshEntity(entitySpawn);
                    }
                } else if (blockState.is(SurvivalReimaginedModBlocks.RED_CHERRIES_FRUIT.get())) {
                    if (world instanceof ServerLevel _level) {
                        ItemEntity entitySpawn = new ItemEntity(_level, (x + 0.5), (y + 0.3),(z + 0.5), new ItemStack(SurvivalReimaginedModItems.RED_CHERRIES.get()));
                        entitySpawn.setPickUpDelay(10);
                        _level.addFreshEntity(entitySpawn);
                    }
                }
            }
        }
    }
    private static Property<?> getPropertyByName(BlockState state, String name) {
        for (Property<?> property : state.getProperties()) {
            if (property.getName().equals(name)) {
                return property;
            }
        }
        return null;
    }
}
