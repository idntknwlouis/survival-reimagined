package net.mcreator.survivalreimagined.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import net.mcreator.survivalreimagined.client.model.Modelpiglet;
import net.mcreator.survivalreimagined.entity.PigletEntity;

public class PigletRenderer extends MobRenderer<PigletEntity, Modelpiglet<PigletEntity>> {
	private static final ResourceLocation TEXTURE =
			ResourceLocation.fromNamespaceAndPath("survival_reimagined", "textures/entities/piglet.png");

	public PigletRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelpiglet<>(context.bakeLayer(Modelpiglet.LAYER_LOCATION)), 0.25F);
	}

	@Override
	public ResourceLocation getTextureLocation(PigletEntity entity) {
		return TEXTURE;
	}
}
