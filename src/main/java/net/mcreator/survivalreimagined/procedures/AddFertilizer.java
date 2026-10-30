package net.mcreator.survivalreimagined.procedures;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.mcreator.survivalreimagined.block.fertilesoil.FertilitySoilTickUpdate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AddFertilizer {
    public static void register() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (hand != InteractionHand.MAIN_HAND) {
                return InteractionResult.PASS;
            }
            BlockPos pos = hitResult.getBlockPos();
            boolean success = execute(world, pos.getX(), pos.getY(), pos.getZ(), player);

            return success ? InteractionResult.SUCCESS : InteractionResult.PASS;
        });
    }

    public static boolean execute(Level world, double x, double y, double z, Player entity) {
        if (entity == null) {
            return false;
        }

        BlockPos currentPos =  BlockPos.containing(x, y, z);
        BlockState state = world.getBlockState(currentPos);

        TagKey<Block> soilTag = TagKey.create(Registries.BLOCK, ResourceLocation.parse("c:soil"));
        if (!state.is(soilTag)) {
            return false;
        }

        ItemStack mainHandItem = entity.getMainHandItem();
        String targetNutrient = null;

        TagKey<Item> nitrogenTag = TagKey.create(Registries.ITEM, ResourceLocation.parse("c:fertilizers/nitrogen"));
        TagKey<Item> phosphorusTag = TagKey.create(Registries.ITEM, ResourceLocation.parse("c:fertilizers/phosphorus"));
        TagKey<Item> potassiumTag = TagKey.create(Registries.ITEM, ResourceLocation.parse("c:fertilizers/potassium"));

        if (mainHandItem.is(nitrogenTag)) {
            targetNutrient = "N";
        } else if (mainHandItem.is(phosphorusTag)) {
            targetNutrient = "P";
        } else if (mainHandItem.is(potassiumTag)) {
            targetNutrient = "K";
        }

        if (targetNutrient != null) {
            if (!world.isClientSide()) {
                BlockEntity blockentity = world.getBlockEntity(currentPos);
                if (blockentity instanceof FertilitySoilTickUpdate.CustomDataHolder holder) {
                    double currentValue = holder.getPersistentData().getDouble(targetNutrient);
                    holder.getPersistentData().putDouble(targetNutrient, currentValue + 20);
                    blockentity.setChanged();
                }
                world.sendBlockUpdated(currentPos, state, state, 3);
            }

            if (!entity.getAbilities().instabuild) {
                mainHandItem.shrink(1);
            }
            entity.swing(InteractionHand.MAIN_HAND, true);
            world.playSound(null, currentPos, SoundEvents.CROP_PLANTED, SoundSource.BLOCKS, 1.0f, 1.0f);

            if (entity instanceof ServerPlayer player) {
                var advancementHolder = player.server.getAdvancements().get(ResourceLocation.parse("survival_reimagined:fertilized_soil"));
                if (advancementHolder != null) {
                    var progress = player.getAdvancements().getOrStartProgress(advancementHolder);
                    if (!progress.isDone()) {
                        for (String criterion : progress.getRemainingCriteria()) {
                            player.getAdvancements().award(advancementHolder, criterion);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
