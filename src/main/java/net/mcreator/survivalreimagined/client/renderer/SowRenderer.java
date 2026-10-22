package net.mcreator.survivalreimagined.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import net.mcreator.survivalreimagined.client.model.Modelsow;
import net.mcreator.survivalreimagined.entity.SowEntity;

public class SowRenderer extends MobRenderer<SowEntity, Modelsow<SowEntity>> {
	private static final ResourceLocation TEXTURE =
			ResourceLocation.fromNamespaceAndPath("survival_reimagined", "textures/entities/boar.png");

	public SowRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelsow<>(context.bakeLayer(Modelsow.LAYER_LOCATION)), 0.5F);
	}

	@Override
	protected void scale(SowEntity entity, PoseStack poseStack, float partialTickTime) {
		poseStack.scale(1.1F, 1.1F, 1.1F);
	}

	@Override
	public ResourceLocation getTextureLocation(SowEntity entity) {
		return TEXTURE;
	}
}
