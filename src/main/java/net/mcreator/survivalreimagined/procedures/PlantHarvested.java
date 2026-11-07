package net.mcreator.survivalreimagined.procedures;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class PlantHarvested {
    public static void register() {
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            if (!(world instanceof ServerLevel serverLevel) || !(player instanceof ServerPlayer serverPlayer) || serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE) return;

            int age = getAge(state);
            int maxAge = getMaxAge(state);

            if (state.is(SurvivalReimaginedModBlocks.WILD_WHEAT.get()) || state.is(SurvivalReimaginedModBlocks.WHEAT_CROP.get())) {
                if (age == 7) {
                    drop(serverLevel, pos, SurvivalReimaginedModItems.WHEAT_SEEDS.get(), random(1,3));
                    drop(serverLevel, pos, Items.WHEAT, 1);
                } else {
                    drop(serverLevel, pos, SurvivalReimaginedModItems.WHEAT_SEEDS.get(), 1);
                }
            } else if (state.is(SurvivalReimaginedModBlocks.WILD_CARROT.get()) || state.is(SurvivalReimaginedModBlocks.CARROT.get())) {
               drop(serverLevel, pos, SurvivalReimaginedModItems.CARROT.get(), age == 3 ? random(2,4) : 1);
            } else if (state.is(SurvivalReimaginedModBlocks.WILD_POTATOES.get()) || state.is(SurvivalReimaginedModBlocks.POTATOES.get())) {
                drop(serverLevel, pos, SurvivalReimaginedModItems.POTATO.get(), age == 3 ? random(2,4) : 1);
            }

            else if (state.is(TagKey.create(Registries.BLOCK, ResourceLocation.parse("survival_reimagined:corn")))) {
                int amount = age == maxAge ? random(1, 4) : 0;

                if (state.is(SurvivalReimaginedModBlocks.CORN_STALK_TOP.get())) {
                    if (amount > 0) {
                        for (int i = 0; i < amount; i++) {
                            drop(serverLevel, pos, SurvivalReimaginedModItems.CORN.get(),1);
                            drop(serverLevel, pos.below(), SurvivalReimaginedModItems.CORN.get(),1);
                            drop(serverLevel, pos.below(2), SurvivalReimaginedModItems.CORN.get(),1);
                        }
                    } else {
                        drop(serverLevel, pos, SurvivalReimaginedModItems.CORN.get(), 1);
                    }
                } else if (state.is(SurvivalReimaginedModBlocks.CORN_STALK_MIDDLE.get())) {
                    if (amount > 0) {
                        for (int i = 0; i < amount; i++) {
                            drop(serverLevel, pos, SurvivalReimaginedModItems.CORN.get(),1);
                            drop(serverLevel, pos.below(), SurvivalReimaginedModItems.CORN.get(),1);
                            drop(serverLevel, pos.above(), SurvivalReimaginedModItems.CORN.get(),1);
                        }
                    } else {
                        drop(serverLevel, pos, SurvivalReimaginedModItems.CORN.get(), 1);
                    }
                } else if (state.is(SurvivalReimaginedModBlocks.CORN_STALK_BOTTOM.get())) {
                    if (amount > 0) {
                        for (int i = 0; i < amount; i++) {
                            drop(serverLevel, pos, SurvivalReimaginedModItems.CORN.get(),1);
                            drop(serverLevel, pos.above(), SurvivalReimaginedModItems.CORN.get(),1);
                            drop(serverLevel, pos.above(2), SurvivalReimaginedModItems.CORN.get(),1);
                        }
                    } else {
                        drop(serverLevel, pos, SurvivalReimaginedModItems.CORN.get(), 1);
                    }
                }
            }
        });
    }

    private static int getAge(BlockState state) {
        var property = state.getBlock().getStateDefinition().getProperty("age");
        return  property instanceof IntegerProperty age ? state.getValue(age) : -1;
    }

    private static int getMaxAge(BlockState state) {
        var property = state.getBlock().getStateDefinition().getProperty("age");
        return property instanceof IntegerProperty age ? age.getPossibleValues().stream().max(Integer::compareTo).orElse(-1) : -1;
    }

    private static int random(int min, int max) {
        return RandomSource.create().nextInt(min, max + 1);
    }

    private static void drop(ServerLevel level, BlockPos pos, Item item, int count) {
        ItemEntity entity = new ItemEntity(level, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, new ItemStack(item, count));
        entity.setPickUpDelay(10);
        level.addFreshEntity(entity);
    }
}
