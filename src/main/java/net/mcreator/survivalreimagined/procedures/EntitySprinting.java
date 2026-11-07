package net.mcreator.survivalreimagined.procedures;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModConfig;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EntitySprinting {
    private static final Map<UUID, Integer> SPRINT_TICKS = new HashMap<>();

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                execute(player);
            }
            SPRINT_TICKS.keySet().removeIf(uuid -> server.getPlayerList().getPlayer(uuid) == null);
        });
    }
    public static void execute(Entity entity) {
        if (entity == null) return;
        if (!(entity instanceof Player player)) return;
        if (!(entity instanceof ServerPlayer serverPlayer)) return;

        UUID uuid = entity.getUUID();

        if (!SurvivalReimaginedModConfig.VALUES.vanilla_hunger) {
            if (getEntityGameType(serverPlayer) == GameType.SURVIVAL) {
                if (entity.isSprinting()) {
                    int sprintTicks = SPRINT_TICKS.getOrDefault(uuid, 0);

                    if (sprintTicks == 95) {
                        if (player.getFoodData().getSaturationLevel() > 0) {
                            player.getFoodData().setSaturation(player.getFoodData().getSaturationLevel() - 1);
                        } else if (player.getFoodData().getFoodLevel() > 0) {
                            player.getFoodData().setFoodLevel(player.getFoodData().getFoodLevel() - 1);
                        }
                        sprintTicks = 0;
                    }
                    SPRINT_TICKS.put(uuid, sprintTicks + 1);
                } else {
                    SPRINT_TICKS.put(uuid, 0);
                }
            }
        }
    }

    private static GameType getEntityGameType(ServerPlayer player) {
        return player.gameMode.getGameModeForPlayer();
    }
}
