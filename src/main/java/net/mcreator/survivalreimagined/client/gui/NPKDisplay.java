package net.mcreator.survivalreimagined.client.gui;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class NPKDisplay {
    public static boolean execute(LevelAccessor world, Entity entity) {
        if (entity == null)
            return false;

        Vec3 eyePosition = entity.getEyePosition(1.0f);
        Vec3 lookVector = entity.getViewVector(1.0f).scale(5);
        Vec3 endVector = eyePosition.add(lookVector);

        BlockHitResult raycastResult = entity.level().clip(new ClipContext(eyePosition, endVector, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity));

        if (raycastResult.getType() != HitResult.Type.BLOCK) {
            return false;
        }

        BlockPos targetPos = raycastResult.getBlockPos();
        TagKey<Block> soilTag = TagKey.create(Registries.BLOCK, ResourceLocation.parse("c:soil"));

        return entity.level().getBlockState(targetPos).is(soilTag);
    }
}
