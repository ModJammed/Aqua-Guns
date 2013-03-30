package net.mrkol999.modjam2013.graphics;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.src.ModLoader;
import net.mrkol999.modjam2013.entity.EntityLiquidBullet;

public class RenderLiquidBullet extends Render
{

	@Override
	public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1)
	{
		if(entity instanceof EntityLiquidBullet) //probably some derpyness here
		{
			EntityColoredSmokeFX efx = new EntityColoredSmokeFX(entity.worldObj, d0, d1, d2, 0, 0, 0, (((EntityLiquidBullet)entity).particleRGB[0] & 0xFF) / 255F, (((EntityLiquidBullet)entity).particleRGB[1] & 0xFF) / 255F, (((EntityLiquidBullet)entity).particleRGB[2] & 0xFF) / 255F);
			ModLoader.getMinecraftInstance().effectRenderer.addEffect(efx);
		}
	}
}
