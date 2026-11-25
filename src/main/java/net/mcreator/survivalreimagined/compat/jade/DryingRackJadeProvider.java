package net.mcreator.survivalreimagined.compat.jade;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.block.entity.DryingRackBlockEntity;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.phys.Vec2;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.StreamServerDataProvider;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElementHelper;

public enum DryingRackJadeProvider implements IBlockComponentProvider,
        StreamServerDataProvider<BlockAccessor, DryingRackJadeProvider.Data> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        Data data = decodeFromData(accessor).orElse(null);
        if (data == null) return;
        IElementHelper helper = IElementHelper.get();

        boolean hasFirst = !data.slot0().isEmpty();
        boolean hasSecond = !data.slot1().isEmpty();
        if (hasFirst) {
            appendSlot(tooltip, helper, data.slot0(), data.progress0(), true, false);
        }
        if (hasSecond) {
            appendSlot(tooltip, helper, data.slot1(), data.progress1(), !hasFirst, hasFirst);
        }
    }

    private void appendSlot(ITooltip tooltip, IElementHelper helper, ItemStack stack, float progress,
                            boolean newRow, boolean addGap) {
        if (stack.isEmpty()) return;

        if (addGap) {
            tooltip.append(helper.spacer(20, 0));
        }

        if (newRow) {
            tooltip.add(helper.item(stack));
        } else {
            tooltip.append(helper.item(stack));
        }

        if (stack.is(SurvivalReimaginedModItems.HIDE.get())) {
            float clampedProgress = Math.max(0.0F, Math.min(1.0F, progress));
            tooltip.append(helper.spacer(4, 0));
            tooltip.append(helper.text(Component.literal(Math.round(clampedProgress * 100.0F) + "%"))
                    .translate(new Vec2(0, 4)));
        }
    }

    @Override
    public Data streamData(BlockAccessor accessor) {
        DryingRackBlockEntity rack = (DryingRackBlockEntity) accessor.getBlockEntity();
        return new Data(rack.getItem(0).copy(), rack.getItem(1).copy(),
                getProgress(rack, 0), getProgress(rack, 1));
    }

    private float getProgress(DryingRackBlockEntity rack, int slot) {
        ItemStack stack = rack.getItem(slot);
        if (stack.isEmpty() || !stack.is(SurvivalReimaginedModItems.HIDE.get())) return 0.0F;
        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        double drying = tag.getDouble("Drying");
        double ticksPerStep = 20.0D * stack.getCount();
        double partial = ticksPerStep <= 0.0D ? 0.0D : rack.getDryClock(slot) / ticksPerStep;
        return (float) Math.max(0.0D, Math.min(1.0D, (drying + partial) / 200.0D));
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, Data> streamCodec() {
        return Data.STREAM_CODEC;
    }

    @Override
    public ResourceLocation getUid() {
        return ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "drying_rack_progress");
    }

    public record Data(ItemStack slot0, ItemStack slot1, float progress0, float progress1) {
        public static final StreamCodec<RegistryFriendlyByteBuf, Data> STREAM_CODEC = StreamCodec.composite(
                ItemStack.OPTIONAL_STREAM_CODEC, Data::slot0,
                ItemStack.OPTIONAL_STREAM_CODEC, Data::slot1,
                ByteBufCodecs.FLOAT, Data::progress0,
                ByteBufCodecs.FLOAT, Data::progress1,
                Data::new);
    }
}
