package net.mcreator.survivalreimagined.procedures;

import net.fabricmc.loader.api.FabricLoader;
import net.mcreator.survivalreimagined.block.fruit.FruitBlock;
import net.mcreator.survivalreimagined.block.fruit.FruitBlockEntity;
import net.mcreator.survivalreimagined.compat.sereneseasons.GetCurrentSeason;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class GrowingLogic {
    public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockState) {
        if (world.isClientSide()) return;

        BlockPos pos = BlockPos.containing(x,y,z);
        boolean hasSereneSeasons = FabricLoader.getInstance().isModLoaded("sereneseasons");
        double maxClock = 200;

        if (hasSereneSeasons) {
            String currentSeason = GetCurrentSeason.execute(world);
            maxClock = 800;

            if ("Spring".equals(currentSeason) && blockState.is(TagKey.create(Registries.BLOCK, ResourceLocation.parse("sereneseasons:spring_crops")))) {
                maxClock = 100;
            } else if ("Summer".equals(currentSeason) && blockState.is(TagKey.create(Registries.BLOCK, ResourceLocation.parse("sereneseasons:summer_crops")))) {
                maxClock = 100;
            } else if ("Autumn".equals(currentSeason) && blockState.is(TagKey.create(Registries.BLOCK, ResourceLocation.parse("sereneseasons:autumn_crops")))) {
                maxClock = 100;
            } else if ("Winter".equals(currentSeason) && blockState.is(TagKey.create(Registries.BLOCK, ResourceLocation.parse("sereneseasons:winter_crops")))) {
                maxClock = 100;
            }
        }

        boolean isFruitOrBush = blockState.is(TagKey.create(Registries.BLOCK, ResourceLocation.parse("c:fruit_of_tree")))
                || blockState.is(TagKey.create(Registries.BLOCK, ResourceLocation.parse("c:berry_bush")))
                || blockState.getBlock() instanceof FruitBlock;

        boolean canGrowGroundCrop = false;
        BlockPos belowPos = pos.below();
        BlockState belowState = world.getBlockState(belowPos);

        if (belowState.is(TagKey.create(Registries.BLOCK, ResourceLocation.parse("c:soil")))) {
            int currentAge = getAge(blockState);
            int maxAge = getMaxAge(blockState);

            if (currentAge < maxAge
                && getBlockNBTNumber(world, belowPos, "N") > 0 && getBlockNBTNumber(world, belowPos, "P") > 0 && getBlockNBTNumber(world, belowPos, "K") > 0) {
                canGrowGroundCrop = true;
            }
        }

        if (canGrowGroundCrop || isFruitOrBush) {
            BlockEntity blockEntity = world.getBlockEntity(pos);

            if (blockEntity instanceof FruitBlockEntity fruitEntity) {
                double nextClockValue = fruitEntity.getGrowClock() + 1;

                if (nextClockValue >= maxClock) {
                    fruitEntity.setGrowClock(0);

                    if (Math.random() < 0.45) {
                        IntegerProperty ageProperty = getAgeProperty(blockState);
                        int currentAge = getAge(blockState);
                        int maxAge = getMaxAge(blockState);
                        int nextAge = currentAge + 1;

                        if (ageProperty != null && currentAge >= 0 && nextAge <= maxAge) {
                            world.setBlock(pos, blockState.setValue(ageProperty, nextAge), 3);
                        }
                    }
                } else {
                    fruitEntity.setGrowClock(nextClockValue);
                }
            }
        }
        handleCornAndFarmingFailure(world, x,y,z, blockState, pos);
    }
    private static void handleCornAndFarmingFailure(LevelAccessor world, double x, double y, double z, BlockState blockState, BlockPos pos) {
        if (blockState.is(SurvivalReimaginedModBlocks.CORN_STALK_BOTTOM.get())) {
            int age = getAge(blockState);
            if (age == 4 && world.isEmptyBlock(pos.above())) {
                world.setBlock(pos.above(), SurvivalReimaginedModBlocks.CORN_STALK_BOTTOM.get().defaultBlockState(), 3);
            }
        }
        BlockPos belowPos = pos.below();
        if (world.getBlockState(belowPos).is(TagKey.create(Registries.BLOCK, ResourceLocation.parse("c:soil")))) {
            if (getBlockNBTNumber(world, belowPos, "N") <= 0 || getBlockNBTNumber(world, belowPos, "P") <= 0 || getBlockNBTNumber(world, belowPos, "K") <= 0) {
                double deathClock = getBlockNBTNumber(world, belowPos, "DeathClock") + 1;
                putBlockNBTNumber(world, belowPos, "DeathClock", deathClock);

                if (deathClock > 2000) {
                    putBlockNBTNumber(world, belowPos, "DeathClock", 0);
                    world.setBlock(pos, Blocks.DEAD_BUSH.defaultBlockState(), 3);

                    if (world instanceof ServerLevel serverLevel) {
                        final Vec3 center = new Vec3(x,y,z);
                        for (Entity entity : serverLevel.getEntitiesOfClass(Entity.class, new AABB(center, center).inflate(10), e -> e instanceof ServerPlayer)) {
                            ServerPlayer player = (ServerPlayer) entity;
                            AdvancementHolder adv = player.server.getAdvancements().get(ResourceLocation.parse("survival_reimagined:terrible_farmer"));
                            if (adv != null) {
                                AdvancementProgress progress = player.getAdvancements().getOrStartProgress(adv);
                                if (!progress.isDone()) {
                                    for (String criteria : progress.getRemainingCriteria()) {
                                        player.getAdvancements().award(adv, criteria);
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                putBlockNBTNumber(world, belowPos, "DeathClock", 0);
            }
        }
    }
    private static IntegerProperty getAgeProperty(BlockState state) {
        Property<?> property = state.getBlock().getStateDefinition().getProperty("age");
        return property instanceof IntegerProperty integerProperty ? integerProperty : null;
    }

    private static int getAge(BlockState state) {
        IntegerProperty ageProperty = getAgeProperty(state);
        return ageProperty == null ? -1 : state.getValue(ageProperty);
    }

    private static int getMaxAge(BlockState state) {
        IntegerProperty ageProperty = getAgeProperty(state);
        return ageProperty == null
                ? -1
                : ageProperty.getPossibleValues().stream().max(Integer::compareTo).orElse(-1);
    }

    private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity != null) {
            CompoundTag compound = blockEntity.saveWithId(world.registryAccess());
            if (compound.contains(tag)) {
                return compound.getDouble(tag);
            }
        }
        return 0;
    }
    private static void putBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag, double value) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity != null) {
            CompoundTag compound = blockEntity.saveWithId(world.registryAccess());
            compound.putDouble(tag, value);
            blockEntity.loadWithComponents(compound, world.registryAccess());
            blockEntity.setChanged();

            if (world instanceof ServerLevel serverLevel) {
                serverLevel.getChunkSource().blockChanged(pos);
            }
        }
    }
}