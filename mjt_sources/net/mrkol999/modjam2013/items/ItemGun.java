package net.mrkol999.modjam2013.items;

import java.util.List;
import org.lwjgl.input.Keyboard;
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

	@SuppressWarnings({
			"rawtypes", "unchecked"
	})
	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
		{
			par3List.add("Hold right mouse button");
			par3List.add("to charge, release to fire.");
			par3List.add("Right mouse button click on");
			par3List.add("any liquid to suck it up.");
		}
		else
		{
			par3List.add("Hold shift for description.");
		}
		LiquidStack ls;
		String l = "None";
		int a = 0;
		try
		{
			ls = LiquidStack.loadLiquidStackFromNBT((NBTTagCompound) par1ItemStack.getTagCompound().getTag("LiquidData"));
			l = Block.blocksList[ls.itemID].getLocalizedName();
			a = ls.amount;
		}
		catch(Exception e)
		{
			ls = null;
		}
		par3List.add("");
		par3List.add("Current liquid: ");
		par3List.add(l);
		par3List.add("Current charge: ");
		par3List.add("" + a);
	
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
		Block block = null;
		LiquidStack currls = null;
		MovingObjectPosition mop = null;
		mop = this.getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, true);
		if(mop != null && mop.typeOfHit == EnumMovingObjectType.TILE)
			block = Block.blocksList[par2World.getBlockId(mop.blockX, mop.blockY, mop.blockZ)];

		try
		{
			currls = LiquidStack.loadLiquidStackFromNBT(par1ItemStack.getTagCompound().getCompoundTag("LiquidData"));
		}
		catch(Exception e)
		{
			if(block != null && (block instanceof IBlockLiquid || block instanceof BlockFluid))
			{
				if(block instanceof IBlockLiquid)
					currls = LiquidDictionary.getLiquid(((IBlockLiquid) block).getLiquidProperties().getName(), 0);
				else
				{
					currls = LiquidDictionary.getLiquid(block.blockID == Block.waterStill.blockID
							|| block.blockID == Block.waterMoving.blockID ? "Water" : "Lava", 0);
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
					if(!par2World.isRemote) par2World.setBlock(mop.blockX, mop.blockY, mop.blockZ, 0);
				}
				else if(currls.amount == 0)
				{
					if(block instanceof IBlockLiquid)
						currls = LiquidDictionary.getLiquid(((IBlockLiquid) block).getLiquidProperties().getName(), 1000);
					else
					{
						currls = LiquidDictionary.getLiquid(block.blockID == Block.waterStill.blockID
								|| block.blockID == Block.waterMoving.blockID ? "Water" : "Lava", 1000);
					}
					par2World.setBlock(mop.blockX, mop.blockY, mop.blockZ, 0);
				}

				NBTTagCompound nbt = new NBTTagCompound();

				currls.writeToNBT(nbt);

				if(par1ItemStack.getTagCompound() == null) par1ItemStack.stackTagCompound = new NBTTagCompound();

				par1ItemStack.getTagCompound().setTag("LiquidData", nbt);
			}
			else
			{
				if(par3EntityPlayer.capabilities.isCreativeMode || (currls != null && currls.amount > 0))
				{
					par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
				}
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

			LiquidStack currls;

			try
			{
				currls = LiquidStack.loadLiquidStackFromNBT(par1ItemStack.getTagCompound().getCompoundTag("LiquidData"));
			}
			catch(Exception e)
			{
				currls = null;
			}


			int j = this.getMaxItemUseDuration(par1ItemStack) - par4;
			float k = (j/20) + 1;
			
			if(currls != null && (flag || currls.amount - Math.floor(10*k) > 0))
			{
				float f = (float) j / 20.0F;
				f = (f * f + f * 2.0F) / 3.0F;
	
				if((double) f < 0.1D)
				{
					return;
				}
	
				if(f > 1.0F)
				{
					f = 1.0F;
				}
				if(!flag) currls.amount -= (int) Math.floor(10 * k);
				LiquidStack ls = currls.copy();
				ls.amount = (int) Math.floor(10 * k);
				EntityLiquidBullet bullet = new EntityLiquidBullet(par2World, par3EntityPlayer, f * 2.0F, ls, k);
				par2World.spawnEntityInWorld(bullet);
				par2World.playSoundEffect(par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ, "sound.pew",
						1.0F, 1.0f);
			}

			if(currls != null)
			{
				NBTTagCompound nbt = new NBTTagCompound();

				currls.writeToNBT(nbt);

				if(par1ItemStack.getTagCompound() == null) par1ItemStack.stackTagCompound = new NBTTagCompound();

				par1ItemStack.getTagCompound().setTag("LiquidData", nbt);
			}
		}
	}
}
