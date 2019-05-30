package net.kaneka.planttech2.armormodels;

import net.minecraft.client.renderer.entity.model.ModelBiped;
import net.minecraft.client.renderer.entity.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelPlayer - Either Mojang or a mod author
 * Created using Tabula 7.0.1
 */
public class TestModel extends ModelBiped {
    public ModelRenderer field_178730_v;
    public ModelRenderer shape15;
    public ModelRenderer shape15_1;

    public TestModel() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.shape15 = new ModelRenderer(this, 0, 0);
        this.shape15.setRotationPoint(5.0F, 0.0F, -3.0F);
        this.shape15.addBox(-1.0F, 0.0F, 0.0F, 6, 5, 6, 0.0F);
        this.field_178730_v = new ModelRenderer(this, 0, 11);
        this.field_178730_v.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.field_178730_v.addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, 0.25F);
        this.shape15_1 = new ModelRenderer(this, 0, 0);
        this.shape15_1.setRotationPoint(-10.0F, 0.0F, -3.0F);
        this.shape15_1.addBox(0.0F, 0.0F, 0.0F, 6, 5, 6, 0.0F);
        
        this.bipedBody.addChild(field_178730_v);
        this.bipedLeftArm.addChild(shape15);
        this.bipedRightArm.addChild(shape15_1);
        
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) 
    { 
        super.render(entity, f, f1, f2, f3, f4, f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
