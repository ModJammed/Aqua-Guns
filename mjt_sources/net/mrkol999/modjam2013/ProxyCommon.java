package net.mrkol999.modjam2013;

import net.mrkol999.modjam2013.entity.EntityLiquidBullet;
import cpw.mods.fml.common.registry.EntityRegistry;

public class ProxyCommon
{
	public void registerClientside()
	{
		EntityRegistry.registerGlobalEntityID(EntityLiquidBullet.class, "Liquid Bullet", EntityRegistry.findGlobalUniqueEntityId());
	}
}
