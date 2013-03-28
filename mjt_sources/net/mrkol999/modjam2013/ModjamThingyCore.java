package net.mrkol999.modjam2013;

import net.minecraft.item.Item;
import net.mrkol999.modjam2013.items.ItemGun;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "mrkol999_ModJamThingy2013", name = "Modjam Thingy")
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
	public void PreInit()
	{
		this.loadLocalizations();
	}
	
	@Mod.Init
	public void Init()
	{
		proxy.registerClientside();
	}
	
	@Mod.PostInit
	public void PostInit()
	{
		
	}
	
	
	
	public void loadLocalizations()
	{
		LanguageRegistry.instance().addStringLocalization("item.aquagun.name", "en_US", "Aqua Gun");
		LanguageRegistry.instance().addStringLocalization("item.aquagun.name", "ru_RU", "Водяная Пушка");
	}
}
