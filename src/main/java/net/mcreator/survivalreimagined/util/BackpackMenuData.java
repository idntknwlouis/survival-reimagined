package net.mcreator.survivalreimagined.util;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

public record BackpackMenuData(BlockPos pos, ItemStack stack) {
    public static final StreamCodec<RegistryFriendlyByteBuf, BackpackMenuData> STREAM_CODEC = StreamCodec.of((buf, data) -> {
                BlockPos.STREAM_CODEC.encode(buf, data.pos());
                ItemStack.STREAM_CODEC.encode(buf, data.stack());
            },
            buf -> new BackpackMenuData(
                    BlockPos.STREAM_CODEC.decode(buf),
                    ItemStack.STREAM_CODEC.decode(buf)
            )
    );
}