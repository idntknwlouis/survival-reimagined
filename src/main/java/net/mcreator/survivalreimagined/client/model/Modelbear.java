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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class Modelbear<T extends Entity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION =
			new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("survival_reimagined", "modelbear"), "main");
	public final ModelPart head;
	public final ModelPart body;
	public final ModelPart leg1;
	public final ModelPart leg2;
	public final ModelPart leg3;
	public final ModelPart leg4;

	public Modelbear(ModelPart root) {
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.leg1 = root.getChild("leg1");
		this.leg2 = root.getChild("leg2");
		this.leg3 = root.getChild("leg3");
		this.leg4 = root.getChild("leg4");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();
		root.addOrReplaceChild("head",
				CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -3.0F, -3.0F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
						.texOffs(0, 44).addBox(-2.5F, 1.0F, -6.0F, 5.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
						.texOffs(26, 0).addBox(2.5F, -4.0F, -1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
						.texOffs(26, 0).addBox(-4.5F, -4.0F, -1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 10.0F, -16.0F));
		root.addOrReplaceChild("body",
				CubeListBuilder.create().texOffs(0, 19).addBox(-5.0F, -13.0F, -7.0F, 14.0F, 14.0F, 11.0F, new CubeDeformation(0.0F))
						.texOffs(39, 0).addBox(-4.0F, -25.0F, -7.0F, 12.0F, 12.0F, 10.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-2.0F, 9.0F, 12.0F, 1.5708F, 0.0F, 0.0F));
		root.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(50, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 8.0F), PartPose.offset(4.5F, 14.0F, 6.0F));
		root.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(50, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 8.0F), PartPose.offset(-4.5F, 14.0F, 6.0F));
		root.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(50, 40).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 6.0F), PartPose.offset(3.5F, 14.0F, -8.0F));
		root.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(50, 40).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 6.0F), PartPose.offset(-3.5F, 14.0F, -8.0F));
		return LayerDefinition.create(mesh, 128, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		leg1.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		leg2.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		leg3.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		leg4.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		head.yRot = netHeadYaw * ((float)Math.PI / 180F);
		head.xRot = headPitch * ((float)Math.PI / 180F);
		leg1.xRot = Mth.cos(limbSwing) * limbSwingAmount;
		leg4.xRot = Mth.cos(limbSwing) * limbSwingAmount;
		leg2.xRot = -Mth.cos(limbSwing) * limbSwingAmount;
		leg3.xRot = -Mth.cos(limbSwing) * limbSwingAmount;
	}
}
