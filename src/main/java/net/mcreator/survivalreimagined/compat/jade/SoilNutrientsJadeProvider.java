package net.mcreator.survivalreimagined.compat.jade;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.block.SimpleAgeCropBlock;
import net.mcreator.survivalreimagined.block.fertilesoil.FertilitySoilBlockEntity;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModConfig;
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
        if (data == null || (data.hasCrop()
                && (accessor.getPlayer() == null || !accessor.getPlayer().isShiftKeyDown()))) {
            return;
        }

        tooltip.add(Component.literal("N: " + Math.round(data.nitrogen()) + "%  P: "
                + Math.round(data.phosphorus()) + "%  K: " + Math.round(data.potassium()) + "%"));
    }

    @Override
    public Data streamData(BlockAccessor accessor) {
        BlockPos targetPos = accessor.getPosition();
        BlockState targetState = accessor.getBlockState();

        // When Jade is looking at a crop, read the fertility soil directly below it.
        if (!(targetState.getBlock() instanceof net.mcreator.survivalreimagined.block.fertilesoil.FertilitySoilBlock)) {
            targetPos = targetPos.below();
        }

        BlockEntity blockEntity = accessor.getLevel().getBlockEntity(targetPos);
        if (!(blockEntity instanceof FertilitySoilBlockEntity soil)) {
            return new Data(0, 0, 0, false);
        }

        boolean hasCrop = accessor.getLevel().getBlockState(targetPos.above()).getBlock() instanceof SimpleAgeCropBlock
                || accessor.getLevel().getBlockState(targetPos.above()).getBlock() instanceof CropBlock;

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
