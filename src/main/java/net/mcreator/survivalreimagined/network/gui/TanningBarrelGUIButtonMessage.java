package net.mcreator.survivalreimagined.network.gui;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.procedures.gui.tanning.SealBarrel;
import net.minecraft.core.SectionPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public record TanningBarrelGUIButtonMessage(int buttonID, int x, int y, int z) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<TanningBarrelGUIButtonMessage> TYPE =
            new CustomPacketPayload.Type<TanningBarrelGUIButtonMessage>(ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "tanning_barrel_gui_message"));

    public static final StreamCodec<RegistryFriendlyByteBuf, TanningBarrelGUIButtonMessage> STREAM_CODEC = StreamCodec.of(
            (RegistryFriendlyByteBuf buffer, TanningBarrelGUIButtonMessage message) -> {
                buffer.writeInt(message.buttonID);
                buffer.writeInt(message.x);
                buffer.writeInt(message.y);
                buffer.writeInt(message.z);
            },
            (RegistryFriendlyByteBuf buffer) -> new TanningBarrelGUIButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt())
    );

    @Override
    public CustomPacketPayload.Type<TanningBarrelGUIButtonMessage> type() {
        return TYPE;
    }

    public static void registerC2S() {
        PayloadTypeRegistry.playC2S().register(TYPE, STREAM_CODEC);

        ServerPlayNetworking.registerGlobalReceiver(TYPE, (payload, context) -> {
            context.server().execute(() -> {
                handleButtonAction(context.player(), payload.buttonID(), payload.x(), payload.y(), payload.z());
            });
        });
    }

    public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
        Level world = entity.level();
        if (!world.getChunkSource().hasChunk(SectionPos.blockToSectionCoord(x), SectionPos.blockToSectionCoord(z))) return;
        if (buttonID == 0) {
            SealBarrel.execute(world, x, y, z);
        }
    }
}
