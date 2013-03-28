package net.mrkol999.modjam2013.graphics;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class ModelGun extends ModelBase
{
	ModelRenderer Pice0;
	
	public ModelGun()
	{
		textureWidth = 68;
		textureHeight = 68;
		setTextureOffset("Pice0.Part0", 0, 22);
		setTextureOffset("Pice0.Part1", 0, 17);
		setTextureOffset("Pice0.Part2", 0, 12); //hope techne done it right
		setTextureOffset("Pice0.Part3", 0, 27);
		setTextureOffset("Pice0.Part4", 0, 0);
		setTextureOffset("Pice0.Part5", 56, 0);
		setTextureOffset("Pice0.Part6", 0, 32);
		setTextureOffset("Pice0.Part7", 24, 0);
		
		Pice0 = new ModelRenderer(this, "Pice0");
		Pice0.setRotationPoint(0F, 0F, 0F);
		Pice0.mirror = false;
		Pice0.addBox("Part0", -15F, -3F, -2F, 30, 1, 4);
		Pice0.addBox("Part1", -15F, -2F, 2F, 30, 4, 1);
		Pice0.addBox("Part2", -15F, -2F, -3F, 30, 4, 1);
		Pice0.addBox("Part3", -15F, 2F, -2F, 30, 1, 4);
		Pice0.addBox("Part4", -3F, -3F, -3F, 6, 6, 6);
		Pice0.addBox("Part5", -1F, 3F, -1F, 2, 5, 2);
		Pice0.addBox("Part6", 14F, -3F, -3F, 1, 6, 6);
		Pice0.addBox("Part7", -2F, -9F, -2F, 4, 6, 4);
	}
	
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, ItemStack is)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Pice0.render(f5);
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
	
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}
}
