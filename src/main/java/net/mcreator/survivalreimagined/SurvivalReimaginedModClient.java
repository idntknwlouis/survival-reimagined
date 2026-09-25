package net.mcreator.survivalreimagined;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.mcreator.survivalreimagined.network.PlayerVariables;
import net.mcreator.survivalreimagined.network.PlayerVariablesSyncMessage;
import net.mcreator.survivalreimagined.network.SavedDataSyncMessage;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModParticles;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.mcreator.survivalreimagined.client.model.ModelGasMask;
import net.mcreator.survivalreimagined.client.model.Modelleaves;
import net.mcreator.survivalreimagined.client.renderer.block.PalmLeavesRenderer;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlockEntities;
import net.mcreator.survivalreimagined.client.renderer.BoarRenderer;
import net.mcreator.survivalreimagined.client.renderer.SowRenderer;
import net.mcreator.survivalreimagined.client.renderer.PigletRenderer;
import net.mcreator.survivalreimagined.client.renderer.BlackBearRenderer;
import net.mcreator.survivalreimagined.client.renderer.BrownBearRenderer;
import net.mcreator.survivalreimagined.client.renderer.CrimsonArachnidRenderer;
import net.mcreator.survivalreimagined.client.renderer.AlphaCrimsonArachnidRenderer;
import net.mcreator.survivalreimagined.client.renderer.GhostRenderer;
import net.mcreator.survivalreimagined.client.renderer.BloodMoonZombieRenderer;
import net.mcreator.survivalreimagined.client.model.Modelbear;
import net.mcreator.survivalreimagined.client.model.Modelboar;
import net.mcreator.survivalreimagined.client.model.Modelsow;
import net.mcreator.survivalreimagined.client.model.Modelpiglet;
import net.mcreator.survivalreimagined.client.model.Modelghost;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModEntities;
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
		EntityRendererRegistry.register(SurvivalReimaginedModEntities.BOAR.get(), BoarRenderer::new);
		EntityRendererRegistry.register(SurvivalReimaginedModEntities.SOW.get(), SowRenderer::new);
		EntityRendererRegistry.register(SurvivalReimaginedModEntities.PIGLET.get(), PigletRenderer::new);
		EntityRendererRegistry.register(SurvivalReimaginedModEntities.BLACK_BEAR.get(), BlackBearRenderer::new);
		EntityRendererRegistry.register(SurvivalReimaginedModEntities.BROWN_BEAR.get(), BrownBearRenderer::new);
		EntityRendererRegistry.register(SurvivalReimaginedModEntities.CRIMSON_ARACHNID.get(), CrimsonArachnidRenderer::new);
		EntityRendererRegistry.register(SurvivalReimaginedModEntities.ALPHA_CRIMSON_ARACHNID.get(), AlphaCrimsonArachnidRenderer::new);
		EntityRendererRegistry.register(SurvivalReimaginedModEntities.GHOST.get(), GhostRenderer::new);
		EntityRendererRegistry.register(SurvivalReimaginedModEntities.BLOOD_MOON_ZOMBIE.get(), BloodMoonZombieRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(Modelbear.LAYER_LOCATION, Modelbear::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(Modelboar.LAYER_LOCATION, Modelboar::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(Modelsow.LAYER_LOCATION, Modelsow::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(Modelpiglet.LAYER_LOCATION, Modelpiglet::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(Modelghost.LAYER_LOCATION, Modelghost::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(ModelGasMask.LAYER_LOCATION, ModelGasMask::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(Modelleaves.LAYER_LOCATION, Modelleaves::createBodyLayer);
		BlockEntityRenderers.register(SurvivalReimaginedModBlockEntities.PALM_LEAVES.get(), PalmLeavesRenderer::new);
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
		BlockRenderLayerMap.INSTANCE.putBlock(SurvivalReimaginedModBlocks.SMALL_PALM_LOG.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SurvivalReimaginedModBlocks.PALM_CROWN.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SurvivalReimaginedModBlocks.PALM_LEAVES.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SurvivalReimaginedModBlocks.ALOE_VERA.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SurvivalReimaginedModBlocks.RADIATED_TALL_GRASS.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SurvivalReimaginedModBlocks.RADIATED_ORCHID.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SurvivalReimaginedModBlocks.WISTERIA_SPIDER_LILY.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SurvivalReimaginedModBlocks.WISTERIA_SAPLING.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SurvivalReimaginedModBlocks.WISTERIA_LEAF_LITTER.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SurvivalReimaginedModBlocks.WISTERIA_FLOWER_UPPER.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SurvivalReimaginedModBlocks.WISTERIA_FLOWER_LOWER.get(), RenderType.cutout());
		ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> {
			RuneInfusionTooltip.append(stack, lines);
			AAFUpgradeTooltip.append(stack, lines);
			FoodTooltip.append(stack, lines);
		});

		ClientPlayNetworking.registerGlobalReceiver(PlayerVariablesSyncMessage.TYPE, (payload, context) -> {
			context.client().execute(() -> {});
		});
		ClientPlayNetworking.registerGlobalReceiver(SavedDataSyncMessage.TYPE, (payload, context) -> {
			context.client().execute(() -> {});
		});
	}
}
