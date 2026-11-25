package net.mcreator.survivalreimagined.compat.jade;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.block.fertilesoil.FertilitySoilBlockEntity;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModConfig;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.StreamServerDataProvider;
import snownee.jade.api.config.IPluginConfig;

public enum SoilNutrientsJadeProvider implements IBlockComponentProvider,
        StreamServerDataProvider<BlockAccessor, SoilNutrientsJadeProvider.Data> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        if (!SurvivalReimaginedModConfig.VALUES.jade_soil_nutrients) {
            return;
        }

        Data data = decodeFromData(accessor).orElse(null);
        if (!shouldShowNutrients(accessor, data)) return;
        if (data == null) return;

        tooltip.add(Component.translatable("tooltip.survival_reimagined.jade.soil_nutrients",
                Math.round(data.nitrogen()), Math.round(data.phosphorus()), Math.round(data.potassium())));
    }

    private static boolean shouldShowNutrients(BlockAccessor accessor, Data data) {
        if (data == null) return false;
        // Unplanted soil always shows its nutrient values. Once a crop is planted,
        // require sneaking so the information does not clutter the normal crop tooltip.
        return !data.hasCrop() || (accessor.getPlayer() != null && accessor.getPlayer().isShiftKeyDown());
    }

    @Override
    public Data streamData(BlockAccessor accessor) {
        FertilitySoilBlockEntity soil = (FertilitySoilBlockEntity) accessor.getBlockEntity();
        boolean hasCrop = accessor.getLevel().getBlockState(accessor.getPosition().above())
                .is(TagKey.create(Registries.BLOCK, ResourceLocation.parse("minecraft:maintains_farmland")));
        return new Data(
                soil.getPersistentData().getDouble("N"),
                soil.getPersistentData().getDouble("P"),
                soil.getPersistentData().getDouble("K"),
                hasCrop);
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, Data> streamCodec() {
        return Data.STREAM_CODEC;
    }

    @Override
    public ResourceLocation getUid() {
        return ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "soil_nutrients");
    }

    public record Data(double nitrogen, double phosphorus, double potassium, boolean hasCrop) {
        public static final StreamCodec<RegistryFriendlyByteBuf, Data> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.DOUBLE, Data::nitrogen,
                ByteBufCodecs.DOUBLE, Data::phosphorus,
                ByteBufCodecs.DOUBLE, Data::potassium,
                ByteBufCodecs.BOOL, Data::hasCrop,
                Data::new);
    }
}
