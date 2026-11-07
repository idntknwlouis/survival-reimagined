package net.mcreator.survivalreimagined.procedures;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class StrippedLogToCraftingTable {
    private static final TagKey<Item> CHISELS = TagKey.create(Registries.ITEM, ResourceLocation.parse("c:tools/chisel"));
    private static final TagKey<Block> CRAFTING_TABLE_BLOCKS = TagKey.create(Registries.BLOCK, ResourceLocation.parse("c:turns_into_crafting_table"));

    public static void register() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (hand != InteractionHand.MAIN_HAND) return InteractionResult.PASS;
            if (world.isClientSide()) return InteractionResult.PASS;
            if (!(world instanceof ServerLevel serverLevel)) return InteractionResult.PASS;
            if (!world.getBlockState(hitResult.getBlockPos()).is(CRAFTING_TABLE_BLOCKS)) return InteractionResult.PASS;

            ItemStack stack = player.getMainHandItem();
            if (!stack.is(CHISELS)) return InteractionResult.PASS;

            BlockPos pos = hitResult.getBlockPos();

            if (Math.random() < 0.1) {
                world.setBlock(pos, Blocks.CRAFTING_TABLE.defaultBlockState(), 3);

                if (player instanceof ServerPlayer serverPlayer) {
                    AdvancementHolder advancement = serverPlayer.getServer().getAdvancements().get(ResourceLocation.parse("survival_reimagined:crafting_table"));
                    if (advancement != null) {
                        AdvancementProgress progress = serverPlayer.getAdvancements().getOrStartProgress(advancement);
                        if (!progress.isDone()) {
                            for (String criteria : progress.getRemainingCriteria()) {
                                serverPlayer.getAdvancements().award(advancement, criteria);
                            }
                        }
                    }
                }
                world.playSound(null, pos, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("survival_reimagined:block.carving_crafting_table")), SoundSource.BLOCKS, 1.0f, 1.0f);
                Item chisel = stack.getItem();
                if (chisel == SurvivalReimaginedModItems.COPPER_CHISEL.asItem()) {
                    damageChisel(stack, stack.getMaxDamage(), serverLevel, player);
                    world.playSound(null, pos, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.item.break")), SoundSource.PLAYERS, 1.0f, 1.0f);
                }
                else if (chisel == SurvivalReimaginedModItems.BRONZE_CHISEL.asItem()) {
                    damageChisel(stack, stack.getMaxDamage() / 2, serverLevel, player);
                }
                else if (chisel == SurvivalReimaginedModItems.STEEL_CHISEL.asItem()) {
                    damageChisel(stack, stack.getMaxDamage() / 3, serverLevel, player);
                }
                else if (chisel == SurvivalReimaginedModItems.DIAMOND_CHISEL.asItem()) {
                    damageChisel(stack, stack.getMaxDamage() / 4, serverLevel, player);
                }
            }
            player.swing(InteractionHand.MAIN_HAND, true);
            damageChisel(stack, 1, serverLevel, player);
            return InteractionResult.SUCCESS;
        });
    }

    private static void damageChisel(ItemStack stack, int amount, ServerLevel level, Player player) {
        if (amount <= 0 || stack.isEmpty()) return;

        ServerPlayer serverPlayer = player instanceof ServerPlayer sp ? sp : null;
        stack.hurtAndBreak(amount, level, serverPlayer, item -> {});
    }
}
