package net.mrkol999.modjam2013;

import net.minecraft.item.Item;
import net.mrkol999.modjam2013.items.ItemGun;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "mrkol999_ModJamThingy2013", name = "Modjam Thingy")
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
public class ModjamThingyCore
{
	//---------------------------ITEMS----------------------------------
	//@Mod.Item(name = "Aqua Gun", typeClass = "net.mrkol999.modjam2013.items.ItemGun") //dunno why I use this, it works fine w/o it too
	public static Item itemAquaGun = new ItemGun(9001); // >9000
	//------------------------------------------------------------------
	
	@Mod.Instance
	public static ModjamThingyCore instance;
	
	@SidedProxy(clientSide = "net.mrkol999.modjam2013.ProxyClient", serverSide = "net.mrkol999.modjam2013.ProxyCommon")
	public static ProxyCommon proxy;
	
	@Mod.PreInit
	public void PreInit(FMLPreInitializationEvent e)
	{
		this.loadLocalizations();
	}
	
	@Mod.Init
	public void Init(FMLInitializationEvent e)
	{
		proxy.registerClientside(); //yaaaay
	}
	
	@Mod.PostInit
	public void PostInit(FMLPostInitializationEvent e)
	{
		
	}
	
	
	
	public void loadLocalizations()
	{
		LanguageRegistry.instance().addStringLocalization("item.aquagun.name", "en_US", "Aqua Gun");
		LanguageRegistry.instance().addStringLocalization("item.aquagun.name", "ru_RU", "Водяная Пушка");
	}
}
