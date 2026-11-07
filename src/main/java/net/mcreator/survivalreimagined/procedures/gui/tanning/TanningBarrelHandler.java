package net.mcreator.survivalreimagined.procedures.gui.tanning;

import net.mcreator.survivalreimagined.block.entity.TanningBarrelBlockEntity;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class TanningBarrelHandler {
    public static void execute(LevelAccessor world, double x, double y, double z, BlockState state) {
        BlockPos pos = BlockPos.containing(x,y,z);
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (!world.isClientSide()) {
            if (blockEntity instanceof TanningBarrelBlockEntity barrel) {
                barrel.getCustomData().putDouble("BarrelSpriteClock", (getBlockNBTNumber(world, pos, "BarrelSpriteClock") + 1));
                barrel.setChanged();
            }
            if (world instanceof Level level1) {
                BlockState state1 = world.getBlockState(pos);
                level1.sendBlockUpdated(pos, state1, state1, 3);
            }
        }

        if (getBlockNBTNumber(world, pos, "BarrelSpriteClock") == 3) {
            if (!world.isClientSide()) {
                if (blockEntity instanceof TanningBarrelBlockEntity barrel) {
                    barrel.getCustomData().putDouble("BarrelSpriteClock", 0);
                    barrel.setChanged();
                }
                if (world instanceof Level level) {
                    BlockState state1 = world.getBlockState(pos);
                    level.sendBlockUpdated(pos, state1, state1, 3);
                }
            }
            SpriteIndexHandler.execute(world,x,y,z);
        }

        if (getBlockNBTNumber(world, pos, "Fluid") < 6000
                && !getBlockNBTLogic(world, pos, "Lime")
                && !getBlockNBTLogic(world, pos, "Tannin")) {
            if (itemFromBlockInventory(world, pos, 0).is(Items.WATER_BUCKET)) {
                if (!world.isClientSide()) {
                    if (blockEntity instanceof TanningBarrelBlockEntity barrel) {
                        barrel.getCustomData().putBoolean("Water", true);
                        barrel.getCustomData().putDouble("Fluid", (getBlockNBTNumber(world, pos, "Fluid") + 2000));
                        barrel.setItem(0, new ItemStack(Items.BUCKET));
                        barrel.setChanged();

                        if (world instanceof Level level) {
                            BlockState state1 = world.getBlockState(pos);
                            level.sendBlockUpdated(pos, state1, state1, 3);

                            level.playSound(null, pos, SoundEvents.PLAYER_SPLASH, SoundSource.BLOCKS, 1.0f, 1.0f);
                        }
                    }
                }
            }
        }

        if (getBlockNBTLogic(world, pos, "Water") && itemFromBlockInventory(world, pos, 0).is(SurvivalReimaginedModItems.QUICK_LIME.get())) {
            double requiredAmount = (getBlockNBTNumber(world, pos, "Fluid") / 6000.0) * 64.0;
            if (itemFromBlockInventory(world, pos, 0).getCount() >= requiredAmount && itemFromBlockInventory(world, pos, 0).getCount() > 0) {
                if (!world.isClientSide()) {
                    if (blockEntity instanceof TanningBarrelBlockEntity barrel) {
                        barrel.getCustomData().putBoolean("CanSeal", true);
                        barrel.setChanged();
                    }
                    if (world instanceof Level level) {
                        BlockState state1 = world.getBlockState(pos);
                        level.sendBlockUpdated(pos, state1, state1, 3);
                    }
                }
            }

            if (getBlockNBTNumber(world, pos, "BarrelClock") >= 24000) {
                BlockState state1 = world.getBlockState(pos);
                if (state1.getBlock().getStateDefinition().getProperty("opened") instanceof BooleanProperty boolProp) {
                    world.setBlock(pos, state1.setValue(boolProp, true), 3);
                }
                if (!world.isClientSide()) {
                    if (blockEntity instanceof TanningBarrelBlockEntity barrel) {
                        barrel.getCustomData().putBoolean("Water", false);
                        barrel.getCustomData().putBoolean("Lime", true);
                        barrel.getCustomData().putBoolean("CanSeal", false);
                        barrel.getCustomData().putDouble("BarrelClock", 0);
                        barrel.setChanged();
                    }
                    if (world instanceof Level level) {
                        BlockState state2 = world.getBlockState(pos);
                        level.sendBlockUpdated(pos, state2, state2, 3);
                    }
                }

                if (blockEntity instanceof Container container) {
                    ItemStack currentStack = container.getItem(0);
                    int nextCount = (int) (currentStack.getCount() - requiredAmount);
                    if (nextCount <= 0) {
                        container.setItem(0, ItemStack.EMPTY);
                    } else {
                        ItemStack setStack = currentStack.copy();
                        setStack.setCount(nextCount);
                        container.setItem(0, setStack);
                    }
                }

                if (!world.isClientSide()) {
                    if (world instanceof Level level) {
                        BlockState state2 = world.getBlockState(pos);
                        level.sendBlockUpdated(pos, state2, state2, 3);
                    }
                }
            }
        }

        if (getBlockNBTLogic(world, pos, "Water") && itemFromBlockInventory(world, pos, 0).is(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "tanning/makes_tannin")))) {
            double requiredAmount = (getBlockNBTNumber(world, pos, "Fluid") / 6000.0) * 64.0;
            if (itemFromBlockInventory(world, pos, 0).getCount() >= requiredAmount && itemFromBlockInventory(world, pos, 0).getCount() > 0) {
                if (!world.isClientSide()) {
                    if (blockEntity instanceof TanningBarrelBlockEntity barrel) {
                        barrel.getCustomData().putBoolean("CanSeal", true);
                        barrel.setChanged();
                    }
                    if (world instanceof Level level) {
                        BlockState state1 = world.getBlockState(pos);
                        level.sendBlockUpdated(pos, state1, state1, 3);
                    }
                }
            }

            if (getBlockNBTNumber(world, pos, "BarrelClock") >= 24000) {
                BlockState state1 = world.getBlockState(pos);
                if (state1.getBlock().getStateDefinition().getProperty("opened") instanceof BooleanProperty boolProp) {
                    world.setBlock(pos, state1.setValue(boolProp, true), 3);
                }

                if (!world.isClientSide()) {
                    if (blockEntity instanceof TanningBarrelBlockEntity barrel) {
                        barrel.getCustomData().putBoolean("Water", false);
                        barrel.getCustomData().putBoolean("Tannin", true);
                        barrel.getCustomData().putBoolean("CanSeal", false);
                        barrel.getCustomData().putDouble("BarrelClock", 0);
                        barrel.setChanged();
                    }
                    if (world instanceof Level level) {
                        BlockState state2 = world.getBlockState(pos);
                        level.sendBlockUpdated(pos, state2, state2, 3);
                    }
                }
                if (blockEntity instanceof Container container) {
                    ItemStack currentStack = container.getItem(0);
                    int nextCount = (int) (currentStack.getCount() - requiredAmount);
                    if (nextCount <= 0) {
                        container.setItem(0, ItemStack.EMPTY);
                    } else {
                        ItemStack setStack = currentStack.copy();
                        setStack.setCount(nextCount);
                        container.setItem(0, setStack);
                    }
                }
                if (!world.isClientSide()) {
                    if (world instanceof Level level) {
                        BlockState state2 = world.getBlockState(pos);
                        level.sendBlockUpdated(pos, state2, state2, 3);
                    }
                }
            }
        }

        if (blockEntity instanceof Container container) {
            int slotCount = container.getContainerSize();
            for (int i = 0; i < slotCount; i++) {
                ItemStack itemstackiterator = container.getItem(i);
                if (i == 1) {
                    double currentDrying = 0;
                    CustomData customData = itemstackiterator.get(DataComponents.CUSTOM_DATA);
                    if (customData != null) {
                        currentDrying = customData.copyTag().getDouble("Drying");
                    }

                    if (getBlockNBTLogic(world, pos, "Lime") && itemstackiterator.is(SurvivalReimaginedModItems.DRIED_HIDE.get())) {
                        if (itemFromBlockInventory(world, pos, 1).getCount() <= (getBlockNBTNumber(world,pos, "Fluid") / 6000.0) * 6.0 && itemFromBlockInventory(world, pos, 1).getCount() > 0) {
                            if (!world.isClientSide()) {
                                if (blockEntity instanceof TanningBarrelBlockEntity barrel) {
                                    barrel.getCustomData().putBoolean("CanSeal", true);
                                    barrel.setChanged();
                                }
                                if (world instanceof Level level) {
                                    BlockState state1 = world.getBlockState(pos);
                                    level.sendBlockUpdated(pos, state1, state1, 3);
                                }
                            }
                        }

                        if (getBlockNBTNumber(world, pos, "BarrelClock") >= 72000) {
                            BlockState state1 = world.getBlockState(pos);
                            if (state1.getBlock().getStateDefinition().getProperty("opened") instanceof BooleanProperty boolProp) {
                                world.setBlock(pos, state1.setValue(boolProp, true), 3);
                            }
                            if (!world.isClientSide()) {
                                if (blockEntity instanceof TanningBarrelBlockEntity barrel) {
                                    barrel.getCustomData().putBoolean("CanSeal", false);
                                    barrel.getCustomData().putDouble("BarrelClock", 0);
                                    barrel.setChanged();
                                }
                                if (world instanceof Level level) {
                                    BlockState state2 = world.getBlockState(pos);
                                    level.sendBlockUpdated(pos, state2, state2, 3);
                                }
                            }
                            if (!world.isClientSide()) {
                                int processedCount = itemstackiterator.getCount();
                                ItemStack preparedStack = new ItemStack(SurvivalReimaginedModItems.PREPARED_HIDE.get());
                                preparedStack.setCount(processedCount);
                                container.setItem(i, preparedStack);

                                if (blockEntity instanceof TanningBarrelBlockEntity barrel) {
                                    double fluid = barrel.getCustomData().getDouble("Fluid");
                                    barrel.getCustomData().putDouble("Fluid", Math.max(0, fluid - processedCount * 500.0));
                                    barrel.setChanged();
                                }
                                container.setChanged();

                                if (world instanceof Level level) {
                                    BlockState state2 = world.getBlockState(pos);
                                    level.sendBlockUpdated(pos, state2, state2, 3);
                                }
                            }


                            if (!world.isClientSide()) {
                                if (blockEntity instanceof TanningBarrelBlockEntity barrel) {
                                    barrel.getCustomData().putDouble("Fluid", (getBlockNBTNumber(world, pos, "Fluid") - itemFromBlockInventory(world, pos, 1).getCount() * 500));
                                    barrel.setChanged();
                                }
                                if (world instanceof Level level) {
                                    BlockState state2 = world.getBlockState(pos);
                                    level.sendBlockUpdated(pos, state2, state2, 3);
                                }
                            }
                        }
                    } else if (getBlockNBTLogic(world, pos, "Tannin") && itemstackiterator.is(SurvivalReimaginedModItems.PREPARED_HIDE.get())) {
                        if (itemFromBlockInventory(world, pos, 1).getCount() <= (getBlockNBTNumber(world,pos, "Fluid") / 6000.0) * 6.0 && itemFromBlockInventory(world, pos, 1).getCount() > 0) {
                            if (!world.isClientSide()) {
                                if (blockEntity instanceof TanningBarrelBlockEntity barrel) {
                                    barrel.getCustomData().putBoolean("CanSeal", true);
                                    barrel.setChanged();
                                }
                                if (world instanceof Level level) {
                                    BlockState state1 = world.getBlockState(pos);
                                    level.sendBlockUpdated(pos, state1, state1, 3);
                                }
                            }
                        }
                        if (getBlockNBTNumber(world, pos, "BarrelClock") >= 72000) {
                            BlockState state1 = world.getBlockState(pos);
                            if (state1.getBlock().getStateDefinition().getProperty("opened") instanceof BooleanProperty boolProp) {
                                world.setBlock(pos, state1.setValue(boolProp, true), 3);
                            }
                            if (!world.isClientSide()) {
                                if (blockEntity instanceof TanningBarrelBlockEntity barrel) {
                                    barrel.getCustomData().putBoolean("CanSeal", false);
                                    barrel.getCustomData().putDouble("Fluid", (getBlockNBTNumber(world, pos, "Fluid") - itemFromBlockInventory(world, pos, 1).getCount() * 500));
                                    barrel.getCustomData().putDouble("BarrelClock", 0);
                                    barrel.setChanged();
                                }
                                ItemStack leatherStack = new ItemStack(Items.LEATHER);
                                leatherStack.setCount(itemstackiterator.getCount() * 2);
                                container.setItem(1, leatherStack);
                                container.setChanged();

                                if (world instanceof Level level) {
                                    BlockState state2 = world.getBlockState(pos);
                                    level.sendBlockUpdated(pos, state2, state2, 3);
                                }
                            }
                        }
                    }
                }
            }
        }

        if (!getPropertyValueBoolean(state, "opened")) {
            if (!world.isClientSide()) {
                if (blockEntity instanceof TanningBarrelBlockEntity barrel) {
                    barrel.getCustomData().putDouble("BarrelClock", (getBlockNBTNumber(world, pos, "BarrelClock") + 1));
                    barrel.setChanged();
                }
                if (world instanceof Level level) {
                    BlockState state1 = world.getBlockState(pos);
                    level.sendBlockUpdated(pos, state1, state1, 3);
                }
            }
        }
        if (getBlockNBTLogic(world, pos, "Water")) {
            if (itemFromBlockInventory(world, pos, 0).getCount() < (getBlockNBTNumber(world, pos, "Fluid") / 6000.0) * 64.0) {
                if (!world.isClientSide()) {
                    if (blockEntity instanceof TanningBarrelBlockEntity barrel) {
                        barrel.getCustomData().putBoolean("CanSeal", false);
                        barrel.setChanged();
                    }
                    if (world instanceof Level level) {
                        BlockState state1 = world.getBlockState(pos);
                        level.sendBlockUpdated(pos, state1, state1, 3);
                    }
                }
            }
        } else if (getBlockNBTLogic(world, pos, "Lime") || getBlockNBTLogic(world, pos, "Tannin")) {
            int itemCount = itemFromBlockInventory(world, pos, 1).getCount();
            double fluid = getBlockNBTNumber(world, pos, "Fluid");
            double requireAmount = (fluid / 6000.0) * 6.0;

            boolean canSeal = itemCount > 0 && itemCount <= requireAmount;

            if (itemCount <=0 || itemCount > requireAmount) {
                if (!world.isClientSide()) {
                    if (blockEntity instanceof TanningBarrelBlockEntity barrel) {
                        barrel.getCustomData().putBoolean("CanSeal", canSeal);
                        barrel.setChanged();
                    }

                    if (world instanceof Level level) {
                        BlockState state1 = world.getBlockState(pos);
                        level.sendBlockUpdated(pos, state1, state1, 3);
                    }
                }
            }
        }

        if (getBlockNBTNumber(world, pos, "Fluid") <= 0) {
            if (!world.isClientSide()) {
                if (blockEntity instanceof TanningBarrelBlockEntity barrel) {
                    barrel.getCustomData().putBoolean("Water", false);
                    barrel.getCustomData().putBoolean("Lime", false);
                    barrel.getCustomData().putBoolean("Tannin", false);
                    barrel.getCustomData().putBoolean("CanSeal", false);
                    barrel.setChanged();
                }
                if (world instanceof Level level) {
                    BlockState state1 = world.getBlockState(pos);
                    level.sendBlockUpdated(pos, state1, state1, 3);
                }
            }
        }
        if (getBlockNBTNumber(world, pos, "Fluid") > 6000) {
            if (!world.isClientSide()) {
                if (blockEntity instanceof TanningBarrelBlockEntity barrel) {
                    barrel.getCustomData().putDouble("Fluid", 6000);
                    barrel.setChanged();
                }
                if (world instanceof Level level) {
                    BlockState state1 = world.getBlockState(pos);
                    level.sendBlockUpdated(pos, state1, state1, 3);
                }
            }
        }
    }

    private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof TanningBarrelBlockEntity barrel) {
            return barrel.getCustomData().getDouble(tag);
        }
        return 0;
    }

    private static boolean getBlockNBTLogic(LevelAccessor world, BlockPos pos, String tag) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof TanningBarrelBlockEntity barrel) {
            return barrel.getCustomData().getBoolean(tag);
        }
        return false;
    }

    private static ItemStack itemFromBlockInventory(LevelAccessor world, BlockPos pos, int slot) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof Container container) {
            return container.getItem(slot);
        }
        return ItemStack.EMPTY;
    }

    private static boolean getPropertyValueBoolean(BlockState state, String name) {
        for (Property<?> property : state.getProperties()) {
            if (property.getName().equals(name) && property instanceof BooleanProperty boolProp) {
                return state.getValue(boolProp);
            }
        }
        return false;
    }

    private static void updateItemNBTNumber(ItemStack stack, String tag, double value) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack, compoundTag -> compoundTag.putDouble(tag, value));
    }
}
