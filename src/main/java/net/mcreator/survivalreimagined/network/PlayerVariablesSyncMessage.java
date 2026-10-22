package net.mcreator.survivalreimagined.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record PlayerVariablesSyncMessage(PlayerVariables data) implements CustomPacketPayload {
    public static final Type<PlayerVariablesSyncMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("survival_reimagined", "player_variable_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, PlayerVariablesSyncMessage> STREAM_CODEC = StreamCodec.of(
            (buf, msg) -> {
                CompoundTag nbt = new CompoundTag();
                msg.data.write(nbt);
                buf.writeNbt(nbt);
            },
            buf -> {
                PlayerVariables vars = new PlayerVariables();
                vars.read(buf.readNbt());
                return new PlayerVariablesSyncMessage(vars);
            }
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
    public static void handleData(PlayerVariablesSyncMessage message, ServerPlayNetworking.Context context) {

    }
}
