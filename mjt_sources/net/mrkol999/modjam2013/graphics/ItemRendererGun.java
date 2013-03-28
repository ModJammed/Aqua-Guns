package net.mrkol999.modjam2013.graphics;

import org.lwjgl.opengl.GL11;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class ItemRendererGun implements net.minecraftforge.client.IItemRenderer
{
	public ModelGun model = new ModelGun();
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return type == ItemRenderType.ENTITY || type == ItemRenderType.EQUIPPED || type == ItemRenderType.INVENTORY;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return true; //I suppose....
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		GL11.glPushMatrix();
		{
			if(type == ItemRenderType.ENTITY) 
			{ 
				model.render((Entity) data[0], 0, 0, 0, 0, 0, 0.0625F, item); 
			} //Yay! It is! Sometimes!
			else
			{
				model.render(null, 0, 0, 0, 0, 0, 0.0625F, item); 
			}
		}
		GL11.glPopMatrix();
	}

}
