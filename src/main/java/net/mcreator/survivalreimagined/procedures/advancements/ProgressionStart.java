package net.mcreator.survivalreimagined.procedures.advancements;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class ProgressionStart {
    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            execute(handler.player);
        });
    }

    public static void execute(ServerPlayer player) {
        if (player == null) return;

        AdvancementHolder advancement = player.server.getAdvancements().get(ResourceLocation.parse("survival_reimagined:survival_reimagined"));

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
