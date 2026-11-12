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

import net.mcreator.survivalreimagined.block.entity.BananaLeavesBlockEntity;
import net.mcreator.survivalreimagined.client.model.ModelCustomModel;

public class BananaLeavesRenderer implements BlockEntityRenderer<BananaLeavesBlockEntity> {
    private final CustomHierarchicalModel model;
    private final ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(
            "survival_reimagined", "textures/block/bannana_leaf.png");

    public BananaLeavesRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new CustomHierarchicalModel(context.bakeLayer(ModelCustomModel.LAYER_LOCATION));
    }

    @Override
    public void render(BananaLeavesBlockEntity blockEntity, float partialTick, PoseStack poseStack,
            MultiBufferSource buffers, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.translate(-0.5D, -0.5D, 0.5D);
        poseStack.translate(0.0D, -1.0D, 0.0D);

        VertexConsumer consumer = buffers.getBuffer(RenderType.entityCutout(texture));
        float age = blockEntity.getLevel() != null
                ? blockEntity.getLevel().getGameTime() + partialTick
                : partialTick;
        model.setupBlockEntityAnim(age);
        model.renderToBuffer(poseStack, consumer, packedLight, packedOverlay, -1);

        poseStack.popPose();
    }

    private static final class CustomHierarchicalModel extends ModelCustomModel<Entity> {
        private final ModelPart root;
        private final BlockEntityHierarchicalModel animator = new BlockEntityHierarchicalModel();

        private CustomHierarchicalModel(ModelPart root) {
            super(root);
            this.root = root;
        }

        private void setupBlockEntityAnim(float ageInTicks) {
            animator.resetPose();
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

            private void resetPose() {
                root().getAllParts().forEach(ModelPart::resetPose);
            }
        }
    }
}
