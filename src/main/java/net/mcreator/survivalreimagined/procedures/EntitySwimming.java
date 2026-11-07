package net.mcreator.survivalreimagined.procedures;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EntitySwimming {
    private static final Map<UUID, Integer> SWIMMING_TICKS = new HashMap<>();

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                execute(player);
            }
            SWIMMING_TICKS.keySet().removeIf(uuid -> server.getPlayerList().getPlayer(uuid) == null);
        });
    }


    public static void execute(Entity entity) {
        if  (entity == null) return;
        if (!(entity instanceof Player player)) return;
        if (!(entity instanceof ServerPlayer serverPlayer)) return;

        UUID uuid = entity.getUUID();

        if (!SurvivalReimaginedModConfig.VALUES.vanilla_hunger) {
            if (getEntityGameType(serverPlayer) == GameType.SURVIVAL) {
                boolean swimming = entity.isSwimming() || (!(entity.getVehicle() instanceof Boat)
                && entity.level().getBlockState(BlockPos.containing(entity.getX(), entity.getY() - 0.1, entity.getZ())).getBlock() == Blocks.AIR);

                if (swimming) {
                    int ticks = SWIMMING_TICKS.getOrDefault(uuid, 0) + 1;
                    SWIMMING_TICKS.put(uuid, ticks);

                    if (ticks == 75) {
                        if (player.getFoodData().getSaturationLevel() > 0) {
                            player.getFoodData().setSaturation(player.getFoodData().getSaturationLevel() - 1);
                        } else {
                            player.getFoodData().setFoodLevel(player.getFoodData().getFoodLevel() - 1);
                        }
                        SWIMMING_TICKS.put(uuid, 0);
                    }
                } else {
                    SWIMMING_TICKS.put(uuid, 0);
                }
            }
        }
    }

    private static GameType getEntityGameType(ServerPlayer player) {
        return player.gameMode.getGameModeForPlayer();
    }
}
