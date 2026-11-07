package net.mcreator.survivalreimagined.procedures.loot_tables;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;

public class PointedStonesLoot {
    private static final TagKey<Block> STONE = TagKey.create(Registries.BLOCK, ResourceLocation.parse("c:pointed_stones/stone"));
    private static final TagKey<Block> DEEPSLATE = TagKey.create(Registries.BLOCK, ResourceLocation.parse("c:pointed_stones/deepslate"));
    private static final TagKey<Block> BASALT = TagKey.create(Registries.BLOCK, ResourceLocation.parse("c:pointed_stones/basalt"));
    private static final TagKey<Block> KIMBERLITE = TagKey.create(Registries.BLOCK, ResourceLocation.parse("c:pointed_stones/kimberlite"));
    private static final TagKey<Block> SHALE = TagKey.create(Registries.BLOCK, ResourceLocation.parse("c:pointed_stones/shale"));

    public static void register() {
        PlayerBlockBreakEvents.AFTER.register((world, player, blockPos, blockState, blockEntity) -> {
            if (!(world instanceof ServerLevel level)) return;
            if (player instanceof ServerPlayer serverPlayer && serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE) return;

            ItemStack drop = ItemStack.EMPTY;

            if (blockState.is(STONE)) {
                drop = new ItemStack(SurvivalReimaginedModItems.STONE_ROCK.get());
            }
            else if (blockState.is(DEEPSLATE)) {
                drop = new ItemStack(SurvivalReimaginedModItems.DEEPSLATE_ROCK.get());
            }
            if (blockState.is(BASALT)) {
                drop = new ItemStack(SurvivalReimaginedModItems.BASALT_ROCK.get());
            }
            if (blockState.is(KIMBERLITE)) {
                drop = new ItemStack(SurvivalReimaginedModItems.KIMBERLITE_ROCK.get());
            }
            if (blockState.is(SHALE)) {
                drop = new ItemStack(SurvivalReimaginedModItems.SHALE_ROCK.get());
            }

            if (!drop.isEmpty()) {
                Block.popResource(level, blockPos, drop);
            }
        });
    }
}
