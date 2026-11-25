package net.mcreator.survivalreimagined.compat.jade;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.block.SimpleAgeCropBlock;
import net.mcreator.survivalreimagined.block.fertilesoil.FertilitySoilBlock;
import net.mcreator.survivalreimagined.block.fertilesoil.FertilitySoilBlockEntity;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModConfig;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
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
        if (!SurvivalReimaginedModConfig.VALUES.jade_soil_nutrients) return;

        Data data = decodeFromData(accessor).orElse(null);
        if (data == null || !data.hasSoil()
                || (data.cropTarget() && (accessor.getPlayer() == null
                || !accessor.getPlayer().isShiftKeyDown()))) {
            return;
        }

        if (data.cropTarget() && !data.fertilityLabel().isEmpty()) {
            tooltip.add(Component.literal(data.fertilityLabel()));
        }

        tooltip.add(Component.literal("N: " + Math.round(data.nitrogen()) + "%  P: "
                + Math.round(data.phosphorus()) + "%  K: " + Math.round(data.potassium()) + "%"));
    }

    @Override
    public Data streamData(BlockAccessor accessor) {
        BlockState targetState = accessor.getBlockState();
        boolean cropTarget = targetState.getBlock() instanceof SimpleAgeCropBlock
                || targetState.getBlock() instanceof CropBlock;

        // Directly targeting fertility soil always displays its nutrients.
        // Targeting a crop reads the fertility soil immediately underneath it.
        BlockPos soilPos = cropTarget ? accessor.getPosition().below() : accessor.getPosition();
        if (!cropTarget && !(targetState.getBlock() instanceof FertilitySoilBlock)) {
            return new Data(0, 0, 0, false, false, 0);
        }

        BlockEntity blockEntity = accessor.getLevel().getBlockEntity(soilPos);
        if (!(blockEntity instanceof FertilitySoilBlockEntity soil)) {
            return new Data(0, 0, 0, false, cropTarget, 0);
        }

        int fertilityLevel = getFertilityLevel(accessor.getLevel().getBlockState(soilPos));

        return new Data(
                soil.getPersistentData().getDouble("N"),
                soil.getPersistentData().getDouble("P"),
                soil.getPersistentData().getDouble("K"),
                true,
                cropTarget,
                fertilityLevel);
    }

    private static int getFertilityLevel(BlockState soilState) {
        if (soilState.is(SurvivalReimaginedModBlocks.LOW_FERTILITY_FARMLAND.get())) return 1;
        if (soilState.is(SurvivalReimaginedModBlocks.MEDIUM_FERTILITY_SOIL.get())) return 2;
        if (soilState.is(SurvivalReimaginedModBlocks.HIGH_FERTILITY_SOIL.get())) return 3;
        return 0;
    }

    private static String getFertilityLabel(int level) {
        return switch (level) {
            case 1 -> "Low Fertility Farmland";
            case 2 -> "Medium Fertility Soil";
            case 3 -> "High Fertility Soil";
            default -> "";
        };
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, Data> streamCodec() {
        return Data.STREAM_CODEC;
    }

    @Override
    public ResourceLocation getUid() {
        return ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "soil_nutrients");
    }

    public record Data(double nitrogen, double phosphorus, double potassium, boolean hasSoil,
                       boolean cropTarget, int fertilityLevel) {
        public String fertilityLabel() {
            return getFertilityLabel(fertilityLevel);
        }

        public static final StreamCodec<RegistryFriendlyByteBuf, Data> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.DOUBLE, Data::nitrogen,
                ByteBufCodecs.DOUBLE, Data::phosphorus,
                ByteBufCodecs.DOUBLE, Data::potassium,
                ByteBufCodecs.BOOL, Data::hasSoil,
                ByteBufCodecs.BOOL, Data::cropTarget,
                ByteBufCodecs.VAR_INT, Data::fertilityLevel,
                Data::new);
    }
}
