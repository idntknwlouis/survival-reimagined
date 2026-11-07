package net.mcreator.survivalreimagined.procedures;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class EntityJumped {
    private static final Set<UUID> GROUNDED_PLAYERS = new HashSet<>();

    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            for (ServerPlayer player : world.players()) {
                UUID uuid = player.getUUID();
                if (player.onGround()) {
                    GROUNDED_PLAYERS.add(uuid);
                } else if (GROUNDED_PLAYERS.remove(uuid) && player.getDeltaMovement().y > 0) {
                    execute(player);
                }
            }
        });
    }


    public static void execute(Entity entity) {
        if (entity == null) return;

        if (!(entity instanceof Player player)) return;
        if (player.level().isClientSide()) return;

        if (!SurvivalReimaginedModConfig.VALUES.vanilla_hunger) {
            if (getEntityGameType(entity) == GameType.SURVIVAL) {
                if ((entity instanceof Player player1 ? player1.getFoodData().getFoodLevel() : 0) > 4) {
                    if (Math.random() < 0.175) {
                        SurvivalReimaginedMod.queueServerWork(1, () -> {
                            if ((entity instanceof Player player1 ? player1.getFoodData().getExhaustionLevel() : 0) > 0) {
                                if (entity instanceof Player player1)
                                    player1.getFoodData().setSaturation((entity instanceof Player player2 ? player2.getFoodData().getSaturationLevel() : 0) - 1);
                            } else {
                                if (entity instanceof Player player1)
                                    player1.getFoodData().setFoodLevel((entity instanceof Player player2 ? player2.getFoodData().getFoodLevel() : 0) - 1);
                            }
                        });
                    }
                } else {
                    if (SurvivalReimaginedModConfig.VALUES.hunger_debuffs) {
                        entity.setDeltaMovement(new Vec3((entity.getDeltaMovement().x()), 0, (entity.getDeltaMovement().z())));
                    }
                }
            }
        }
    }

    private static GameType getEntityGameType(Entity entity) {
        if (entity instanceof ServerPlayer serverPlayer) {
            return serverPlayer.gameMode.getGameModeForPlayer();
        } else if (entity instanceof Player player && player.level().isClientSide()) {
            PlayerInfo playerInfo = Minecraft.getInstance().getConnection().getPlayerInfo(player.getGameProfile().getId());
            if (playerInfo != null) return playerInfo.getGameMode();
        }
        return null;
    }
}
