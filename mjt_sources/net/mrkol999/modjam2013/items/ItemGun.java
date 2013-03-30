package net.mrkol999.modjam2013.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFluid;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.liquids.IBlockLiquid;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import net.mrkol999.modjam2013.entity.EntityLiquidBullet;

public class ItemGun extends Item
{
	public ItemGun(int par1)
	{
		super(par1);
		this.setMaxStackSize(1);
		this.setCreativeTab(CreativeTabs.tabCombat); // epic combat gun is in
														// the epic combat tab
														// is creative
		this.setNoRepair();
		this.setUnlocalizedName("aquagun");
		this.setMaxDamage(1000); // 1000 millibuckets = 1 bucket
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.bow;
	}

	@Override
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		return par1ItemStack;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 36000;
	}

	@Override
	public int getItemEnchantability()
	{
		return 0; // nope, no enchants
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
        MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, true);
        Block block = null;
        if(mop != null && mop.typeOfHit == EnumMovingObjectType.TILE) block = Block.blocksList[par2World.getBlockId(mop.blockX, mop.blockY, mop.blockZ)];
		LiquidStack currls;
		try
		{
			currls = LiquidStack.loadLiquidStackFromNBT(par1ItemStack.getTagCompound().getCompoundTag("LiquidData"));
		}
		catch(Exception e)
		{
			if(block != null && (block instanceof IBlockLiquid || block instanceof BlockFluid))
			{
				if(block instanceof IBlockLiquid) currls = LiquidDictionary.getLiquid(((IBlockLiquid)block).getLiquidProperties().getName(), 1000);
				else
				{
					currls = LiquidDictionary.getLiquid(block.blockID == Block.waterStill.blockID || block.blockID == Block.waterMoving.blockID ? "Water" : "Lava", 1000);
				}
				NBTTagCompound nbt = new NBTTagCompound();
				
				currls.writeToNBT(nbt);
	
				if(par1ItemStack.getTagCompound() == null) par1ItemStack.stackTagCompound = new NBTTagCompound();
	
				par1ItemStack.getTagCompound().setTag("LiquidData", nbt);
			}
			else
			{
				currls = null;
			}
		}
		
		if(block != null && (block instanceof IBlockLiquid || block instanceof BlockFluid))
		{
			if(currls.amount < 1000)
			{
				if(block.blockID == currls.itemID)
				{
					currls.amount = 1000;
					par2World.setBlock(mop.blockX, mop.blockY, mop.blockZ, 0);
				}
				else
				if(currls.amount == 0)
				{
					if(block instanceof IBlockLiquid) currls = LiquidDictionary.getLiquid(((IBlockLiquid)block).getLiquidProperties().getName(), 1000);
					else
					{
						currls = LiquidDictionary.getLiquid(block.blockID == Block.waterStill.blockID || block.blockID == Block.waterMoving.blockID ? "Water" : "Lava", 1000);
					}
					par2World.setBlock(mop.blockX, mop.blockY, mop.blockZ, 0);
				}
				
				NBTTagCompound nbt = new NBTTagCompound();
	
				currls.writeToNBT(nbt);
	
				if(par1ItemStack.getTagCompound() == null) par1ItemStack.stackTagCompound = new NBTTagCompound();
	
				par1ItemStack.getTagCompound().setTag("LiquidData", nbt);
			}
		}
		else
		{
			if(par3EntityPlayer.capabilities.isCreativeMode || (currls != null && currls.amount > 0))
			{
				par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
			}
		}
		
		
		
		return par1ItemStack;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
	{
		if(!par2World.isRemote)
		{
			boolean flag = par3EntityPlayer.capabilities.isCreativeMode;
	
			LiquidStack currls = LiquidStack.loadLiquidStackFromNBT(par1ItemStack.getTagCompound().getCompoundTag("LiquidData"));
	
			if(flag || currls.amount > 0) // if gamemode is creative, or if we still have any liquid left...
			{
				if(!flag) currls.amount--;
				par3EntityPlayer.addChatMessage("PEW! The gun fires: " + currls.itemID + "; " + currls.amount + "; ");
				// launch da liquid missile, pew.
		        int j = this.getMaxItemUseDuration(par1ItemStack) - par4;
	            float f = (float)j / 20.0F;
	            f = (f * f + f * 2.0F) / 3.0F;

	            if ((double)f < 0.1D)
	            {
	                return;
	            }

	            if (f > 1.0F)
	            {
	                f = 1.0F;
	            }

	            LiquidStack ls = currls.copy();
	            ls.amount = 1;
	            
	            EntityLiquidBullet entityarrow = new EntityLiquidBullet(par2World, par3EntityPlayer, f * 2.0F, ls);
	            
	            if (!par2World.isRemote)
	            {
	                par2World.spawnEntityInWorld(entityarrow);
	            }
			}
	
			NBTTagCompound nbt = new NBTTagCompound();
	
			currls.writeToNBT(nbt);
	
			if(par1ItemStack.getTagCompound() == null) par1ItemStack.stackTagCompound = new NBTTagCompound();
	
			par1ItemStack.getTagCompound().setTag("LiquidData", nbt);
		}
	}
}
