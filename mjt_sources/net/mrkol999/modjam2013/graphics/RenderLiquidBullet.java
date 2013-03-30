package net.mrkol999.modjam2013.graphics;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraftforge.liquids.IBlockLiquid;
import net.mrkol999.modjam2013.entity.EntityLiquidBullet;

public class RenderLiquidBullet extends Render
{

	@Override
	public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1)
	{
		if(entity instanceof EntityLiquidBullet && ((EntityLiquidBullet)entity).liquidStored != null)
		{
			EntityColoredSmokeFX efx = new EntityColoredSmokeFX(entity.worldObj, d0, d1, d2, 0, 0, 0, ((IBlockLiquid)Block.blocksList[((EntityLiquidBullet)entity).liquidStored.itemID]).getLiquidRGB()[0] / 255f, ((IBlockLiquid)Block.blocksList[((EntityLiquidBullet)entity).liquidStored.itemID]).getLiquidRGB()[1] / 255f, ((IBlockLiquid)Block.blocksList[((EntityLiquidBullet)entity).liquidStored.itemID]).getLiquidRGB()[2] / 255f);
			entity.worldObj.spawnEntityInWorld(efx);
		}
	}
}
