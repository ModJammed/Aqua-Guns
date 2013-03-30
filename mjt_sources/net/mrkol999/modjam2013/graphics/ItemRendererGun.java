package net.mrkol999.modjam2013.graphics;

import org.lwjgl.opengl.GL11;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraftforge.client.IItemRenderer;

public class ItemRendererGun implements IItemRenderer
{
	public ModelGun model = new ModelGun();
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return true;
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
				GL11.glRotatef(15F, -1f, 0f, 0f);
				GL11.glRotatef(16F, 0f, 0f, 1f);
				GL11.glRotatef(10F, 0f, 1f, 0f);
				GL11.glTranslatef(0f, 0.35f, 0f);
				GL11.glTranslatef(0.1f, 0f, 0f);
				
				if(ModLoader.getMinecraftInstance().thePlayer.inventory.getCurrentItem() == item && ModLoader.getMinecraftInstance().gameSettings.thirdPersonView == 0)
				{
					GL11.glRotatef(45f, 0f, 1f, 0f);
					if(ModLoader.getMinecraftInstance().thePlayer.getItemInUseCount()> 0)
					{
						GL11.glRotatef(45f, 0f, 1f, 0f);
						GL11.glRotatef(30f, 0f, 0f, -1f);
					}
				}
			}
			
			if(type == ItemRenderType.FIRST_PERSON_MAP)
			{
				GL11.glRotatef(90f, 0f, 1f, 0f);
			}
			
			if(type == ItemRenderType.ENTITY)
			
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
