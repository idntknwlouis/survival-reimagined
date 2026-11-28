package net.mcreator.survivalreimagined.compat.jade;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.block.CrudeTorch;
import net.mcreator.survivalreimagined.block.entity.CrudeTorchBlockEntity;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.StreamServerDataProvider;
import snownee.jade.api.config.IPluginConfig;

/** Shows whether a crude torch is lit and how long it has left to burn. */
public enum CrudeTorchJadeProvider implements IBlockComponentProvider,
        StreamServerDataProvider<BlockAccessor, CrudeTorchJadeProvider.Data> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        boolean lit = accessor.getBlockState().getValue(CrudeTorch.LIT);
        tooltip.add(Component.translatable(lit
                ? "tooltip.survival_reimagined.jade.crude_torch.lit"
                : "tooltip.survival_reimagined.jade.crude_torch.unlit"));

        if (!lit) {
            return;
        }

        Data data = decodeFromData(accessor).orElse(null);
        if (data == null) {
            return;
        }

        int remainingSeconds = Math.max(0, data.remainingSeconds());
        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;
        tooltip.add(Component.translatable(
                "tooltip.survival_reimagined.jade.crude_torch.remaining",
                minutes,
                String.format(java.util.Locale.ROOT, "%02d", seconds)));
    }

    @Override
    public Data streamData(BlockAccessor accessor) {
        CrudeTorchBlockEntity torch = (CrudeTorchBlockEntity) accessor.getBlockEntity();
        // The torch's clock advances once every 20 game ticks, so one clock
        // unit corresponds to one second of remaining burn time.
        int remainingSeconds = Math.max(0, torch.getBurnTarget() - torch.getBurnClock());
        return new Data(remainingSeconds);
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, Data> streamCodec() {
        return Data.STREAM_CODEC;
    }

    @Override
    public ResourceLocation getUid() {
        return ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "crude_torch");
    }

    public record Data(int remainingSeconds) {
        public static final StreamCodec<RegistryFriendlyByteBuf, Data> STREAM_CODEC =
                StreamCodec.composite(ByteBufCodecs.VAR_INT, Data::remainingSeconds, Data::new);
    }
}
