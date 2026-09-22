package net.mcreator.survivalreimagined.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import net.mcreator.survivalreimagined.entity.BoarEntity;

public class BoarRenderer extends MobRenderer<BoarEntity, PigModel<BoarEntity>> {
	private static final ResourceLocation TEMP_TEXTURE =
			ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/pig/pig.png");

	public BoarRenderer(EntityRendererProvider.Context context) {
		super(context, new PigModel<>(context.bakeLayer(ModelLayers.PIG)), 0.5F);
	}

	@Override
	protected void scale(BoarEntity entity, PoseStack poseStack, float partialTickTime) {
		poseStack.scale(1.25F, 1.25F, 1.25F);
	}

	@Override
	public ResourceLocation getTextureLocation(BoarEntity entity) {
		return TEMP_TEXTURE;
	}
}
