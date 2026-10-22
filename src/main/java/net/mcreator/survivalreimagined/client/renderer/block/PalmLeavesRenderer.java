package net.mcreator.survivalreimagined.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import net.mcreator.survivalreimagined.block.entity.PalmLeavesBlockEntity;
import net.mcreator.survivalreimagined.client.model.Modelleaves;

public class PalmLeavesRenderer implements BlockEntityRenderer<PalmLeavesBlockEntity> {
    private final CustomHierarchicalModel model;
    private final ResourceLocation texture;

    public PalmLeavesRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new CustomHierarchicalModel(context.bakeLayer(Modelleaves.LAYER_LOCATION));
        this.texture = ResourceLocation.fromNamespaceAndPath(
                "survival_reimagined", "textures/block/palm_leaves.png");
    }

    @Override
    public void render(PalmLeavesBlockEntity blockEntity, float partialTick, PoseStack poseStack,
            MultiBufferSource buffers, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.translate(-0.5D, -0.5D, 0.5D);
        poseStack.translate(0.0D, -1.0D, 0.0D);

        VertexConsumer consumer = buffers.getBuffer(RenderType.entityCutout(texture));
        model.setupBlockEntityAnim(blockEntity,
                blockEntity.getLevel() != null ? blockEntity.getLevel().getGameTime() + partialTick : partialTick);
        model.renderToBuffer(poseStack, consumer, packedLight, packedOverlay, -1);

        poseStack.popPose();
    }

    private static final class CustomHierarchicalModel extends Modelleaves<Entity> {
        private final ModelPart root;
        private final BlockEntityHierarchicalModel animator = new BlockEntityHierarchicalModel();

        private CustomHierarchicalModel(ModelPart root) {
            super(root);
            this.root = root;
        }

        private void setupBlockEntityAnim(PalmLeavesBlockEntity blockEntity, float ageInTicks) {
            animator.setupBlockEntityAnim();
            super.setupAnim(null, 0.0F, 0.0F, ageInTicks, 0.0F, 0.0F);
        }

        private final class BlockEntityHierarchicalModel extends HierarchicalModel<Entity> {
            @Override
            public ModelPart root() {
                return root;
            }

            @Override
            public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount,
                    float ageInTicks, float netHeadYaw, float headPitch) {
            }

            private void setupBlockEntityAnim() {
                root().getAllParts().forEach(ModelPart::resetPose);
            }
        }
    }
}
