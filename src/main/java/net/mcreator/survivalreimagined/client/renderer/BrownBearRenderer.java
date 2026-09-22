package net.mcreator.survivalreimagined.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.mcreator.survivalreimagined.client.model.Modelbear;
import net.mcreator.survivalreimagined.entity.BrownBearEntity;

public class BrownBearRenderer extends MobRenderer<BrownBearEntity, Modelbear<BrownBearEntity>> {
	private static final ResourceLocation TEXTURE =
			ResourceLocation.fromNamespaceAndPath("survival_reimagined", "textures/entities/brown_bear.png");

	public BrownBearRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelbear<>(context.bakeLayer(Modelbear.LAYER_LOCATION)), 0.5F);
	}

	@Override
	protected void scale(BrownBearEntity entity, PoseStack poseStack, float partialTickTime) {
		poseStack.scale(1.6F, 1.6F, 1.6F);
		poseStack.scale(entity.getAgeScale(), entity.getAgeScale(), entity.getAgeScale());
	}

	@Override
	public ResourceLocation getTextureLocation(BrownBearEntity entity) {
		return TEXTURE;
	}
}
