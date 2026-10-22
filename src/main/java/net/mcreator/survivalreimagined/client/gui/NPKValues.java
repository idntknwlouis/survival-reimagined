package net.mcreator.survivalreimagined.client.gui;

import net.mcreator.survivalreimagined.block.fertilesoil.FertilitySoilBlockEntity;
import net.mcreator.survivalreimagined.block.fertilesoil.FertilitySoilTickUpdate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class NPKValues {
    public static String execute(LevelAccessor world, Entity entity) {
        if (entity == null)
            return "";

        Vec3 eyePosition = entity.getEyePosition(1.0f);
        Vec3 lookVector = entity.getViewVector(1.0f).scale(6);
        Vec3 endVector = eyePosition.add(lookVector);

        BlockHitResult raycastResult = entity.level().clip(new ClipContext(eyePosition, endVector, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity));

        if (raycastResult.getType() != HitResult.Type.BLOCK) {
            return "N: -1, P: -1, K: -1";
        }
        BlockPos targetPos = raycastResult.getBlockPos();

        long nValue = Math.round(getBlockNBTNumber(world, targetPos, "N"));
        long pValue = Math.round(getBlockNBTNumber(world, targetPos, "P"));
        long kValue = Math.round(getBlockNBTNumber(world, targetPos, "K"));

        return "N: " + nValue + ", P: " + pValue + ", K: " + kValue;
    }

    public static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity == null) return -1;

        if (blockEntity instanceof FertilitySoilTickUpdate.CustomDataHolder holder) {
            CompoundTag nbt = holder.getPersistentData();
            if (nbt.contains(tag)) {
                return nbt.getDouble(tag);
            }
        }

        CustomData customData = blockEntity.components().get(DataComponents.BLOCK_ENTITY_DATA);
        if (customData != null) {
            CompoundTag nbt = customData.copyTag();
            if (nbt.contains(tag)) {
                return nbt.getDouble(tag);
            }
        }
        return -1;
    }
}
