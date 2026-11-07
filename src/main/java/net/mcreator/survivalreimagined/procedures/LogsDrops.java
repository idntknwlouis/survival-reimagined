package net.mcreator.survivalreimagined.procedures;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class LogsDrops {
    public static void register() {
        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) -> {
            execute(world, pos, player, state);
            return true;
        });
    }

    public static void execute(Level world, BlockPos pos, Player player, BlockState state) {
        if (!(player instanceof ServerPlayer serverPlayer)) return;
        GameType gameType = serverPlayer.gameMode.getGameModeForPlayer();
        if (gameType != GameType.SURVIVAL && gameType != GameType.ADVENTURE) return;
        if (!state.is(BlockTags.LOGS)) return;

        ItemStack heldItem = player.getMainHandItem();
        boolean hasAxe = heldItem.getItem() instanceof AxeItem;

        if (!hasAxe) {
            if (state.is(Blocks.OAK_LOG)) { drop(world, pos, SurvivalReimaginedModItems.OAK_BARK); }
            else if (state.is(Blocks.DARK_OAK_LOG)) { drop(world, pos, SurvivalReimaginedModItems.DARK_OAK_BARK); }
            else if (state.is(Blocks.BIRCH_LOG)) { drop(world, pos, SurvivalReimaginedModItems.BIRCH_BARK); }
            else if (state.is(Blocks.SPRUCE_LOG)) { drop(world, pos, SurvivalReimaginedModItems.SPRUCE_BARK); }
            else if (state.is(BlockTags.JUNGLE_LOGS)) { drop(world, pos, SurvivalReimaginedModItems.JUNGLE_BARK); }
            else if (state.is(Blocks.MANGROVE_LOG)) { drop(world, pos, SurvivalReimaginedModItems.MANGROVE_BARK); }
            else if (state.is(Blocks.ACACIA_LOG)) { drop(world, pos, SurvivalReimaginedModItems.ACACIA_BARK); }
            else if (state.is(Blocks.CHERRY_LOG)) { drop(world, pos, SurvivalReimaginedModItems.CHERRY_BARK); }
            else if (state.is(Blocks.CRIMSON_STEM)) { drop(world, pos, SurvivalReimaginedModItems.CRIMSON_BARK); }
            else if (state.is(Blocks.WARPED_STEM)) { drop(world, pos, SurvivalReimaginedModItems.WARPED_BARK); }
            else if (state.is(SurvivalReimaginedModBlocks.RADIANT_LOG.get())) { drop(world, pos, SurvivalReimaginedModItems.RADIATED_BARK); }
            else if (state.is(SurvivalReimaginedModBlocks.WISTERIA_LOG.get())) { drop(world, pos, SurvivalReimaginedModItems.WISTERIA_BARK); }
            else if (state.is(SurvivalReimaginedModBlocks.MANDARIN_LOG.get())) { drop(world, pos, SurvivalReimaginedModItems.MANDARIN_BARK); }
        } else {
            if (state.is(Blocks.OAK_LOG)) { drop(world, pos, Blocks.OAK_LOG); }
            else if (state.is(Blocks.DARK_OAK_LOG)) { drop(world, pos, Blocks.DARK_OAK_LOG); }
            else if (state.is(Blocks.BIRCH_LOG)) { drop(world, pos, Blocks.BIRCH_LOG); }
            else if (state.is(Blocks.SPRUCE_LOG)) { drop(world, pos, Blocks.SPRUCE_LOG); }
            else if (state.is(BlockTags.JUNGLE_LOGS)) {
                if (state.is(Blocks.JUNGLE_LOG)) { drop(world, pos, Blocks.JUNGLE_LOG); }
                else if (state.is(SurvivalReimaginedModBlocks.BANANA_GROW_BLOCK.get())
                        || state.is(SurvivalReimaginedModBlocks.SMALL_BANANA_JUNGLE_LOG.get())) { drop(world, pos, SurvivalReimaginedModBlocks.SMALL_BANANA_JUNGLE_LOG.get()); }
                else if (state.is(SurvivalReimaginedModBlocks.BANANA_JUNGLE_LOG.get())) { drop(world, pos, SurvivalReimaginedModBlocks.BANANA_JUNGLE_LOG.get()); }
            }
            else if (state.is(Blocks.MANGROVE_LOG)) { drop(world, pos, Blocks.MANGROVE_LOG); }
            else if (state.is(Blocks.ACACIA_LOG)) { drop(world, pos, Blocks.ACACIA_LOG); }
            else if (state.is(Blocks.CHERRY_LOG)) { drop(world, pos, Blocks.CHERRY_LOG); }
            else if (state.is(Blocks.CRIMSON_STEM)) { drop(world, pos, Blocks.CRIMSON_STEM); }
            else if (state.is(Blocks.WARPED_STEM)) { drop(world, pos, Blocks.WARPED_STEM); }
            else if (state.is(SurvivalReimaginedModBlocks.RADIANT_LOG.get())) { drop(world, pos, SurvivalReimaginedModBlocks.RADIANT_LOG.get()); }
            else if (state.is(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "palm_logs")))) {
                if (state.is(SurvivalReimaginedModBlocks.PALM_CROWN.get()) || state.is(SurvivalReimaginedModBlocks.SMALL_PALM_LOG.get())) { drop(world, pos, SurvivalReimaginedModBlocks.SMALL_PALM_LOG.get()); }
                else if (state.is(SurvivalReimaginedModBlocks.PALM_LOG_BARKED_TOP.get())) { drop(world, pos, SurvivalReimaginedModBlocks.PALM_LOG_BARKED_TOP.get()); }
                else if (state.is(SurvivalReimaginedModBlocks.PALM_LOG.get())) { drop(world, pos, SurvivalReimaginedModBlocks.PALM_LOG.get()); }
            }
            else if (state.is(SurvivalReimaginedModBlocks.MANDARIN_LOG.get())) { drop(world, pos, SurvivalReimaginedModBlocks.MANDARIN_LOG.get()); }
        }
    }

    private static void drop(Level world, BlockPos pos, ItemLike item) {
        ItemEntity entity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(item));
        entity.setPickUpDelay(10);
        world.addFreshEntity(entity);
    }
}
