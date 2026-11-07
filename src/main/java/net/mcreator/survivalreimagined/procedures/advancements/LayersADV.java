package net.mcreator.survivalreimagined.procedures.advancements;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class LayersADV {
    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register((world -> {
            for (ServerPlayer player : world.players()) {
                execute(player);
            }
        }));
    }

    public static void execute(Entity entity) {
        if (entity == null) return;
        if (entity instanceof Player) {
            if (entity.getY() <= -96) {
                if (entity instanceof ServerPlayer _player) {
                    AdvancementHolder advancement = _player.server.getAdvancements().get(ResourceLocation.parse("survival_reimagined:volcanic_layer"));
                    if (advancement != null) {
                        AdvancementProgress progress = _player.getAdvancements().getOrStartProgress(advancement);
                        if (!progress.isDone()) {
                            for (String criteria : progress.getRemainingCriteria())
                                _player.getAdvancements().award(advancement, criteria);
                        }
                    }
                }
            }
            if (entity.getY() <= -192) {
                if (entity instanceof ServerPlayer _player) {
                    AdvancementHolder advancement = _player.server.getAdvancements().get(ResourceLocation.parse("survival_reimagined:kimberlite_layer"));
                    if (advancement != null) {
                        AdvancementProgress progress = _player.getAdvancements().getOrStartProgress(advancement);
                        if (!progress.isDone()) {
                            for (String criteria : progress.getRemainingCriteria())
                                _player.getAdvancements().award(advancement, criteria);
                        }
                    }
                }
            }
        }
    }
}
