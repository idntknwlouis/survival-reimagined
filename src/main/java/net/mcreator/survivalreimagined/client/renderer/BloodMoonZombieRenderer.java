package net.mcreator.survivalreimagined.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import net.mcreator.survivalreimagined.entity.BloodMoonZombieEntity;

public class BloodMoonZombieRenderer extends MobRenderer<BloodMoonZombieEntity, HumanoidModel<BloodMoonZombieEntity>> {
	private static final ResourceLocation TEXTURE =
			ResourceLocation.fromNamespaceAndPath("survival_reimagined", "textures/entities/bloodmoonzombie.png");

	public BloodMoonZombieRenderer(EntityRendererProvider.Context context) {
		super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5F);
	}

	@Override
	protected void scale(BloodMoonZombieEntity entity, PoseStack poseStack, float partialTickTime) {
		poseStack.scale(1.5F, 1.5F, 1.5F);
	}

	@Override
	public ResourceLocation getTextureLocation(BloodMoonZombieEntity entity) {
		return TEXTURE;
	}
}
