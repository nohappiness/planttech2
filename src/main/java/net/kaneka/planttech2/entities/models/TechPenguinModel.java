package net.kaneka.planttech2.entities.models;// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class TechPenguinModel<T extends Entity> extends EntityModel<T>
{
	private final ModelRenderer body;
	private final ModelRenderer feet;
	private final ModelRenderer head;
	private final ModelRenderer hands;

	public TechPenguinModel()
	{
		texWidth = 64;
		texHeight = 64;

		body = new ModelRenderer(this);
		body.setPos(0.0F, 24.0F, 0.0F);
		body.texOffs(0, 0).addBox(-4.0F, -13.0F, -3.0F, 8.0F, 11.0F, 7.0F, 0.0F, false);
		body.texOffs(30, 30).addBox(-3.0F, -12.0F, -4.0F, 6.0F, 9.0F, 1.0F, 0.0F, false);

		feet = new ModelRenderer(this);
		feet.setPos(0.0F, 24.0F, 0.0F);
		feet.texOffs(38, 18).addBox(-4.0F, -2.0F, 0.0F, 3.0F, 2.0F, 3.0F, 0.0F, false);
		feet.texOffs(30, 25).addBox(-4.0F, -1.0F, -4.0F, 3.0F, 1.0F, 4.0F, 0.0F, false);
		feet.texOffs(19, 37).addBox(1.0F, -2.0F, 0.0F, 3.0F, 2.0F, 3.0F, 0.0F, false);
		feet.texOffs(30, 6).addBox(1.0F, -1.0F, -4.0F, 3.0F, 1.0F, 4.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setPos(0.0F, 24.0F, 0.0F);
		head.texOffs(18, 32).addBox(-2.0F, -16.0F, -5.0F, 4.0F, 1.0F, 2.0F, 0.0F, false);
		head.texOffs(23, 0).addBox(-3.0F, -21.0F, -2.0F, 6.0F, 1.0F, 5.0F, 0.0F, false);
		head.texOffs(0, 18).addBox(-4.0F, -20.0F, -3.0F, 8.0F, 7.0F, 7.0F, 0.0F, false);

		hands = new ModelRenderer(this);
		hands.setPos(0.0F, 24.0F, 0.0F);
		hands.texOffs(0, 32).addBox(-6.0F, -16.0F, -1.0F, 2.0F, 7.0F, 4.0F, 0.0F, false);
		hands.texOffs(34, 11).addBox(-6.0F, -9.0F, -1.0F, 1.0F, 3.0F, 4.0F, 0.0F, false);
		hands.texOffs(26, 14).addBox(4.0F, -16.0F, -1.0F, 2.0F, 7.0F, 4.0F, 0.0F, false);
		hands.texOffs(12, 32).addBox(5.0F, -9.0F, -1.0F, 1.0F, 3.0F, 4.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		feet.render(matrixStack, buffer, packedLight, packedOverlay);
		head.render(matrixStack, buffer, packedLight, packedOverlay);
		hands.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}