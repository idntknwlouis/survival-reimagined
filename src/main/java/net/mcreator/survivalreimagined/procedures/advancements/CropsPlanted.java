package net.mcreator.survivalreimagined.procedures.advancements;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CropsPlanted {
    private static final Map<UUID, CompoundTag> PLAYER_DATA = new HashMap<>();
    private record PendingCrop(ServerPlayer player, BlockPos pos) {}
    private static final Map<UUID, PendingCrop> PENDING = new HashMap<>();

    public static void register() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (!(player instanceof ServerPlayer serverPlayer)) return InteractionResult.PASS;
            if (world.isClientSide()) return InteractionResult.PASS;
            BlockPos pos = hitResult.getBlockPos().relative(hitResult.getDirection());
            PENDING.put(serverPlayer.getUUID(), new PendingCrop(serverPlayer, pos));
            return InteractionResult.PASS;
        });

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (PendingCrop pending : PENDING.values()) {
                ServerPlayer player = pending.player();
                BlockPos pos = pending.pos();

                if (!player.isAlive()) continue;
                if (!player.serverLevel().getBlockState(pos).is(BlockTags.CROPS)) continue;
                execute(player);
            }
            PENDING.clear();
        });
    }

    public static void execute(ServerPlayer player) {
        if (player == null) return;
        CompoundTag data = PLAYER_DATA.computeIfAbsent(player.getUUID(), uuid -> new CompoundTag());
        double cropsPlanted = data.getDouble("CropsPlanted");

        if (cropsPlanted <= 1000) {
            cropsPlanted++;
            data.putDouble("CropsPlanted", cropsPlanted);
        }
        awardAdvancement(player, "survival_reimagined:humbe_beginnings");

        if (cropsPlanted >= 1000) awardAdvancement(player, "survival_reimagined:strongest_farmer_of_history");
        else if (cropsPlanted >= 500) awardAdvancement(player, "survival_reimagined:seasoned_farmer");
        else if (cropsPlanted >= 100) awardAdvancement(player, "survival_reimagined:professional_farmer");
    }

    private static void awardAdvancement(ServerPlayer player, String ID) {
        AdvancementHolder advancement = player.server.getAdvancements().get(ResourceLocation.parse(ID));
        if (advancement == null) return;
        AdvancementProgress progress = player.getAdvancements().getOrStartProgress(advancement);

        if (!progress.isDone()) {
            for (String criteria : progress.getRemainingCriteria()) {
                player.getAdvancements().award(advancement, criteria);
            }
        }
    }
}
