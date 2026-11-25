package net.mcreator.survivalreimagined.compat.jade;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.block.entity.CampfireBlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.StreamServerDataProvider;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElementHelper;

/** Displays campfire fuel and the cooking progress of each occupied cooking slot. */
public enum CampfireJadeProvider implements IBlockComponentProvider,
        StreamServerDataProvider<BlockAccessor, CampfireJadeProvider.Data> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        Data data = decodeFromData(accessor).orElse(null);
        if (data == null) return;

        IElementHelper helper = IElementHelper.get();
        if (!data.fuel().isEmpty()) {
            tooltip.add(Component.translatable("tooltip.survival_reimagined.jade.campfire.fuel"));
            tooltip.append(helper.item(data.fuel()));
        }

        boolean hasFood = false;
        for (ItemStack stack : data.food()) {
            if (stack.isEmpty()) continue;
            if (!hasFood) {
                tooltip.add(Component.translatable("tooltip.survival_reimagined.jade.campfire.cooking"));
                hasFood = true;
            }

            tooltip.add(helper.item(stack));
            CompoundTag customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
            int stageProgress = Math.max(0, Math.min(30, (int) customData.getDouble("PercentageNumber")));
            tooltip.append(helper.spacer(4, 0));
            tooltip.append(helper.text(Component.literal(Math.round(stageProgress / 30.0F * 100.0F) + "%")));
        }
    }

    @Override
    public Data streamData(BlockAccessor accessor) {
        CampfireBlockEntity campfire = (CampfireBlockEntity) accessor.getBlockEntity();
        List<ItemStack> food = new ArrayList<>(4);
        for (int slot = 1; slot < CampfireBlockEntity.CONTAINER_SIZE; slot++) {
            food.add(campfire.getItem(slot).copy());
        }
        return new Data(campfire.getItem(0).copy(), food);
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, Data> streamCodec() {
        return Data.STREAM_CODEC;
    }

    @Override
    public ResourceLocation getUid() {
        return ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "campfire_progress");
    }

    public record Data(ItemStack fuel, List<ItemStack> food) {
        public static final StreamCodec<RegistryFriendlyByteBuf, Data> STREAM_CODEC = StreamCodec.composite(
                ItemStack.OPTIONAL_STREAM_CODEC, Data::fuel,
                ItemStack.OPTIONAL_LIST_STREAM_CODEC, Data::food,
                Data::new);
    }
}
