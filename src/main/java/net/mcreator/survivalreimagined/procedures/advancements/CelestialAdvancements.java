package net.mcreator.survivalreimagined.procedures.advancements;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.mcreator.survivalreimagined.network.SurvivalReimaginedModVariables;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class CelestialAdvancements {
    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            for (ServerPlayer player : world.players()) {
                execute(player);
            }
        });
    }

    public static void execute(ServerPlayer entity) {
        if (entity == null) return;

        if (SurvivalReimaginedModVariables.MapVariables.get(entity.serverLevel()).isBloodMoon) {
            AdvancementHolder advancementHolder = entity.server.getAdvancements().get(ResourceLocation.parse("survival_reimagined:blood_moon_hours"));

            if (advancementHolder != null) {
                AdvancementProgress progress = entity.getAdvancements().getOrStartProgress(advancementHolder);

                if (!progress.isDone()) {
                    for (String criteria : progress.getRemainingCriteria()) {
                        entity.getAdvancements().award(advancementHolder, criteria);
                    }
                }
            }
        }
    }
}
