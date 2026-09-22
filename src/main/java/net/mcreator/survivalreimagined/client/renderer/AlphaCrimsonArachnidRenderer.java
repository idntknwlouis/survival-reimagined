package net.mcreator.survivalreimagined.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.SpiderModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

import net.mcreator.survivalreimagined.entity.AlphaCrimsonArachnidEntity;

public class AlphaCrimsonArachnidRenderer extends MobRenderer<AlphaCrimsonArachnidEntity, SpiderModel<AlphaCrimsonArachnidEntity>> {
	private static final ResourceLocation TEXTURE =
			ResourceLocation.fromNamespaceAndPath("survival_reimagined", "textures/entities/bloodmoon_spider.png");
	private static final ResourceLocation EMISSION =
			ResourceLocation.fromNamespaceAndPath("survival_reimagined", "textures/entities/bloodmoon_spider_emission.png");

	public AlphaCrimsonArachnidRenderer(EntityRendererProvider.Context context) {
		super(context, new SpiderModel<>(context.bakeLayer(ModelLayers.SPIDER)), 0.5F);
		this.addLayer(new RenderLayer<>(this) {
			@Override
			public void render(PoseStack poseStack, MultiBufferSource bufferSource, int light,
					AlphaCrimsonArachnidEntity entity, float limbSwing, float limbSwingAmount,
					float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
				VertexConsumer consumer = bufferSource.getBuffer(RenderType.eyes(EMISSION));
				this.getParentModel().renderToBuffer(
						poseStack, consumer, light, LivingEntityRenderer.getOverlayCoords(entity, 0.0F));
			}
		});
	}

	@Override
	protected void scale(AlphaCrimsonArachnidEntity entity, PoseStack poseStack, float partialTickTime) {
		poseStack.scale(1.2F, 1.2F, 1.2F);
	}

	@Override
	public ResourceLocation getTextureLocation(AlphaCrimsonArachnidEntity entity) {
		return TEXTURE;
	}
}
