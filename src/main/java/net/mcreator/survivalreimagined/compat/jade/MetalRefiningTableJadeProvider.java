package net.mcreator.survivalreimagined.compat.jade;

import java.util.List;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.block.entity.MetalRefiningTableBlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.StreamServerDataProvider;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElementHelper;

/**
 * Shows the Metal Refining Table's two ingredients, hammer, and result preview.
 */
public enum MetalRefiningTableJadeProvider implements IBlockComponentProvider,
        StreamServerDataProvider<BlockAccessor, MetalRefiningTableJadeProvider.Data> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        Data data = decodeFromData(accessor).orElse(null);
        if (data == null || data.inventory().stream().allMatch(ItemStack::isEmpty)) {
            return;
        }

        IElementHelper helper = IElementHelper.get();
        for (ItemStack stack : data.inventory()) {
            if (!stack.isEmpty()) {
                tooltip.add(helper.item(stack));
            }
        }
    }

    @Override
    public Data streamData(BlockAccessor accessor) {
        MetalRefiningTableBlockEntity table = (MetalRefiningTableBlockEntity) accessor.getBlockEntity();
        return new Data(List.of(
                table.getItem(0).copy(),
                table.getItem(1).copy(),
                table.getItem(2).copy(),
                table.getItem(3).copy()
        ));
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, Data> streamCodec() {
        return Data.STREAM_CODEC;
    }

    @Override
    public ResourceLocation getUid() {
        return ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "metal_refining_table");
    }

    public record Data(List<ItemStack> inventory) {
        public static final StreamCodec<RegistryFriendlyByteBuf, Data> STREAM_CODEC = StreamCodec.composite(
                ItemStack.OPTIONAL_LIST_STREAM_CODEC, Data::inventory,
                Data::new
        );
    }
}
