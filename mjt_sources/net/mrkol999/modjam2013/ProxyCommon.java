package net.mrkol999.modjam2013;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class ProxyCommon
{
	public void registerClientside()
	{
		
	}
	
    @ForgeSubscribe
    public void onSound(SoundLoadEvent event)
    {
        try 
        {
            event.manager.soundPoolSounds.addSound("sound/pew.ogg", ModjamThingyCore.class.getResource("mods/ModjamThingy/sounds/pew.ogg"));            
        }
        catch (Exception e)
        {
            System.err.println("Failed to register one or more sounds.");
        }
    }
}
