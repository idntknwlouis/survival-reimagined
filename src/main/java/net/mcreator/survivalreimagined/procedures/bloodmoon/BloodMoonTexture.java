package net.mcreator.survivalreimagined.procedures.bloodmoon;


import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.mcreator.survivalreimagined.network.SurvivalReimaginedModVariables;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.LevelAccessor;

public class BloodMoonTexture {
   private static boolean clientIsBloodMoon = false;
   private static boolean lastServerState = false;

   public record BloodMoonPayload(boolean isBloodMoon) implements CustomPacketPayload {
       public static final Type<BloodMoonPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("survival_reimagined", "blood_moon_sync"));
       public static final StreamCodec<RegistryFriendlyByteBuf, BloodMoonPayload> CODEC = StreamCodec.of(
               (buf, payload) -> buf.writeBoolean(payload.isBloodMoon()),
               buf -> new BloodMoonPayload(buf.readBoolean())
       );
       @Override
       public Type<? extends CustomPacketPayload> type() {
           return TYPE;
       }
   }

   public static void initPackets() {
       PayloadTypeRegistry.playS2C().register(BloodMoonPayload.TYPE, BloodMoonPayload.CODEC);
   }

   public static void registerServerTicker() {
       ServerTickEvents.END_SERVER_TICK.register(server -> {
           var overworld = server.overworld();
           if (overworld != null) {
               boolean currentServerState = SurvivalReimaginedModVariables.MapVariables.get(overworld).isBloodMoon;

               if (currentServerState != lastServerState) {
                   lastServerState = currentServerState;
                   for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                       ServerPlayNetworking.send(player, new BloodMoonPayload(currentServerState));
                   }
               }
           }
       });
   }
   public static void registerClientReceiver() {
       ClientPlayNetworking.registerGlobalReceiver(BloodMoonPayload.TYPE, (payload, context) -> {
           context.client().execute(() -> {
               clientIsBloodMoon = payload.isBloodMoon();
           });
       });

       ClientTickEvents.END_CLIENT_TICK.register(client -> {
           if(client.level != null && client.player != null) {
               Minecraft minecraft = Minecraft.getInstance();
               ResourceLocation vanillaMoonTex = ResourceLocation.fromNamespaceAndPath("minecraft", "textures/environment/moon_phases.png");

               if (clientIsBloodMoon) {
                   ResourceLocation bloodMoonTex = ResourceLocation.fromNamespaceAndPath("survival_reimagined", "textures/environment/blood_moon.png");
                   minecraft.getTextureManager().bindForSetup(bloodMoonTex);
                   minecraft.getTextureManager().register(vanillaMoonTex, minecraft.getTextureManager().getTexture(bloodMoonTex));
               } else {
                   minecraft.getTextureManager().release(vanillaMoonTex);
               }
           }
       });
   }
}
