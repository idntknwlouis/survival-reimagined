package net.mcreator.survivalreimagined.mixin;

import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;



//THIS IS AN EXPERIMENT
@Mixin(LevelRenderer.class)
public class SkyRotationMixin {
    @Unique
    private static final float AXIAL_TILT = -23.44f;
    @Unique
    private static final float MOON_TILT = 0f; //5.14f
    @Unique
    private static final double MONTH_TICKS = 29.530588 * 24000.0;
    @Unique
    private static final double MOON_ALPHA_BLEND_ANGLE = 8.0f;
    @Unique
    private MeshData sr$sunMesh;
    @Unique
    private MeshData sr$moonMesh;

    @Shadow
    @Final
    private static ResourceLocation SUN_LOCATION;

    @Shadow
    @Final
    private static ResourceLocation MOON_LOCATION;

    @Shadow
    private ClientLevel level;

    @Shadow private Frustum cullingFrustum;
    @Unique
    private Matrix4f sr$cameraMatrix;

    @Unique
    private float sr$partialTick;
    @Inject(
            method = "renderSky(Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FLnet/minecraft/client/Camera;ZLjava/lang/Runnable;)V",
            at = @At("HEAD")
    )
    private void captureSkyInputs(Matrix4f matrix4f, Matrix4f matrix4f2, float partialTick, Camera camera, boolean bl, Runnable runnable, CallbackInfo ci) {
        this.sr$cameraMatrix = new Matrix4f(matrix4f);
        this.sr$partialTick = partialTick;
    }

