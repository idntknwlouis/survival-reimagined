package net.mcreator.survivalreimagined.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record MapVariablesSyncMessage(MapVariables data) implements CustomPacketPayload {
    public static final Type<MapVariablesSyncMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("survival_reimagined", "map_variable_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, MapVariablesSyncMessage> STREAM_CODEC = StreamCodec.of(
            (buf, msg) -> {
                CompoundTag nbt = new CompoundTag();
                msg.data.write(nbt, buf.registryAccess());
                buf.writeNbt(nbt);
            },
            buf -> {
               MapVariables vars = new MapVariables();
                vars.read(buf.readNbt(), buf.registryAccess());
                return new MapVariablesSyncMessage(vars);
            }
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
    public static void handleData(MapVariablesSyncMessage message, ServerPlayNetworking.Context context) {

    }
}
