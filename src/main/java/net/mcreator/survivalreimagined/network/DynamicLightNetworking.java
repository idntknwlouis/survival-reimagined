package net.mcreator.survivalreimagined.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class DynamicLightNetworking {
    private static final Map<UUID, Boolean> PLAYER_LIGHT_STATE = new ConcurrentHashMap<>();

    public static void register() {
        PayloadTypeRegistry.playC2S().register(DynamicLightStatePayload.TYPE, DynamicLightStatePayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(DynamicLightStatePayload.TYPE, (payload, context) -> {
            PLAYER_LIGHT_STATE.put(context.player().getUUID(), payload.enabled());
        });

        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            PLAYER_LIGHT_STATE.remove(handler.player.getUUID());
        });
    }

    public static boolean hasDynamicLightExemption(ServerPlayer player) {
        return PLAYER_LIGHT_STATE.getOrDefault(player.getUUID(), false);
    }
}
