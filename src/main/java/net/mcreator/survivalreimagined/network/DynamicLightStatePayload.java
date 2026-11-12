package net.mcreator.survivalreimagined.network;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record DynamicLightStatePayload(boolean enabled) implements CustomPacketPayload {
    public static final Type<DynamicLightStatePayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "dynamic_light_state"));

    public static final StreamCodec<RegistryFriendlyByteBuf, DynamicLightStatePayload> CODEC = StreamCodec.composite(ByteBufCodecs.BOOL, DynamicLightStatePayload::enabled, DynamicLightStatePayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
