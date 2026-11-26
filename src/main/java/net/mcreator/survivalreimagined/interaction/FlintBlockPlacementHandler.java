package net.mcreator.survivalreimagined.interaction;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Handles placing the custom Flint Block by using a Flint item.
 */
public final class FlintBlockPlacementHandler {
    private FlintBlockPlacementHandler() {
    }

    public static void register() {
        UseBlockCallback.EVENT.register((player, level, hand, hit) -> {
            ItemStack stack = player.getItemInHand(hand);
            if (!stack.is(Items.FLINT)) {
                return InteractionResult.PASS;
            }

            BlockPos clicked = hit.getBlockPos();
            BlockPos placePos = level.getBlockState(clicked).canBeReplaced()
                    ? clicked
                    : clicked.relative(hit.getDirection());
            BlockState placed = SurvivalReimaginedModBlocks.FLINTBLOCK.get().defaultBlockState();

            if (!level.getBlockState(placePos).canBeReplaced() || !placed.canSurvive(level, placePos)) {
                return InteractionResult.PASS;
            }

            if (!level.isClientSide()) {
                level.setBlock(placePos, placed, 11);
                level.playSound(null, placePos, SoundEvents.DRIPSTONE_BLOCK_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
            }
            return InteractionResult.SUCCESS;
        });
    }
}
