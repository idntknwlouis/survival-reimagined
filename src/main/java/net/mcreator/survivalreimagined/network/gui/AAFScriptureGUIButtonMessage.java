package net.mcreator.survivalreimagined.network.gui;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.core.SectionPos;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.procedures.gui.AAF.NextPage;
import net.mcreator.survivalreimagined.procedures.gui.AAF.PreviousPage;
import net.minecraft.world.scores.ScoreHolder;

public record AAFScriptureGUIButtonMessage(int buttonID, int x, int y, int z) implements CustomPacketPayload {
    public static final Type<AAFScriptureGUIButtonMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "aaf_scripture_gui_buttons"));
    public static final StreamCodec<RegistryFriendlyByteBuf, AAFScriptureGUIButtonMessage> STREAM_CODEC = StreamCodec.of((buf, message) -> {
        buf.writeInt(message.buttonID());
        buf.writeInt(message.x());
        buf.writeInt(message.y());
        buf.writeInt(message.z());
    },
            buf -> new AAFScriptureGUIButtonMessage(buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt()
            )
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void registerC2S() {
        PayloadTypeRegistry.playC2S().register(TYPE, STREAM_CODEC);
            ServerPlayNetworking.registerGlobalReceiver(TYPE, (message, context) -> context.server().execute(() -> {
                    handleButtonAction(context.player(), message.buttonID(), message.x(), message.y(), message.z());
            })
        );
    }

    public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
        if (!(entity instanceof ServerPlayer serverPlayer)) {
            return;
        }
        Level world = serverPlayer.level();


        if (!world.getChunkSource().hasChunk(
                SectionPos.blockToSectionCoord(x),
                SectionPos.blockToSectionCoord(z))) {
            return;
        }

        if (buttonID == 0) {
            NextPage.execute(world, x, y, z, serverPlayer);
        } else if (buttonID == 1) {
            PreviousPage.execute(world, x, y, z, serverPlayer);
        } else {
            return;
        }

        var scoreboard = serverPlayer.getScoreboard();
        var objective = scoreboard.getObjective("SR_PageNumber");

        if (objective == null) {
            return;
        }
        var holder = ScoreHolder.forNameOnly(serverPlayer.getScoreboardName());

        int page = scoreboard.getOrCreatePlayerScore(holder, objective).get();

        ServerPlayNetworking.send(serverPlayer, new AAFScripturePageSyncPayload(page));
    }
}
