package net.mcreator.survivalreimagined.procedures;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.GameType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HungerMessages {
    private static final Map<UUID, Integer> HUNGER_TICK_DELAY = new HashMap<>();

    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register(level -> {
            for (ServerPlayer serverPlayer : level.players()) {
                execute(serverPlayer);

                ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
                    HUNGER_TICK_DELAY.remove(handler.player.getUUID());
                });
            }
        });
    }

    public static void execute(ServerPlayer player) {
        if (player == null) return;

        if (player.gameMode.getGameModeForPlayer() != GameType.SURVIVAL) return;

        int foodLevel = player.getFoodData().getFoodLevel();

        if (foodLevel <= 6 && foodLevel >= 5) {
            player.displayClientMessage(Component.literal("Peskish"), true);
        } else if (foodLevel <= 4 && foodLevel >= 3) {
            player.displayClientMessage(Component.literal("Famished"), true);
        } else if (foodLevel <= 2 && foodLevel >= 1) {
            player.displayClientMessage(Component.literal("Starving"), true);

            if (SurvivalReimaginedModConfig.VALUES.hunger_debuffs) {
                if (player.getAttributes().hasAttribute(Attributes.MOVEMENT_SPEED)) {
                    player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.05);
                }
            }
        } else if (foodLevel == 0) {
            player.displayClientMessage(Component.literal("Dying"), true);
        } else if (!SurvivalReimaginedModConfig.VALUES.vanilla_hunger_effect && player.hasEffect(MobEffects.HUNGER)) {
            player.displayClientMessage(Component.literal("Food Poisoned"), true);

            int delay = HUNGER_TICK_DELAY.getOrDefault(player.getUUID(), 0);

            if (delay == 19) {
                if (player.getFoodData().getSaturationLevel() > 0) {
                    if (Math.random() < 0.1) {
                        player.getFoodData().setSaturation(player.getFoodData().getSaturationLevel() - 1);
                    }
                } else if (foodLevel > 0) {
                    if (Math.random() < 0.1) {
                        player.getFoodData().setFoodLevel(foodLevel - 1);
                    }
                }
            }

            if (foodLevel > 2) {
                if (player.getAttributes().hasAttribute(Attributes.MOVEMENT_SPEED)) {
                    player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.1);
                }
            }

            delay++;
            if (delay >= 20) delay = 0;
            HUNGER_TICK_DELAY.put(player.getUUID(), delay);
        }
    }
}
