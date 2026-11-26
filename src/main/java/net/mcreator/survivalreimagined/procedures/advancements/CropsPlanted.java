package net.mcreator.survivalreimagined.procedures.advancements;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CropsPlanted {
    private static final Map<UUID, Integer> CROPS_PLANTED = new HashMap<>();
    private static final Map<UUID, BlockPos> PENDING_PLACEMENT = new HashMap<>();

    public static void register() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (world.isClientSide() || !(player instanceof ServerPlayer)) return InteractionResult.PASS;

            BlockPos clickedPos = hitResult.getBlockPos();
            BlockPos cropPos = clickedPos.above();

            PENDING_PLACEMENT.put(player.getUUID(), cropPos.immutable());
            return InteractionResult.PASS;
        });

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                BlockPos pos = PENDING_PLACEMENT.remove(player.getUUID());

                if (pos == null) continue;
                if (player.level().getBlockState(pos).is(BlockTags.CROPS)) {
                    execute(player, pos);
                };
            }
        });
    }

    public static void execute(ServerPlayer player, BlockPos pos) {

        int crops = CROPS_PLANTED.getOrDefault(player.getUUID(), 0) + 1;
        CROPS_PLANTED.put(player.getUUID(), crops);
        awardAdvancement(player, "survival_reimagined:humbe_beginnings");
        if (crops >= 100) awardAdvancement(player, "survival_reimagined:professional_farmer");
        if (crops >= 500) awardAdvancement(player, "survival_reimagined:seasoned_farmer");
        if (crops >= 1000) awardAdvancement(player, "survival_reimagined:strongest_farmer_of_history");
    }

    private static void awardAdvancement(ServerPlayer player, String ID) {
        AdvancementHolder advancement = player.server.getAdvancements().get(ResourceLocation.parse(ID));
        if (advancement != null) {
            AdvancementProgress progress = player.getAdvancements().getOrStartProgress(advancement);
            if (!progress.isDone()) {
                for (String criteria : progress.getRemainingCriteria()) {
                    player.getAdvancements().award(advancement, criteria);
                }
            }
        }
    }
}
