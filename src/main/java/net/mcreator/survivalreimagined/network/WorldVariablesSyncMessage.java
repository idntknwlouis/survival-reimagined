package net.mcreator.survivalreimagined.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record WorldVariablesSyncMessage(WorldVariables data) implements CustomPacketPayload {
    public static final Type<WorldVariablesSyncMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("survival_reimagined", "world_variable_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, WorldVariablesSyncMessage> STREAM_CODEC = StreamCodec.of(
            (buf, msg) -> {
                CompoundTag nbt = new CompoundTag();
                msg.data.write(nbt);
                buf.writeNbt(nbt);
            },
            buf -> {
                WorldVariables vars = new WorldVariables();
                vars.read(buf.readNbt());
                return new WorldVariablesSyncMessage(vars);
            }
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
    public static void handleData(WorldVariablesSyncMessage message, ServerPlayNetworking.Context context) {

    }
}
