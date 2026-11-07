package net.mcreator.survivalreimagined.block.fertilesoil;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.block.SimpleAgeCropBlock;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Optional;


public class FertilitySoilTickUpdate {
    public static void execute(LevelAccessor world, BlockPos currentPos, BlockState blockState) {

        boolean hasWater =
                   (world.getFluidState(currentPos.offset(0, 0, -2)).createLegacyBlock()).getBlock() == Blocks.WATER
                || (world.getFluidState(currentPos.offset(0, 0, -1)).createLegacyBlock()).getBlock() == Blocks.WATER
                || (world.getFluidState(currentPos.offset(0, 0, 1)).createLegacyBlock()).getBlock() == Blocks.WATER
                || (world.getFluidState(currentPos.offset(0, 0, 2)).createLegacyBlock()).getBlock() == Blocks.WATER
                || (world.getFluidState(currentPos.offset(2, 0, -2)).createLegacyBlock()).getBlock() == Blocks.WATER
                || (world.getFluidState(currentPos.offset(2, 0, -1)).createLegacyBlock()).getBlock() == Blocks.WATER
                || (world.getFluidState(currentPos.offset(2, 0, 0)).createLegacyBlock()).getBlock() == Blocks.WATER
                || (world.getFluidState(currentPos.offset(2, 0, 1)).createLegacyBlock()).getBlock() == Blocks.WATER
                || (world.getFluidState(currentPos.offset(2, 0, 2)).createLegacyBlock()).getBlock() == Blocks.WATER
                || (world.getFluidState(currentPos.offset(1, 0, -2)).createLegacyBlock()).getBlock() == Blocks.WATER
                || (world.getFluidState(currentPos.offset(1, 0, -1)).createLegacyBlock()).getBlock() == Blocks.WATER
                || (world.getFluidState(currentPos.offset(1, 0, 0)).createLegacyBlock()).getBlock() == Blocks.WATER
                || (world.getFluidState(currentPos.offset(1, 0, 1)).createLegacyBlock()).getBlock() == Blocks.WATER
                || (world.getFluidState(currentPos.offset(1, 0, 2)).createLegacyBlock()).getBlock() == Blocks.WATER
                || (world.getFluidState(currentPos.offset(-1, 0, -2)).createLegacyBlock()).getBlock() == Blocks.WATER
                || (world.getFluidState(currentPos.offset(-1, 0, -1)).createLegacyBlock()).getBlock() == Blocks.WATER
                || (world.getFluidState(currentPos.offset(-1, 0, 0)).createLegacyBlock()).getBlock() == Blocks.WATER
                || (world.getFluidState(currentPos.offset(-1, 0, 1)).createLegacyBlock()).getBlock() == Blocks.WATER
                || (world.getFluidState(currentPos.offset(-1, 0, 2)).createLegacyBlock()).getBlock() == Blocks.WATER
                || (world.getFluidState(currentPos.offset(-2, 0, -2)).createLegacyBlock()).getBlock() == Blocks.WATER
                || (world.getFluidState(currentPos.offset(-2, 0, -1)).createLegacyBlock()).getBlock() == Blocks.WATER
                || (world.getFluidState(currentPos.offset(-2, 0, 0)).createLegacyBlock()).getBlock() == Blocks.WATER
                || (world.getFluidState(currentPos.offset(-2, 0, 1)).createLegacyBlock()).getBlock() == Blocks.WATER
                || (world.getFluidState(currentPos.offset(-2, 0, 2)).createLegacyBlock()).getBlock() == Blocks.WATER;

        if (hasWater) {
            int currentMoisture = blockState.getOptionalValue(BlockStateProperties.MOISTURE).orElse(-1);
            int maxMoisture = blockState.getBlock().getStateDefinition().getProperty("moisture") instanceof IntegerProperty ip ? ip.getPossibleValues().stream().max(Integer::compareTo).orElse(-1) : -1;

            if (getBlockNBTNumber(world, currentPos, "MoistureCheck") >= 5 && currentMoisture < maxMoisture) {
                if (Math.random() < 0.5) {
                    int newValue = currentMoisture + 1;
                    if (blockState.getBlock().getStateDefinition().getProperty("moisture") instanceof IntegerProperty ip && ip.getPossibleValues().contains(newValue)) {
                        world.setBlock(currentPos, blockState.setValue(ip, newValue), 3);
                    }
                }
                if (!world.isClientSide()) {
                    setBlockNBTNumber(world, currentPos, "MoistureCheck", 0);
                    updateBlock(world, currentPos);
                }
            }
        } else {
            int currentMoisture = blockState.getOptionalValue(BlockStateProperties.MOISTURE).orElse(-1);
            if (getBlockNBTNumber(world, currentPos, "MoistureCheck") >= 6 && currentMoisture > 0) {
                int newValue = currentMoisture - 1;
                if (blockState.getBlock().getStateDefinition().getProperty("moisture") instanceof IntegerProperty ip && ip.getPossibleValues().contains(newValue)) {
                    world.setBlock(currentPos, blockState.setValue(ip, newValue), 3);
                }
                if (!world.isClientSide()) {
                    setBlockNBTNumber(world, currentPos, "MoistureCheck", 0);
                    updateBlock(world, currentPos);
                }
            }

            if (blockState.getOptionalValue(BlockStateProperties.MOISTURE).orElse(-1) == 0 && world.getBlockState(currentPos.above()).isAir()) {
                if (Math.random() < 0.15) {
                    BlockState targetState = null;
                    if (world.getBlockState(currentPos).is(SurvivalReimaginedModBlocks.LOW_FERTILITY_FARMLAND.get())) {
                        targetState = Blocks.DIRT.defaultBlockState();
                    } else if (world.getBlockState(currentPos).is(SurvivalReimaginedModBlocks.MEDIUM_FERTILITY_SOIL.get())) {
                        targetState = SurvivalReimaginedModBlocks.MEDIUM_FERTILITY_DIRT.get().defaultBlockState();
                    } else if (world.getBlockState(currentPos).is(SurvivalReimaginedModBlocks.HIGH_FERTILITY_SOIL.get())) {
                        targetState = SurvivalReimaginedModBlocks.HIGH_FERTILITY_DIRT.get().defaultBlockState();
                    }

                    if (targetState != null) {
                        BlockState oldState = world.getBlockState(currentPos);
                        for (Property<?> propOld : oldState.getProperties()) {
                            PropertyPropertyCopyHelper(targetState, oldState, propOld);
                        }
                        world.setBlock(currentPos, targetState, 3);
                    }
                }
                if (!world.isClientSide()) {
                    setBlockNBTNumber(world, currentPos, "MoistureCheck", 0);
                    updateBlock(world, currentPos);
                }
            }
        }

        if (!world.isClientSide()) {
            setBlockNBTNumber(world, currentPos, "MoistureCheck", getBlockNBTNumber(world, currentPos, "MoistureCheck") + 1);
            setBlockNBTNumber(world, currentPos, "MoistureClock", getBlockNBTNumber(world, currentPos, "MoistureClock") + 1);
        }


        BlockState aboveState = world.getBlockState(currentPos.above());


        TagKey<Block> maintainsFarmlandTag = TagKey.create(Registries.BLOCK, ResourceLocation.parse("minecraft:maintains_farmland"));


        if (aboveState.is(maintainsFarmlandTag)) {
            Property<?> ageProperty = aboveState.getBlock().getStateDefinition().getProperty("age");

            int currentAge = -1;

            if (ageProperty instanceof IntegerProperty integerProperty && aboveState.hasProperty(integerProperty)) {
                currentAge = aboveState.getValue(integerProperty);
            }




            if (currentAge != -1 && !world.isClientSide()) {
                BlockEntity blockEntity = world.getBlockEntity(currentPos);

                if (blockEntity instanceof FertilitySoilBlockEntity soilBlockEntity) {

                    soilBlockEntity.fertilizerClockPrimitive++;

                    if (soilBlockEntity.fertilizerClockPrimitive >= 600) {
                        soilBlockEntity.fertilizerClockPrimitive = 0;


                        double currentN = getBlockNBTNumber(world, currentPos, "N");
                        double currentP = getBlockNBTNumber(world, currentPos, "P");
                        double currentK = getBlockNBTNumber(world, currentPos, "K");


                        int availableCount = 0;
                        if (currentN > 0) availableCount++;
                        if (currentP > 0) availableCount++;
                        if (currentK > 0) availableCount++;

                        if (availableCount > 0) {
                            int choice = RandomSource.create().nextInt(availableCount);
                            String nutrient;
                            double currentValue;

                            if (currentN > 0 && choice-- == 0) {
                                nutrient = "N";
                                currentValue = currentN;
                            } else if (currentP > 0 && choice-- == 0) {
                                nutrient = "P";
                                currentValue = currentP;
                            } else {
                                nutrient = "K";
                                currentValue = currentK;
                            }
                            setBlockNBTNumber(world, currentPos, nutrient, Math.max(0, currentValue - 1));

                            soilBlockEntity.setChanged();
                            updateBlock(world, currentPos);

                        }
                    }
                }
            }
        } else {
            if (!world.isClientSide()) {
                BlockEntity blockEntity = world.getBlockEntity(currentPos);
                if (blockEntity instanceof FertilitySoilBlockEntity soilBlockEntity) {
                    soilBlockEntity.fertilizerClockPrimitive = 0;
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> void PropertyPropertyCopyHelper(BlockState target, BlockState oldState, Property<T> propOld) {
        Property<T> propNew = (Property<T>) target.getBlock().getStateDefinition().getProperty(propOld.getName());
        if (propNew != null && target.getValue(propNew) != null) {
            try {
                target.setValue(propNew, oldState.getValue(propOld));
            } catch (Exception ignored) {}
        }
    }

    private static void setBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag, double value) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CustomDataHolder holder) {
            holder.getPersistentData().putDouble(tag, value);
        }
    }

    private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CustomDataHolder holder) {
            return holder.getPersistentData().getDouble(tag);
        }
        return -1;
    }

    private static void updateBlock(LevelAccessor world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (world instanceof Level level) {
            level.sendBlockUpdated(pos, state, state, 3);
        }
    }

    public interface CustomDataHolder {
        CompoundTag getPersistentData();
    }
}
