package net.mcreator.survivalreimagined.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.entity.BoatEntity;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;
import org.joml.Quaternionf;

public class BoatRenderer extends EntityRenderer<BoatEntity> {
    private final BoatModel boatModel;
    private final ResourceLocation texture;

    public BoatRenderer(EntityRendererProvider.Context context, String texturePath) {
        super(context);
        this.shadowRadius = 0.8f;
        this.boatModel = new BoatModel(context.bakeLayer(ModelLayers.createBoatModelName(Boat.Type.OAK)));
        this.texture = ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, texturePath);
    }

    @Override
    public void render(BoatEntity boat, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0D, 0.375D, 0.0D);
        poseStack.mulPose(new Quaternionf().setAngleAxis((-90.0f - entityYaw) * ((float)Math.PI / 180F), 0.0f, 1.0f, 0.0f));


        float hurtTicks = (float)boat.getHurtTime() - partialTicks;
        float damageWobble = boat.getDamage() - partialTicks;

        if (damageWobble < 0.0f) {
            damageWobble = 0.0f;
        }

        if (hurtTicks > 0.0f) {
            poseStack.mulPose(new Quaternionf().setAngleAxis(Mth.sin(hurtTicks) * hurtTicks * damageWobble / 10.0f * (float)boat.getHurtDir() * ((float)Math.PI / 180f), 1.0f, 0.0f, 0.0f));
        }

        float bubbleWobble = boat.getBubbleAngle(partialTicks);
        if (!Mth.equal(bubbleWobble, 0.0f)) {
            poseStack.mulPose(new Quaternionf().setAngleAxis(boat.getBubbleAngle(partialTicks) * ((float)Math.PI / 180f), 1.0f, 0.0f, 1.0f));
        }

        poseStack.scale(-1.0f, -1.0f, 1.0f);
        poseStack.mulPose(new Quaternionf().setAngleAxis((float)Math.PI, 0.0f, 1.0f, 0.0f));

        this.boatModel.setupAnim(boat, partialTicks, 0.0f, -0.1f, 0.0f, 0.0f);
        VertexConsumer vertexConsumer = buffer.getBuffer(this.boatModel.renderType(this.getTextureLocation(boat)));
        this.boatModel.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);

        if (!boat.isUnderWater()) {
            VertexConsumer waterConsumer = buffer.getBuffer(RenderType.waterMask());
            this.boatModel.waterPatch().render(poseStack, waterConsumer, packedLight, OverlayTexture.NO_OVERLAY);
        }

        poseStack.popPose();
        super.render(boat, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(BoatEntity entity) {
        return this.texture;
    }
}
