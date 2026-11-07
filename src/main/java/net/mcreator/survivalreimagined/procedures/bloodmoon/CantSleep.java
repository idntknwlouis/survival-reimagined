package net.mcreator.survivalreimagined.procedures.bloodmoon;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.mcreator.survivalreimagined.SurvivalReimaginedModClient;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModConfig;
import net.mcreator.survivalreimagined.network.SurvivalReimaginedModVariables;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class CantSleep {
    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                execute(player.level(), player.getX(), player.getY(), player.getZ(), player);
            }
        });
    }

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (!(entity instanceof ServerPlayer player)) {
            return;
        }

        if (SurvivalReimaginedModConfig.VALUES.bloodmoon_sleep) {
            return;
        }
        if (world instanceof ServerLevel serverLevel) {
            if (!SurvivalReimaginedModVariables.MapVariables.get(serverLevel).isBloodMoon) {
                return;
            }
        }

        if (player.isSleeping()) {
            ServerLevel serverLevel = (ServerLevel) player.level();

            player.hurt(serverLevel.damageSources().fellOutOfWorld(), 0.01f);
            player.displayClientMessage(Component.literal("§4The Blood Moons influence prevents you from sleeping..."), true);
            serverLevel.playSound(null, BlockPos.containing(x,y,z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.warden.heartbeat")), SoundSource.PLAYERS, 1.0f, 1.0f);
        }
    }
}
