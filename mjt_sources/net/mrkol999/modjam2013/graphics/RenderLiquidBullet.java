package net.mrkol999.modjam2013.graphics;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.src.ModLoader;
import net.minecraftforge.liquids.IBlockLiquid;
import net.mrkol999.modjam2013.entity.EntityLiquidBullet;

public class RenderLiquidBullet extends Render
{

	@Override
	public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1)
	{
		if(entity instanceof EntityLiquidBullet) //probably some derpyness here
		{
			try
			{
				int liid = ((EntityLiquidBullet)entity).getLiquidItemID();
				byte[] c = ((IBlockLiquid)Block.blocksList[liid]).getLiquidRGB();
				EntityColoredSmokeFX efx = new EntityColoredSmokeFX(entity.worldObj, d0, d1, d2, 0, 0, 0, c[0] / 255f, c[1] / 255f, c[2] / 255f);
				ModLoader.getMinecraftInstance().effectRenderer.addEffect(efx);
			}
			catch(Exception e)
			{
				
			}
		}
	}
}