package net.mrkol999.modjam2013.graphics;

import org.lwjgl.opengl.GL11;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;

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
		return type != ItemRenderType.EQUIPPED; //I suppose....
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		GL11.glPushMatrix();
		{
			if(type != ItemRenderType.EQUIPPED && type != ItemRenderType.FIRST_PERSON_MAP) 
			{
				GL11.glScalef(0.6F, 0.6F, 0.6F);
			}
			
			if(type == ItemRenderType.EQUIPPED)
			{
				GL11.glRotatef(90, 0, 1f, 0);
				GL11.glTranslatef(0f, 0f, 0.4f);
				GL11.glRotatef(15.5F, -1f, 0f, 0f);
			}
			
			if(type == ItemRenderType.ENTITY)
			{
				GL11.glTranslatef(0.0F, 0.25F, 0.0F);
			}
			
			GL11.glRotatef(180F, 1F, 0F, 0F);
			
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, ModLoader.getMinecraftInstance().renderEngine.getTexture("/mods/ModjamThingy/textures/models/Gun.png"));
			
			if(type == ItemRenderType.ENTITY) 
			{ 
				model.render((Entity) data[1], 0, 0, 0, 0, 0, 0.0625F, item); 
			}
			else
			{
				model.render(null, 0, 0, 0, 0, 0, 0.0625F, item); 
			}
		}
		GL11.glPopMatrix();
	}

}
