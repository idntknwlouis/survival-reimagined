package net.mcreator.survivalreimagined.compat.jade;

import java.util.List;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
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
 * Shows the Metal Refining Table's ingredients, required hammer, and result preview.
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

        ItemStack first = data.inventory().get(0);
        ItemStack second = data.inventory().get(1);
        ItemStack hammer = data.inventory().get(2);
        ItemStack result = data.inventory().get(3);
        IElementHelper helper = IElementHelper.get();

        if (!first.isEmpty() || !second.isEmpty() || !result.isEmpty()) {

            if (!first.isEmpty()) {
                tooltip.add(helper.item(first));
            }
            if (!second.isEmpty()) {
                if (!first.isEmpty()) {
                    tooltip.append(helper.spacer(4, 5));
                    tooltip.append(helper.text(Component.literal("+")));
                    tooltip.append(helper.spacer(4, 0));
                    tooltip.append(helper.item(second));
                } else {
                    tooltip.add(helper.item(second));
                }
            }
            if (!result.isEmpty()) {
                if (!first.isEmpty() || !second.isEmpty()) {
                    tooltip.append(helper.spacer(8, 0));
                    tooltip.append(helper.text(Component.literal("→")));
                    tooltip.append(helper.spacer(8, 0));
                }
                tooltip.append(helper.item(result));
            }
        }

        if (!hammer.isEmpty()) {
            tooltip.add(Component.translatable("tooltip.survival_reimagined.jade.metal_refining_table.hammer"));
            tooltip.add(helper.item(hammer));
            if (hammer.isDamageableItem()) {
                int remainingDurability = hammer.getMaxDamage() - hammer.getDamageValue();
                int maxDurability = hammer.getMaxDamage();
                tooltip.append(helper.spacer(4, 0));
                tooltip.append(helper.text(Component.literal(remainingDurability + " / " + maxDurability)));
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
