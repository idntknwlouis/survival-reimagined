package net.mcreator.survivalreimagined.procedures;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.block.fertilesoil.FertilitySoilBlockEntity;
import net.mcreator.survivalreimagined.block.fertilesoil.FertilitySoilTickUpdate;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Map;
import java.util.function.Supplier;

public class FertilitySoilTilling {
    private static final Map<Block, Supplier<Block>> GRASS_TO_DIRT = Map.of(
            Blocks.GRASS_BLOCK, () -> Blocks.DIRT,
            SurvivalReimaginedModBlocks.MEDIUM_FERTILITY_GRASS.get(), SurvivalReimaginedModBlocks.MEDIUM_FERTILITY_DIRT,
            SurvivalReimaginedModBlocks.HIGH_FERTILITY_GRASS.get(), SurvivalReimaginedModBlocks.HIGH_FERTILITY_DIRT
    );

    private static final Map<Block, TillTarget> DIRT_TO_SOIL = Map.of(
            Blocks.DIRT, new TillTarget(SurvivalReimaginedModBlocks.LOW_FERTILITY_FARMLAND, 20),
            SurvivalReimaginedModBlocks.MEDIUM_FERTILITY_DIRT.get(), new TillTarget(SurvivalReimaginedModBlocks.MEDIUM_FERTILITY_SOIL, 60),
            SurvivalReimaginedModBlocks.HIGH_FERTILITY_DIRT.get(), new TillTarget(SurvivalReimaginedModBlocks.HIGH_FERTILITY_SOIL, 80)
    );

    public static void register() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (hand != InteractionHand.MAIN_HAND) {
                return InteractionResult.PASS;
            }
            BlockPos pos = hitResult.getBlockPos();
            BlockState state = world.getBlockState(pos);
            Block block = state.getBlock();

            if (!GRASS_TO_DIRT.containsKey(block) && !DIRT_TO_SOIL.containsKey(block)) {
                return InteractionResult.PASS;
            }
            if (!(player.getItemInHand(hand).getItem() instanceof HoeItem)) {
                return InteractionResult.PASS;
            }

            if (world.isClientSide()) {
                return InteractionResult.PASS;
            }

            execute(world, pos, player);
            return InteractionResult.SUCCESS;
        });
    }

    public static void execute(Level world, BlockPos pos, Player entity) {
        if (entity == null) return;

        ItemStack handItem = entity.getMainHandItem();
        if (!handItem.is(ItemTags.HOES)) return;

        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (GRASS_TO_DIRT.containsKey(block)) {
            SurvivalReimaginedMod.queueServerWork(1, () -> {
                Block targetDirt = GRASS_TO_DIRT.get(block).get();
                transformBlock(world, pos, targetDirt.defaultBlockState());
                world.levelEvent(2001, pos, Block.getId(Blocks.DIRT.defaultBlockState()));
                entity.swing(InteractionHand.MAIN_HAND, true);
            });
            return;
        }

        if (DIRT_TO_SOIL.containsKey(block)) {
            TillTarget target = DIRT_TO_SOIL.get(block);
            CustomData customData = handItem.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
            CompoundTag nbt = customData.copyTag();
            double currentTicks = nbt.getDouble("TillClock") + 1;

            nbt.putDouble("TillClock", currentTicks);
            handItem.set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));

            if (currentTicks >= 5) {
                SurvivalReimaginedMod.queueServerWork(1, () -> {
                    CustomData currentData = handItem.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
                    double updatedTicks = currentData.copyTag().getDouble("TillClock");

                    if (Math.random() < 0.3 || updatedTicks >= 20) {
                        CompoundTag resetNBT = currentData.copyTag().copy();
                        resetNBT.putDouble("TillClock", 0);
                        handItem.set(DataComponents.CUSTOM_DATA, CustomData.of(resetNBT));

                        BlockState targetState = target.getStateSupplier().get().defaultBlockState();
                        transformBlock(world, pos, targetState);

                        if (!world.isClientSide()) {
                            world.playSound(null, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0f, 1.0f);
                        } else {
                            world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0f, 1.0f, false);
                        }

                        if (!world.isClientSide()) {
                            BlockEntity existingEntity = world.getBlockEntity(pos);
                            if (existingEntity != null) {
                                if (existingEntity instanceof FertilitySoilTickUpdate.CustomDataHolder holder) {
                                    CompoundTag data = holder.getPersistentData();

                                    data.putDouble("N", target.getValueNPK());
                                    data.putDouble("P", target.getValueNPK());
                                    data.putDouble("K", target.getValueNPK());
                                    data.putDouble("MoistureCheck", 0);
                                    data.putDouble("MoistureClock", 0);

                                    System.out.println(
                                            "[TILL DEBUG] Initialized NPK: N="
                                                    + data.getDouble("N")
                                                    + " P=" + data.getDouble("P")
                                                    + " K=" + data.getDouble("K")
                                    );
                                } else {
                                    System.out.println(
                                            "[TILL DEBUG] ERROR: Soil block entity missing at " + pos
                                    );
                                }
                                existingEntity.setChanged();
                                world.sendBlockUpdated(pos, targetState, targetState, 3);

                            }
                        }

                        if (entity instanceof ServerPlayer serverPlayer) {
                            AdvancementHolder adv = serverPlayer.server.getAdvancements().get(ResourceLocation.parse("survival_reimagined:soil_preperation"));
                            if (adv != null) {
                                AdvancementProgress ap = serverPlayer.getAdvancements().getOrStartProgress(adv);
                                if (!ap.isDone()) {
                                    for (String criteria : ap.getRemainingCriteria()) {
                                        serverPlayer.getAdvancements().award(adv, criteria);
                                    }
                                }
                            }
                        }
                        if (world instanceof ServerLevel serverLevel) {
                            handItem.hurtAndBreak(1, serverLevel, (ServerPlayer) entity, item -> {});
                        }
                    }
                });
            }
            entity.swing(InteractionHand.MAIN_HAND, true);
            world.levelEvent(2001, pos, Block.getId(block.defaultBlockState()));
        }
    }

    private static void transformBlock(Level world, BlockPos pos, BlockState newState) {
        BlockState oldState = world.getBlockState(pos);
        for (Property<?> oldProperty : oldState.getProperties()) {
            Property<?> newProperty = newState.getBlock().getStateDefinition().getProperty(oldProperty.getName());
            if (newProperty != null && newState.getValue(newProperty) != null) {
                try {
                    newState = applyProperty(newState, newProperty, oldState.getValue(oldProperty));
                } catch (IllegalArgumentException ignored) {}
            }
        }
        boolean changed = world.setBlock(pos, newState, 3);
        System.out.println(
                "[TILL DEBUG] Changed=" + changed
                        + " | Expected=" + newState
                        + " | Actual=" + world.getBlockState(pos)
                        + " | BlockEntity=" + world.getBlockEntity(pos)
        );
        world.getChunkAt(pos).setUnsaved(true);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> BlockState applyProperty(BlockState state, Property<T> property, Object value) {
        return state.setValue(property, (T) value);
    }

    private static class TillTarget {
        private final Supplier<Block> stateSupplier;
        private final double ValueNPK;

        public TillTarget(Supplier<Block> stateSupplier, double ValueNPK) {
            this.stateSupplier = stateSupplier;
            this.ValueNPK = ValueNPK;

        }

        public Supplier<Block> getStateSupplier() {
            return stateSupplier;
        }

        public double getValueNPK() {
            return ValueNPK;
        }
    }
}
