package net.mcreator.survivalreimagined.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.saveddata.SavedData;

public record SavedDataSyncMessage(int dataType, CompoundTag tag) implements CustomPacketPayload {
    public static final Type<SavedDataSyncMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("survival_reimagined", "saved_data_sync"));

    public SavedDataSyncMessage(int dataType, SavedData savedData) {
        this(dataType, savedData.save(new CompoundTag(), null));
    }
    public static final StreamCodec<RegistryFriendlyByteBuf, SavedDataSyncMessage> STREAM_CODEC = StreamCodec.of(
            (buf, msg) -> {
                buf.writeInt(msg.dataType);
                buf.writeNbt(msg.tag);
            },
            buf -> new SavedDataSyncMessage(buf.readInt(), buf.readNbt())
    );

    @Override
    public CustomPacketPayload.Type<SavedDataSyncMessage> type() {
        return TYPE;
    }
    public static void handleData(SavedDataSyncMessage message, ServerPlayNetworking.Context context) {

    }
}
