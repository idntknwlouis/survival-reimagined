package net.mcreator.survivalreimagined.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


//THIS IS AN EXPERIMENT, IT IS DISABLED BY DEFAULT
@Mixin(LevelRenderer.class)
public class SkyRotationMixin {
    private static final float AXIAL_TILT = 23.44f;
    private static final float SKY_ROTATION = 30.0f;
    private static final String SET_UV = "Lcom/mojang/blaze3d/vertex/VertexConsumer;setUv(FF)Lcom/mojang/blaze3d/vertex/VertexConsumer;";

    @Redirect(
            method = "renderSky(Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FLnet/minecraft/client/Camera;ZLjava/lang/Runnable;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vertex/PoseStack;last()Lcom/mojang/blaze3d/vertex/PoseStack$Pose;",
                    ordinal = 2
            ),
            require = 1
    )
    private PoseStack.Pose rotateCelestialBodies(PoseStack poseStack) {
        poseStack.mulPose(Axis.ZP.rotationDegrees(SKY_ROTATION));
        poseStack.mulPose(Axis.XP.rotationDegrees(AXIAL_TILT));
        return poseStack.last();
    }

}
