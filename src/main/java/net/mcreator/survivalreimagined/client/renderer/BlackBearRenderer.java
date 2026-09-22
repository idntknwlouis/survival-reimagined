package net.mcreator.survivalreimagined.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.mcreator.survivalreimagined.client.model.Modelbear;
import net.mcreator.survivalreimagined.entity.BlackBearEntity;

public class BlackBearRenderer extends MobRenderer<BlackBearEntity, Modelbear<BlackBearEntity>> {
	private static final ResourceLocation TEXTURE =
			ResourceLocation.fromNamespaceAndPath("survival_reimagined", "textures/entities/black_bear.png");

	public BlackBearRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelbear<>(context.bakeLayer(Modelbear.LAYER_LOCATION)), 0.5F);
	}

	@Override
	protected void scale(BlackBearEntity entity, PoseStack poseStack, float partialTickTime) {
		poseStack.scale(1.2F, 1.2F, 1.2F);
		poseStack.scale(entity.getAgeScale(), entity.getAgeScale(), entity.getAgeScale());
	}

	@Override
	public ResourceLocation getTextureLocation(BlackBearEntity entity) {
		return TEXTURE;
	}
}
