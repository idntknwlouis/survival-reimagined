package net.mcreator.survivalreimagined.procedures;

import net.mcreator.survivalreimagined.block.DryingRack;
import net.mcreator.survivalreimagined.block.entity.DryingRackBlockEntity;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;


public class DryingRackInventoryHandler {
    public static void execute(Level level, double x, double y, double z) {
        if (level.isClientSide()) {
            return;
        }

        BlockPos pos = BlockPos.containing(x,y, z);
        BlockEntity blockEntity = level.getBlockEntity(pos);

        if (!(blockEntity instanceof DryingRackBlockEntity rack)) {
            return;
        }

        for (int slot = 0; slot < 2; slot++) {
            ItemStack stack = rack.getItem(slot);

            if (stack.isEmpty()) {
                rack.setDryClock(slot, 0);
                continue;
            }

            double clock = rack.getDryClock(slot) + 1.0;

            if (clock >= 20.0 * stack.getCount()) {
                clock = 0;

                if (stack.is(SurvivalReimaginedModItems.HIDE.get())) {
                    CompoundTag customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
                    double drying = customData.getDouble("Drying") + 1.0;

                    if (drying < 200.0) {
                        customData.putDouble("Drying", drying);
                        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(customData));
                    } else {
                        ItemStack driedStack = new ItemStack(SurvivalReimaginedModItems.DRIED_HIDE.get());
                        driedStack.setCount(stack.getCount());

                        rack.setItem(slot, driedStack);
                        rack.setChanged();
                    }
                }
            }
            rack.setDryClock(slot, clock);
        }
        boolean slot0HasHide = rack.getItem(0).is(SurvivalReimaginedModItems.HIDE.get()) || rack.getItem(0).is(SurvivalReimaginedModItems.DRIED_HIDE.get());
        boolean slot1HasHide = rack.getItem(1).is(SurvivalReimaginedModItems.HIDE.get()) || rack.getItem(1).is(SurvivalReimaginedModItems.DRIED_HIDE.get());

        int occupied = 0;

        if (slot0HasHide && slot1HasHide) {
            occupied = 3;
        } else if (slot0HasHide) {
            occupied  = 1;
        } else if (slot1HasHide) {
            occupied = 2;
        }

        BlockState state = level.getBlockState(pos);

        if (state.getBlock() == SurvivalReimaginedModBlocks.DRYING_RACK.get()) {
            IntegerProperty slotsOccupied = DryingRack.SLOTS_OCCUPIED;

            if (state.hasProperty(slotsOccupied) && state.getValue(slotsOccupied) != occupied) {
                level.setBlock(pos, state.setValue(slotsOccupied, occupied), Block.UPDATE_CLIENTS | Block.UPDATE_NEIGHBORS);
            }
        }
    }


}
