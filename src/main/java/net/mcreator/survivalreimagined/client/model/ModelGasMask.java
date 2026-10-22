package net.mcreator.survivalreimagined.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class ModelGasMask extends EntityModel<LivingEntity> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			ResourceLocation.fromNamespaceAndPath("survival_reimagined", "gas_mask"), "main");

	public final ModelPart gasMask;

	public ModelGasMask(ModelPart root) {
		this.gasMask = root.getChild("gas_mask");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();

		PartDefinition gasMask = root.addOrReplaceChild("gas_mask",
				CubeListBuilder.create()
						.texOffs(0, 0)
						.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 2.0F, new CubeDeformation(0.1F))
						.texOffs(5, 17)
						.addBox(3.0F, -5.0F, -2.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.1F))
						.texOffs(5, 17)
						.addBox(-4.0F, -5.0F, -2.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.1F)),
				PartPose.offset(0.0F, 24.0F, 2.0F));

		gasMask.addOrReplaceChild("string3_r1",
				CubeListBuilder.create()
						.texOffs(5, 17)
						.addBox(-1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.1F)),
				PartPose.offsetAndRotation(-2.0F, -4.0F, 3.0F, 0.0F, 1.5708F, 0.0F));

		gasMask.addOrReplaceChild("betterfilter_r1",
				CubeListBuilder.create()
						.texOffs(12, 13)
						.addBox(0.0F, 1.0F, -2.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.1F))
						.texOffs(12, 10)
						.addBox(-4.0F, 1.0F, -2.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.1F))
						.texOffs(0, 10)
						.addBox(-3.0F, 0.0F, -3.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.1F)),
				PartPose.offsetAndRotation(1.5F, -3.0F, -4.0F, 0.3927F, 0.0F, 0.0F));

		return LayerDefinition.create(mesh, 24, 24);
	}

	@Override
	public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight,
			int packedOverlay, int color) {
		gasMask.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}
}
