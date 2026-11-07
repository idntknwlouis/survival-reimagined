package net.mcreator.survivalreimagined.network.gui;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record AAFScripturePageSyncPayload(int page) implements CustomPacketPayload {
    public static final Type<AAFScripturePageSyncPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(
                    SurvivalReimaginedMod.MODID,
                    "aaf_scripture_page_sync"
            ));
    public static final StreamCodec<RegistryFriendlyByteBuf, AAFScripturePageSyncPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, paylaod) -> buf.writeInt(paylaod.page()),
                    buf -> new AAFScripturePageSyncPayload(buf.readInt())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
