package net.mcreator.survivalreimagined.procedures;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

public class FlintScrape {
    public static void register() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (hand != InteractionHand.MAIN_HAND) return InteractionResult.PASS;

            InteractionResult result = execute(world, hitResult.getBlockPos(), player, hand);
            return result;
        });
    }

    public static InteractionResult execute(Level world, BlockPos pos, Player player, InteractionHand hand) {
        if (player == null) return InteractionResult.PASS;
        if (!world.getBlockState(pos).is(TagKey.create(Registries.BLOCK, ResourceLocation.parse("c:stones")))) return InteractionResult.PASS;

        ItemStack stack = player.getItemInHand(hand);

        if (stack.getItem() != Items.FLINT) return InteractionResult.PASS;

        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> {
            tag.putDouble("ScrapeHandler", tag.getDouble("ScrapeHandler") + 1);
        });

        double scrapeCount = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("ScrapeHandler");

        if (scrapeCount == 7) {
            CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> {
                tag.putDouble("ScrapeHandler", 0);
            });

            if (!world.isClientSide()) {
                ItemStack flintTool = new ItemStack(SurvivalReimaginedModItems.FLINT_TOOL.get());

                player.getInventory().clearOrCountMatchingItems(item -> item.is(Items.FLINT), 1, player.inventoryMenu.getCraftSlots());

                if (!player.getInventory().add(flintTool)) {
                    player.drop(flintTool, false);
                }
            }
        }
        var sound = BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("survival_reimagined:flint_scrape"));

        if(!world.isClientSide()) {
            world.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0f, 1.0f);
        } else {
            world.playLocalSound(pos, sound, SoundSource.BLOCKS, 1.0f, 1.0f, false);
        }
        player.swing(hand, true);
        return InteractionResult.SUCCESS;
    }
}
