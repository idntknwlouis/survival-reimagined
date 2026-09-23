package net.mcreator.survivalreimagined.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import net.mcreator.survivalreimagined.block.entity.PalmLeavesBlockEntity;
import net.mcreator.survivalreimagined.client.model.Modelleaves;

public class PalmLeavesRenderer implements BlockEntityRenderer<PalmLeavesBlockEntity> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("survival_reimagined", "textures/block/palm_leaves.png");

    private final Modelleaves<?> model;

    public PalmLeavesRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new Modelleaves<>(context.bakeLayer(Modelleaves.LAYER_LOCATION));
    }

    @Override
    public void render(PalmLeavesBlockEntity blockEntity, float partialTick, PoseStack poseStack,
            MultiBufferSource buffers, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.translate(-0.5D, -0.5D, 0.5D);
        poseStack.translate(0.0D, -1.0D, 0.0D);

        VertexConsumer consumer = buffers.getBuffer(RenderType.entityCutout(TEXTURE));
        model.renderToBuffer(poseStack, consumer, packedLight, packedOverlay, -1);

        poseStack.popPose();
    }
}
