package net.mcreator.survivalreimagined.compat.jade;

import java.util.List;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.block.entity.RuneMagicInfuserBlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.StreamServerDataProvider;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElementHelper;

/**
 * Displays the Rune Magic Infuser's target item, rune, and lapis inputs.
 */
public enum RuneMagicInfuserJadeProvider implements IBlockComponentProvider,
        StreamServerDataProvider<BlockAccessor, RuneMagicInfuserJadeProvider.Data> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        Data data = decodeFromData(accessor).orElse(null);
        if (data == null || data.inventory().stream().allMatch(ItemStack::isEmpty)) {
            return;
        }

        tooltip.add(Component.translatable("tooltip.survival_reimagined.jade.rune_magic_infuser.inputs"));
        IElementHelper helper = IElementHelper.get();

        ItemStack target = data.inventory().get(0);
        ItemStack rune = data.inventory().get(1);
        ItemStack lapis = data.inventory().get(2);

        if (!target.isEmpty()) {
            tooltip.add(helper.item(target));
        }
        if (!rune.isEmpty()) {
            tooltip.append(helper.spacer(4, 0));
            tooltip.append(helper.item(rune));
        }
        if (!lapis.isEmpty()) {
            tooltip.append(helper.spacer(4, 0));
            tooltip.append(helper.item(lapis));
        }
    }

    @Override
    public Data streamData(BlockAccessor accessor) {
        RuneMagicInfuserBlockEntity infuser = (RuneMagicInfuserBlockEntity) accessor.getBlockEntity();
        return new Data(List.of(
                infuser.getItem(0).copy(),
                infuser.getItem(1).copy(),
                infuser.getItem(2).copy()
        ));
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, Data> streamCodec() {
        return Data.STREAM_CODEC;
    }

    @Override
    public ResourceLocation getUid() {
        return ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "rune_magic_infuser");
    }

    public record Data(List<ItemStack> inventory) {
        public static final StreamCodec<RegistryFriendlyByteBuf, Data> STREAM_CODEC = StreamCodec.composite(
                ItemStack.OPTIONAL_LIST_STREAM_CODEC, Data::inventory,
                Data::new
        );
    }
}
