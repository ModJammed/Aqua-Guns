package net.mrkol999.modjam2013.graphics;

import org.lwjgl.opengl.GL11;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.liquids.IBlockLiquid;
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
    	LiquidStack currls;
    	try
    	{
    		currls = LiquidStack.loadLiquidStackFromNBT(is.getTagCompound().getCompoundTag("LiquidData"));
    	}
    	catch(Exception e)
    	{
    		currls = null;
    	}
    	
		super.render(entity, f, f1, f2, f3, f4, f5);
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Pice0.render(f5);
		if(currls != null && currls.itemID != 0 && currls.amount > 0)
		{
			String tex = "";
			if(Block.blocksList[currls.itemID] instanceof IBlockLiquid) tex = ((IBlockLiquid)Block.blocksList[currls.itemID]).getLiquidBlockTextureFile();
			else tex = "/terrain.png";
			
			ModLoader.getMinecraftInstance().renderEngine.bindTexture(tex);

			Tessellator tes = Tessellator.instance;

			currls.getRenderingIcon().getInterpolatedU(0);
			double miu = currls.getRenderingIcon().getInterpolatedU(0);
			double miv = currls.getRenderingIcon().getInterpolatedV(0 + (((currls.amount * -1) + 1000) / 62.5d));
			double mau = currls.getRenderingIcon().getInterpolatedU(16);
			double mav = currls.getRenderingIcon().getInterpolatedV(16);
			double lh = currls.amount / 200F;

			GL11.glPushMatrix();
			GL11.glScalef(1f/16f, 1f/16f, 1f/16f); 
	        GL11.glDisable(GL11.GL_LIGHTING);
			// without F it scales it down to 0, 0, 0. That's because it is trying to make 0.0625 an integer, and 0.0625 without .0625 is 0.
			tes.startDrawingQuads(); // fine tessellation code
			{
				tes.addVertexWithUV(-1.5, -3, -1.5, mau, miv);
				tes.addVertexWithUV(1.5, -3, -1.5, miu, miv);
				tes.addVertexWithUV(1.5, -3 - lh, -1.5, miu, mav);
				tes.addVertexWithUV(-1.5, -3 - lh, -1.5, mau, mav);

				tes.addVertexWithUV(-1.5, -3 - lh, 1.5, miu, mav);
				tes.addVertexWithUV(-1.5, -3, 1.5, miu, miv);
				tes.addVertexWithUV(-1.5, -3, -1.5, mau, miv);
				tes.addVertexWithUV(-1.5, -3 - lh, -1.5, mau, mav);

				tes.addVertexWithUV(1.5, -3 - lh, 1.5, mau, mav);
				tes.addVertexWithUV(1.5, -3, 1.5, mau, miv);
				tes.addVertexWithUV(-1.5, -3, 1.5, miu, miv);
				tes.addVertexWithUV(-1.5, -3 - lh, 1.5, miu, mav);

				tes.addVertexWithUV(1.5, -3, -1.5, miu, miv);
				tes.addVertexWithUV(1.5, -3, 1.5, mau, miv);
				tes.addVertexWithUV(1.5, -3 - lh, 1.5, mau, mav);
				tes.addVertexWithUV(1.5, -3 - lh, -1.5, miu, mav);

				tes.addVertexWithUV(1.5, -3 - lh, 1.5, mau, mav);
				tes.addVertexWithUV(-1.5, -3 - lh, 1.5, mau, miv);
				tes.addVertexWithUV(-1.5, -3 - lh, -1.5, miu, miv);
				tes.addVertexWithUV(1.5, -3 - lh, -1.5, miu, mav);
			}
			tes.draw();
	        GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
		}
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}
}
