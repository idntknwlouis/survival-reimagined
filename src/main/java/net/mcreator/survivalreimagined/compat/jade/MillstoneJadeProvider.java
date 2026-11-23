package net.mcreator.survivalreimagined.compat.jade;

import java.util.List;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.mcreator.survivalreimagined.block.entity.MillstoneBlockEntity;
import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.StreamServerDataProvider;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElementHelper;

/**
 * Displays the Millstone's input, output, and milling progress in Jade.
 */
public enum MillstoneJadeProvider implements IBlockComponentProvider,
        StreamServerDataProvider<BlockAccessor, MillstoneJadeProvider.Data> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        Data data = decodeFromData(accessor).orElse(null);
        if (data == null || (data.input().isEmpty() && data.output().isEmpty())) {
            return;
        }

        IElementHelper helper = IElementHelper.get();
        tooltip.add(helper.item(data.input()));
        tooltip.append(helper.spacer(4, 0));
        tooltip.append(helper.progress(data.total() <= 0 ? 0F : (float) data.progress() / data.total())
                .translate(new Vec2(-2, 0)));
        tooltip.append(helper.item(data.output()));
    }

    @Override
    public Data streamData(BlockAccessor accessor) {
        MillstoneBlockEntity millstone = (MillstoneBlockEntity) accessor.getBlockEntity();
        return new Data(millstone.getItem(MillstoneBlockEntity.INPUT_SLOT),
                millstone.getItem(MillstoneBlockEntity.OUTPUT_SLOT),
                millstone.getProgress(), MillstoneBlockEntity.MAX_PROGRESS);
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, Data> streamCodec() {
        return Data.STREAM_CODEC;
    }

    @Override
    public ResourceLocation getUid() {
        return ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "millstone_progress");
    }

    public record Data(ItemStack input, ItemStack output, int progress, int total) {
        public static final StreamCodec<RegistryFriendlyByteBuf, Data> STREAM_CODEC = StreamCodec.composite(
                ItemStack.OPTIONAL_STREAM_CODEC, Data::input,
                ItemStack.OPTIONAL_STREAM_CODEC, Data::output,
                ByteBufCodecs.VAR_INT, Data::progress,
                ByteBufCodecs.VAR_INT, Data::total,
                Data::new);
    }
}
