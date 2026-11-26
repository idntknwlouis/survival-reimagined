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

/** Displays campfire fuel and cooking progress in a compact two-column grid. */
public enum CampfireJadeProvider implements IBlockComponentProvider,
        StreamServerDataProvider<BlockAccessor, CampfireJadeProvider.Data> {
    INSTANCE;

    private static final int COLUMN_GAP = 20;
    private static final int CELL_WIDTH = 40;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        Data data = decodeFromData(accessor).orElse(null);
        if (data == null) return;

        IElementHelper helper = IElementHelper.get();

        ItemStack slot1 = data.food().size() > 0 ? data.food().get(0) : ItemStack.EMPTY;
        ItemStack slot2 = data.food().size() > 1 ? data.food().get(1) : ItemStack.EMPTY;
        ItemStack slot3 = data.food().size() > 2 ? data.food().get(2) : ItemStack.EMPTY;
        ItemStack slot4 = data.food().size() > 3 ? data.food().get(3) : ItemStack.EMPTY;

        if (!slot1.isEmpty() || !slot2.isEmpty() || !slot3.isEmpty() || !slot4.isEmpty()) {
            tooltip.add(Component.translatable("tooltip.survival_reimagined.jade.campfire.cooking"));
            appendCookingRow(tooltip, helper, slot1, slot2);
            appendCookingRow(tooltip, helper, slot3, slot4);
        }

        // Keep the fuel indicator below the cooking grid to match the campfire GUI.
        if (!data.fuel().isEmpty()) {
            tooltip.add(Component.translatable("tooltip.survival_reimagined.jade.campfire.fuel"));
            tooltip.append(helper.item(data.fuel()));
        }
    }

    private void appendCookingRow(ITooltip tooltip, IElementHelper helper, ItemStack first, ItemStack second) {
        if (first.isEmpty() && second.isEmpty()) return;

        if (!first.isEmpty()) {
            tooltip.add(helper.item(first));
            appendPercentage(tooltip, helper, first);
        } else {
            // Keep slot 2/4 in the second column when slot 1/3 is empty.
            tooltip.add(helper.spacer(CELL_WIDTH + COLUMN_GAP, 0));
        }

        if (!second.isEmpty()) {
            if (!first.isEmpty()) {
                tooltip.append(helper.spacer(COLUMN_GAP, 0));
            }
            tooltip.append(helper.item(second));
            appendPercentage(tooltip, helper, second);
        }
    }

    private void appendPercentage(ITooltip tooltip, IElementHelper helper, ItemStack stack) {
        CompoundTag customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        int stageProgress = Math.max(0, Math.min(30, (int) customData.getDouble("PercentageNumber")));
        int percentage = Math.round(stageProgress / 30.0F * 100.0F);
        tooltip.append(helper.spacer(4, 0));
        tooltip.append(helper.text(Component.literal(percentage + "%")));
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
