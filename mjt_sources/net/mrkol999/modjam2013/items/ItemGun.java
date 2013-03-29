package net.mrkol999.modjam2013.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGun extends Item //remember how all this stuff work in new forge.
{
	public ItemGun(int par1)
	{
		super(par1);
		this.setMaxStackSize(1);
		this.setCreativeTab(CreativeTabs.tabCombat); // epic combat gun is in the epic combat tab is creative
		this.setNoRepair();
		this.setUnlocalizedName("aquagun");
        this.setMaxDamage(1000); //1000 millibuckets = 1 bucket
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
        return 0; //nope, no enchants
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (par3EntityPlayer.capabilities.isCreativeMode || par1ItemStack.getItemDamage() < 1000)
        {
            par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        }

        return par1ItemStack;
    }
    
    @Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
    {
        boolean flag = par3EntityPlayer.capabilities.isCreativeMode;

        if (flag || par1ItemStack.getItemDamage() < 1000) //if gamemode is creative, or if we still have some liquid left...
        {
            par1ItemStack.damageItem(1, par3EntityPlayer);
        }
    }
}
