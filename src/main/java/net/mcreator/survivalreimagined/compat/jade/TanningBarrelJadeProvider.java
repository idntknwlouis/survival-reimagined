package net.mcreator.survivalreimagined.compat.jade;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.block.entity.TanningBarrelBlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.StreamServerDataProvider;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElementHelper;

/**
 * Shows the Tanning Barrel's contents, fluid amount, current treatment, and progress.
 */
public enum TanningBarrelJadeProvider implements IBlockComponentProvider,
        StreamServerDataProvider<BlockAccessor, TanningBarrelJadeProvider.Data> {
    INSTANCE;

    private static final int IDLE = 0;
    private static final int WATER = 1;
    private static final int LIME = 2;
    private static final int TANNIN = 3;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        Data data = decodeFromData(accessor).orElse(null);
        if (data == null) {
            return;
        }

        IElementHelper helper = IElementHelper.get();
        tooltip.add(Component.translatable("tooltip.survival_reimagined.jade.tanning_barrel.fluid",
                data.fluid(), 6000));

        Component stageText = switch (data.stage()) {
            case WATER -> Component.translatable("tooltip.survival_reimagined.jade.tanning_barrel.stage.water");
            case LIME -> Component.translatable("tooltip.survival_reimagined.jade.tanning_barrel.stage.lime");
            case TANNIN -> Component.translatable("tooltip.survival_reimagined.jade.tanning_barrel.stage.tannin");
            default -> Component.translatable("tooltip.survival_reimagined.jade.tanning_barrel.stage.idle");
        };
        tooltip.add(stageText);

        if (data.stage() != IDLE) {
            int duration = data.stage() == WATER ? 24000 : 72000;
            float progress = Math.max(0.0F, Math.min(1.0F, (float) data.clock() / duration));
            tooltip.append(helper.progress(progress).translate(new Vec2(-2, 0)));
        }

        for (ItemStack stack : data.inventory()) {
            if (!stack.isEmpty()) {
                tooltip.add(helper.item(stack));
            }
        }

        if (data.canSeal()) {
            tooltip.add(Component.translatable("tooltip.survival_reimagined.jade.tanning_barrel.ready_to_seal"));
        }
    }

    @Override
    public Data streamData(BlockAccessor accessor) {
        TanningBarrelBlockEntity barrel = (TanningBarrelBlockEntity) accessor.getBlockEntity();
        CompoundTag customData = barrel.getCustomData();

        int stage = IDLE;
        if (customData.getBoolean("Water")) {
            stage = WATER;
        } else if (customData.getBoolean("Lime")) {
            stage = LIME;
        } else if (customData.getBoolean("Tannin")) {
            stage = TANNIN;
        }

        List<ItemStack> inventory = new ArrayList<>(barrel.getContainerSize());
        for (int slot = 0; slot < barrel.getContainerSize(); slot++) {
            inventory.add(barrel.getItem(slot).copy());
        }

        return new Data(inventory,
                Math.max(0, Math.min(6000, (int) customData.getDouble("Fluid"))),
                Math.max(0, (int) customData.getDouble("BarrelClock")),
                stage,
                customData.getBoolean("CanSeal"));
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, Data> streamCodec() {
        return Data.STREAM_CODEC;
    }

    @Override
    public ResourceLocation getUid() {
        return ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "tanning_barrel");
    }

    public record Data(List<ItemStack> inventory, int fluid, int clock, int stage, boolean canSeal) {
        public static final StreamCodec<RegistryFriendlyByteBuf, Data> STREAM_CODEC = StreamCodec.composite(
                ItemStack.OPTIONAL_LIST_STREAM_CODEC, Data::inventory,
                ByteBufCodecs.VAR_INT, Data::fluid,
                ByteBufCodecs.VAR_INT, Data::clock,
                ByteBufCodecs.VAR_INT, Data::stage,
                ByteBufCodecs.BOOL, Data::canSeal,
                Data::new);
    }
}
