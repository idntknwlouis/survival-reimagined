package net.mcreator.survivalreimagined.network;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.item.Items;

public class DynamicLightClientHandler {
    private static Boolean lastSentState = null;

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) {
                lastSentState = null;
                return;
            }

            boolean dynamicLightsLoaded = FabricLoader.getInstance().isModLoaded("lambdynlights");
            boolean holdingLightSource =
                    client.player.getMainHandItem().is(Items.TORCH)
                    || client.player.getMainHandItem().is(Items.SOUL_TORCH)
                    || client.player.getMainHandItem().is(Items.LANTERN)
                    || client.player.getMainHandItem().is(Items.SOUL_LANTERN)
                    || client.player.getOffhandItem().is(Items.TORCH)
                    || client.player.getOffhandItem().is(Items.SOUL_TORCH)
                    || client.player.getOffhandItem().is(Items.LANTERN)
                    || client.player.getOffhandItem().is(Items.SOUL_LANTERN);

            boolean exemption = dynamicLightsLoaded && holdingLightSource;

            if (lastSentState == null || lastSentState != exemption) {
                if (ClientPlayNetworking.canSend(DynamicLightStatePayload.TYPE)) {
                    ClientPlayNetworking.send(new DynamicLightStatePayload(exemption));
                    lastSentState = exemption;
                }
            }
        });
    }
}
