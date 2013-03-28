package net.mrkol999.modjam2013;

import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.mrkol999.modjam2013.graphics.ItemRendererGun;

public class ProxyClient extends ProxyCommon
{
	public IItemRenderer itemrendererGun = new ItemRendererGun();
	
	@Override
	public void registerClientside()
	{
		MinecraftForgeClient.registerItemRenderer(ModjamThingyCore.itemAquaGun.itemID, itemrendererGun);
	}
}
