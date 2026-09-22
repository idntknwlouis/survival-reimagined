package net.mcreator.survivalreimagined.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

import net.mcreator.survivalreimagined.client.model.Modelghost;
import net.mcreator.survivalreimagined.entity.GhostEntity;

public class GhostRenderer extends MobRenderer<GhostEntity, Modelghost<GhostEntity>> {
	private static final ResourceLocation BASE =
			ResourceLocation.fromNamespaceAndPath("survival_reimagined", "textures/entities/guy_alpha.png");
	private static final ResourceLocation OVERLAY =
			ResourceLocation.fromNamespaceAndPath("survival_reimagined", "textures/entities/guy_1.png");

	public GhostRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelghost<>(context.bakeLayer(Modelghost.LAYER_LOCATION)), 0.1F);
		this.addLayer(new RenderLayer<>(this) {
			@Override
			public void render(PoseStack poseStack, MultiBufferSource bufferSource, int light, GhostEntity entity,
					float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
					float netHeadYaw, float headPitch) {
				VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityTranslucentCull(OVERLAY));
				getParentModel().renderToBuffer(
						poseStack, consumer, light, LivingEntityRenderer.getOverlayCoords(entity, 0.0F));
			}
		});
	}

	@Override
	public ResourceLocation getTextureLocation(GhostEntity entity) {
		return BASE;
	}
}