    @Redirect(
            method = "renderSky(Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FLnet/minecraft/client/Camera;ZLjava/lang/Runnable;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vertex/PoseStack;mulPose(Lorg/joml/Quaternionf;)V",
                    ordinal = 3
            ),
            require = 1
    )
    private void rotateCelestialBodies(PoseStack poseStack, Quaternionf rotation) {
        poseStack.mulPose(Axis.XP.rotationDegrees(AXIAL_TILT));
        poseStack.mulPose(rotation);
    }

    @Unique
    private double sr$getSunMoonSeparation() {
        double progress = this.sr$getLunarProgress();
        double angle = progress * 360.0f;
        return Math.min(angle, 360.0 - angle);
    }

    @Unique
    private boolean sr$shouldUseAlphaBlending() {
        return sr$getSunMoonSeparation() <= MOON_ALPHA_BLEND_ANGLE;
    }

    @Redirect(
            method = "renderSky(Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FLnet/minecraft/client/Camera;ZLjava/lang/Runnable;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/multiplayer/ClientLevel;getMoonPhase()I"
            ),
            require = 1
    )
    private int sr$getCustomMoonPhase(ClientLevel level) {
        double progress = this.sr$getLunarProgress();
        return ((int) Math.floor(progress * 8.0 + 0.5)) % 8;
    }

    @Unique
    private double sr$getLunarProgress() {
        double ticks = this.level.getDayTime() + this.sr$partialTick;
        double progress = ticks % MONTH_TICKS;
        if (progress < 0.0) progress += MONTH_TICKS;
        return progress / MONTH_TICKS;
    }

    @Unique
    private Matrix4f sr$getMoonMatrix() {
        float sunAngle = this.level.getTimeOfDay(this.sr$partialTick) * 360.0f;

        float lunarOffset = (float) (this.sr$getLunarProgress() * 360.0);

        float moonAngle = sunAngle + lunarOffset;

        return new Matrix4f(this.sr$cameraMatrix)
                .rotate(Axis.XP.rotationDegrees(AXIAL_TILT))
                .rotate(Axis.ZP.rotationDegrees(MOON_TILT))
                .rotate(Axis.YP.rotationDegrees(-90.0f))
                .rotate(Axis.XP.rotationDegrees(moonAngle));
    }



    @Unique
    private VertexConsumer sr$redirectMoonVertex(BufferBuilder consumer, float x, float y, float z) {
        return consumer.addVertex(sr$getMoonMatrix(), x, y + 20.0f, z);
    }

    @Redirect(
            method = "renderSky(Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FLnet/minecraft/client/Camera;ZLjava/lang/Runnable;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vertex/BufferUploader;drawWithShader(Lcom/mojang/blaze3d/vertex/MeshData;)V",
                    ordinal = 1
            ),
            require = 1
    )
    private void sr$deferSun(MeshData mesh) {
        this.sr$sunMesh = mesh;
    }
    @Redirect(
            method = "renderSky(Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FLnet/minecraft/client/Camera;ZLjava/lang/Runnable;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vertex/BufferUploader;drawWithShader(Lcom/mojang/blaze3d/vertex/MeshData;)V",
                    ordinal = 2
            ),
            require = 1
    )
    private void sr$deferMoon(MeshData mesh) {
        this.sr$moonMesh = mesh;
    }

    @Inject(
            method = "renderSky(Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FLnet/minecraft/client/Camera;ZLjava/lang/Runnable;)V",
            at = @At("RETURN")
    )
    private void sr$renderCelestialLayers(Matrix4f matrix4f, Matrix4f matrix4f2, float partialTick, Camera camera, boolean bl, Runnable runnable, CallbackInfo ci) {
        float rainAlpha = 1.0f - this.level.getRainLevel(this.sr$partialTick);
        RenderSystem.enableBlend();
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);

        if (this.sr$sunMesh != null) {
            RenderSystem.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE, SourceFactor.ONE, DestFactor.ZERO);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, SUN_LOCATION);
            RenderSystem.setShaderColor(1.0f, 1.0f,1.0f, rainAlpha);
            BufferUploader.drawWithShader(this.sr$sunMesh);
            this.sr$sunMesh = null;
        }

        if (this.sr$moonMesh != null) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, MOON_LOCATION);
            RenderSystem.setShaderColor(1.0f,1.0f,1.0f, rainAlpha);

            if (sr$shouldUseAlphaBlending()) {
                RenderSystem.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ONE_MINUS_SRC_ALPHA);
            } else {
                RenderSystem.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE, SourceFactor.ONE, DestFactor.ZERO);
            }

            BufferUploader.drawWithShader(this.sr$moonMesh);
            this.sr$moonMesh = null;
        }
        RenderSystem.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE, SourceFactor.ONE, DestFactor.ZERO);
        RenderSystem.setShaderColor(1.0f,1.0f, 1.0f,1.0f);
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
    }







    // Moon vertex 1
    @Redirect(
            method = "renderSky(Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FLnet/minecraft/client/Camera;ZLjava/lang/Runnable;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;addVertex(Lorg/joml/Matrix4f;FFF)Lcom/mojang/blaze3d/vertex/VertexConsumer;",
                    ordinal = 6
            ),
            require = 1
    )
    private VertexConsumer sr$moonVertex1(BufferBuilder consumer, Matrix4f matrix, float x, float y, float z) {
        return sr$redirectMoonVertex(consumer, x, y, z);
    }


    // Moon vertex 2
    @Redirect(
            method = "renderSky(Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FLnet/minecraft/client/Camera;ZLjava/lang/Runnable;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;addVertex(Lorg/joml/Matrix4f;FFF)Lcom/mojang/blaze3d/vertex/VertexConsumer;",
                    ordinal = 7
            ),
            require = 1
    )
    private VertexConsumer sr$moonVertex2(BufferBuilder consumer, Matrix4f matrix, float x, float y, float z) {
        return sr$redirectMoonVertex(consumer, x, y, z);
    }


    // Moon vertex 3
    @Redirect(
            method = "renderSky(Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FLnet/minecraft/client/Camera;ZLjava/lang/Runnable;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;addVertex(Lorg/joml/Matrix4f;FFF)Lcom/mojang/blaze3d/vertex/VertexConsumer;",
                    ordinal = 8
            ),
            require = 1
    )
    private VertexConsumer sr$moonVertex3(BufferBuilder consumer, Matrix4f matrix, float x, float y, float z) {
        return sr$redirectMoonVertex(consumer, x, y, z);
    }


    // Moon vertex 4
    @Redirect(
            method = "renderSky(Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FLnet/minecraft/client/Camera;ZLjava/lang/Runnable;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;addVertex(Lorg/joml/Matrix4f;FFF)Lcom/mojang/blaze3d/vertex/VertexConsumer;",
                    ordinal = 9
            ),
            require = 1
    )
    private VertexConsumer sr$moonVertex4(BufferBuilder consumer, Matrix4f matrix, float x, float y, float z) {
        return sr$redirectMoonVertex(consumer, x, y, z);
    }
}
