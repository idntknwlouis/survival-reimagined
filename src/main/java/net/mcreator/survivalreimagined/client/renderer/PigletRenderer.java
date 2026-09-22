package net.mcreator.survivalreimagined.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import net.mcreator.survivalreimagined.entity.PigletEntity;

public class PigletRenderer extends MobRenderer<PigletEntity, PigModel<PigletEntity>> {
	private static final ResourceLocation TEMP_TEXTURE =
			ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/pig/pig.png");

	public PigletRenderer(EntityRendererProvider.Context context) {
		super(context, new PigModel<>(context.bakeLayer(ModelLayers.PIG)), 0.25F);
	}

	@Override
	protected void scale(PigletEntity entity, PoseStack poseStack, float partialTickTime) {
		poseStack.scale(0.5F, 0.5F, 0.5F);
	}

	@Override
	public ResourceLocation getTextureLocation(PigletEntity entity) {
		return TEMP_TEXTURE;
	}
}
