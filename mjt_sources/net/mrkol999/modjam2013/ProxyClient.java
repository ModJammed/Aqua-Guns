package net.mrkol999.modjam2013;

import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.mrkol999.modjam2013.graphics.ItemRendererGun;
import net.mrkol999.modjam2013.graphics.RenderLiquidBullet;

public class ProxyClient extends ProxyCommon
{
	public IItemRenderer itemrendererGun = new ItemRendererGun();
	public Render renderLiquidBullet = new RenderLiquidBullet();
	
	@SuppressWarnings("unchecked")
	@Override
	public void registerClientside()
	{
		super.registerClientside();
		MinecraftForgeClient.registerItemRenderer(ModjamThingyCore.itemAquaGun.itemID, itemrendererGun);
		EntityRegistry.registerGlobalEntityID(net.mrkol999.modjam2013.graphics.EntityColoredSmokeFX.class, "Liquid Bullet", EntityRegistry.findGlobalUniqueEntityId());
		RenderManager.instance.entityRenderMap.put(net.mrkol999.modjam2013.entity.EntityLiquidBullet.class, renderLiquidBullet);
	}
}
