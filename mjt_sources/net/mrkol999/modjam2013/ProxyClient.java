package net.mrkol999.modjam2013;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.renderer.entity.Render;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.mrkol999.modjam2013.graphics.ItemRendererGun;
import net.mrkol999.modjam2013.graphics.RenderLiquidBullet;

public class ProxyClient extends ProxyCommon
{
	public IItemRenderer itemrendererGun = new ItemRendererGun();
	public Render renderLiquidBullet = new RenderLiquidBullet();
	
	@Override
	public void registerClientside()
	{
		super.registerClientside();
		MinecraftForgeClient.registerItemRenderer(ModjamThingyCore.itemAquaGun.itemID, itemrendererGun);
		RenderingRegistry.registerEntityRenderingHandler(net.mrkol999.modjam2013.entity.EntityLiquidBullet.class, renderLiquidBullet);
		
	}
}
