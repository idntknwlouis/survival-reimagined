package net.mcreator.survivalreimagined.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import net.mcreator.survivalreimagined.client.model.Modelboar;
import net.mcreator.survivalreimagined.entity.BoarEntity;

public class BoarRenderer extends MobRenderer<BoarEntity, Modelboar<BoarEntity>> {
	private static final ResourceLocation TEXTURE =
			ResourceLocation.fromNamespaceAndPath("survival_reimagined", "textures/entities/boar.png");

	public BoarRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelboar<>(context.bakeLayer(Modelboar.LAYER_LOCATION)), 0.5F);
	}

	@Override
	protected void scale(BoarEntity entity, PoseStack poseStack, float partialTickTime) {
		poseStack.scale(1.25F, 1.25F, 1.25F);
	}

	@Override
	public ResourceLocation getTextureLocation(BoarEntity entity) {
		return TEXTURE;
	}
}
