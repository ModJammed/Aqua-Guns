package net.mrkol999.modjam2013.graphics;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraft.client.renderer.Tessellator;

public class ModelGun extends ModelBase
{
	ModelRenderer Pice0; // The gun

	public ModelGun()
	{
		textureWidth = 68;
		textureHeight = 68;
		setTextureOffset("Pice0.Part0", 0, 22);
		setTextureOffset("Pice0.Part1", 0, 17);
		setTextureOffset("Pice0.Part2", 0, 12); // hope techne done it right
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
		if(is.getItemDamage() < 1000)
		{
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, ModLoader.getMinecraftInstance().renderEngine
					.getTexture(Item.itemsList[LiquidStack.loadLiquidStackFromNBT(is.getTagCompound().getCompoundTag(
							"LiquidData")).itemID].getSpriteNumber() == 1 ? "/gui/items.png" : "/terrain.png"));

			Tessellator tes = Tessellator.instance; //Eclipse auto formatter FTW

			int miu = (int) is.getItem().getIconIndex(is).getMinU();
			int miv = (int) is.getItem().getIconIndex(is).getMinV();
			int mau = (int) is.getItem().getIconIndex(is).getMaxU();
			int mav = (int) is.getItem().getIconIndex(is).getMaxV();
			int lh = (int) Math.floor(((is.getItemDamage() * -1) + 1000) / 5);

			tes.startDrawingQuads(); // brutal tessellation code
			{
				tes.addVertexWithUV(-1.5, -3, -1.5, mau, mav);
				tes.addVertexWithUV(1.5, -3, -1.5, mau, miv);
				tes.addVertexWithUV(-1.5, -3 - lh, -1.5, miu, mav);
				tes.addVertexWithUV(1.5, -3 - lh, -1.5, miu, miv);

				tes.addVertexWithUV(-1.5, -3, -1.5, mau, mav);
				tes.addVertexWithUV(-1.5, -3, 1.5, mau, miv);
				tes.addVertexWithUV(-1.5, -3 - lh, -1.5, miu, mav);
				tes.addVertexWithUV(-1.5, -3 - lh, 1.5, miu, miv);

				tes.addVertexWithUV(-1.5, -3, 1.5, mau, mav);
				tes.addVertexWithUV(1.5, -3, 1.5, mau, miv);
				tes.addVertexWithUV(-1.5, -3 - lh, 1.5, miu, mav);
				tes.addVertexWithUV(1.5, -3 - lh, 1.5, miu, miv);

				tes.addVertexWithUV(1.5, -3, -1.5, mau, mav);
				tes.addVertexWithUV(1.5, -3, 1.5, mau, miv);
				tes.addVertexWithUV(1.5, -3 - lh, -1.5, miu, mav);
				tes.addVertexWithUV(1.5, -3 - lh, 1.5, miu, miv);

				tes.addVertexWithUV(-1.5, -3 - lh, -1.5, mau, mav);
				tes.addVertexWithUV(-1.5, -3 - lh, 1.5, mau, miv);
				tes.addVertexWithUV(1.5, -3 - lh, -1.5, miu, mav);
				tes.addVertexWithUV(1.5, -3 - lh, 1.5, miu, miv);
			}
			tes.draw();
		}
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}
}
