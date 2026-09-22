package net.mcreator.survivalreimagined;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModParticles;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.mcreator.survivalreimagined.client.model.ModelGasMask;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModScreens;
import net.mcreator.survivalreimagined.util.RuneInfusionTooltip;
import net.mcreator.survivalreimagined.util.AAFUpgradeTooltip;
import net.mcreator.survivalreimagined.util.FoodTooltip;

public class SurvivalReimaginedModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		SurvivalReimaginedModParticles.register();
		SurvivalReimaginedModScreens.register();
		EntityModelLayerRegistry.registerModelLayer(ModelGasMask.LAYER_LOCATION, ModelGasMask::createBodyLayer);
		ArmorRenderer.register((matrices, vertexConsumers, stack, entity, slot, light, contextModel) -> {
			if (slot != EquipmentSlot.HEAD) return;
			ModelGasMask model = new ModelGasMask(
					Minecraft.getInstance().getEntityModels().bakeLayer(ModelGasMask.LAYER_LOCATION));
			model.gasMask.copyFrom(contextModel.head);
			ArmorRenderer.renderPart(
					matrices,
					vertexConsumers,
					light,
					stack,
					model,
					ResourceLocation.fromNamespaceAndPath("survival_reimagined", "textures/models/armor/gasmask_layer_1.png"));
		}, SurvivalReimaginedModItems.GAS_MASK_HELMET.get());
		BlockRenderLayerMap.INSTANCE.putBlock(SurvivalReimaginedModBlocks.URANIUM_ROD.get(), RenderType.translucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SurvivalReimaginedModBlocks.CAMPFIRE.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SurvivalReimaginedModBlocks.RYE_SEEDS.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SurvivalReimaginedModBlocks.SPELT_SEEDS.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SurvivalReimaginedModBlocks.HEMP.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SurvivalReimaginedModBlocks.WILD_RYE.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SurvivalReimaginedModBlocks.WILD_SPELT.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SurvivalReimaginedModBlocks.WILD_CARROT.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SurvivalReimaginedModBlocks.WHEAT_CROP.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SurvivalReimaginedModBlocks.WILD_WHEAT.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SurvivalReimaginedModBlocks.POTATOES.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SurvivalReimaginedModBlocks.WILD_POTATOES.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SurvivalReimaginedModBlocks.STRAWBERRY_PLANT.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SurvivalReimaginedModBlocks.RASPBERRY_PLANT.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SurvivalReimaginedModBlocks.CORN_STALK_BOTTOM.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SurvivalReimaginedModBlocks.CORN_STALK_MIDDLE.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SurvivalReimaginedModBlocks.CORN_STALK_TOP.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SurvivalReimaginedModBlocks.THIN_RADIATED_VINES.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SurvivalReimaginedModBlocks.THIN_RADIATED_VINE_BASE.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SurvivalReimaginedModBlocks.THICK_RADIATED_VINES.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SurvivalReimaginedModBlocks.THICK_RADIATED_VINES_BASE.get(), RenderType.cutout());
		ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> {
			RuneInfusionTooltip.append(stack, lines);
			AAFUpgradeTooltip.append(stack, lines);
			FoodTooltip.append(stack, lines);
		});
	}
}
