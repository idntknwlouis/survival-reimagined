package net.mcreator.survivalreimagined.procedures;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class GemOreXP {
    private static final TagKey<Block> ORE_DROP_XP = TagKey.create(Registries.BLOCK, ResourceLocation.parse("survival_reimagined:ore_drops_xp"));
    private static final Random RANDOM = new Random();

    public static void register() {
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            if (player instanceof ServerPlayer serverPlayer) {
                execute(serverPlayer, pos, state);
            }
        });
    }
    public static void execute(ServerPlayer player, BlockPos pos, BlockState state) {
        if (player == null) return;
        if (player.gameMode.getGameModeForPlayer() != GameType.SURVIVAL) return;
        if (!state.is(ORE_DROP_XP)) return;
        if (!(player.level() instanceof ServerLevel level)) return;

        int amount = RANDOM.nextInt(1, 4);

        for (int i = 0; i < amount; i++) {
            ExperienceOrb orb = new ExperienceOrb(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,2);
            level.addFreshEntity(orb);
        }
    }
}
