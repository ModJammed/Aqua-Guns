package net.mrkol999.modjam2013.graphics;

import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.world.World;


public class EntityColoredSmokeFX extends EntitySmokeFX
{

	public EntityColoredSmokeFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, float pred, float pgreen, float pblue)
	{
		super(par1World, par2, par4, par6, par8, par10, par12);

		this.particleRed = pred;
		this.particleGreen = pgreen;
		this.particleBlue = pblue;
	}

}
