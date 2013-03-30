package net.mrkol999.modjam2013.entity;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet70GameEvent;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.liquids.LiquidStack;

public class EntityLiquidBullet extends Entity implements IProjectile
{
	private boolean inGround = false;
	public Entity shootingEntity;
	public LiquidStack liquidStored;
	private int ticksInAir = 0;
	private double damage = 0;
	private int knockbackStrength;

	public EntityLiquidBullet(World par1World, LiquidStack ls)
	{
		super(par1World);
		this.renderDistanceWeight = 10.0D;
		this.setSize(0.5F, 0.5F);
		this.liquidStored = ls;
	}

	public EntityLiquidBullet(World par1World, double par2, double par4, double par6, LiquidStack ls)
	{
		super(par1World);
		this.renderDistanceWeight = 10.0D;
		this.setSize(0.5F, 0.5F);
		this.setPosition(par2, par4, par6);
		this.yOffset = 0.0F;
		this.liquidStored = ls;
	}

	public EntityLiquidBullet(World par1World, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving, float par4,
			float par5, LiquidStack ls)
	{
		super(par1World);
		this.renderDistanceWeight = 10.0D;
		this.shootingEntity = par2EntityLiving;
		this.liquidStored = ls;

		this.posY = par2EntityLiving.posY + (double) par2EntityLiving.getEyeHeight() - 0.10000000149011612D;
		double d0 = par3EntityLiving.posX - par2EntityLiving.posX;
		double d1 = par3EntityLiving.boundingBox.minY + (double) (par3EntityLiving.height / 3.0F) - this.posY;
		double d2 = par3EntityLiving.posZ - par2EntityLiving.posZ;
		double d3 = (double) MathHelper.sqrt_double(d0 * d0 + d2 * d2);

		if(d3 >= 1.0E-7D)
		{
			float f2 = (float) (Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
			float f3 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
			double d4 = d0 / d3;
			double d5 = d2 / d3;
			this.setLocationAndAngles(par2EntityLiving.posX + d4, this.posY, par2EntityLiving.posZ + d5, f2, f3);
			this.yOffset = 0.0F;
			float f4 = (float) d3 * 0.2F;
			this.setThrowableHeading(d0, d1 + (double) f4, d2, par4, par5);
		}
	}

	public EntityLiquidBullet(World par1World, EntityLiving par2EntityLiving, float par3, LiquidStack ls)
	{
		super(par1World);
		this.renderDistanceWeight = 10.0D;
		this.shootingEntity = par2EntityLiving;
		this.liquidStored = ls;

		this.setSize(0.5F, 0.5F);
		this.setLocationAndAngles(par2EntityLiving.posX, par2EntityLiving.posY + (double) par2EntityLiving.getEyeHeight(),
				par2EntityLiving.posZ, par2EntityLiving.rotationYaw, par2EntityLiving.rotationPitch);
		this.posX -= (double) (MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
		this.posY -= 0.10000000149011612D;
		this.posZ -= (double) (MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.yOffset = 0.0F;
		this.motionX = (double) (-MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper
				.cos(this.rotationPitch / 180.0F * (float) Math.PI));
		this.motionZ = (double) (MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper
				.cos(this.rotationPitch / 180.0F * (float) Math.PI));
		this.motionY = (double) (-MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI));
		this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, par3 * 1.5F, 1.0F);
	}

	protected void entityInit()
	{
		// Data watcher? Aint nobody got time for it
	}

	public void setThrowableHeading(double par1, double par3, double par5, float par7, float par8)
	{
		float f2 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
		par1 /= (double) f2;
		par3 /= (double) f2;
		par5 /= (double) f2;
		par1 += this.rand.nextGaussian() * (double) (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double) par8;
		par3 += this.rand.nextGaussian() * (double) (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double) par8;
		par5 += this.rand.nextGaussian() * (double) (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double) par8;
		par1 *= (double) par7;
		par3 *= (double) par7;
		par5 *= (double) par7;
		this.motionX = par1;
		this.motionY = par3;
		this.motionZ = par5;
		float f3 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
		this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(par1, par5) * 180.0D / Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(par3, (double) f3) * 180.0D / Math.PI);
	}

	public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
	{
		this.setPosition(par1, par3, par5);
		this.setRotation(par7, par8);
	}

	public void setVelocity(double par1, double par3, double par5)
	{
		this.motionX = par1;
		this.motionY = par3;
		this.motionZ = par5;

		if(this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
		{
			float f = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
			this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(par1, par5) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(par3, (double) f) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch;
			this.prevRotationYaw = this.rotationYaw;
			this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
		}
	}

	public void onUpdate()
	{
		super.onUpdate();
		
		switch(this.liquidStored.itemID)
		{
			case 8:
			case 9:
				this.damage = 0.5D;
				break;

			case 10:
			case 11:
				this.damage = 2.0D;
				break;
		}

		if(this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
		{
			float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(this.motionY, (double) f) * 180.0D / Math.PI);
		}

		if(this.inGround)
		{
			this.setDead(); // we die as soon, as we hit a block's bounding box
		}
		else
		{
			++this.ticksInAir;
			Vec3 vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
			Vec3 vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY,
					this.posZ + this.motionZ);
			MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks_do_do(vec3, vec31, false, true);
			vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
			vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY,
					this.posZ + this.motionZ);

			if(movingobjectposition != null)
			{
				vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(movingobjectposition.hitVec.xCoord,
						movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
			}

			Entity entity = null;
			@SuppressWarnings("rawtypes")
			List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this,
					this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
			double d0 = 0.0D;
			int l;
			float f1;

			for(l = 0; l < list.size(); ++l)
			{
				Entity entity1 = (Entity) list.get(l);

				if(entity1.canBeCollidedWith() && (entity1 != this.shootingEntity || this.ticksInAir >= 5))
				{
					f1 = 0.3F;
					AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand((double) f1, (double) f1, (double) f1);
					MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec3, vec31);

					if(movingobjectposition1 != null)
					{
						double d1 = vec3.distanceTo(movingobjectposition1.hitVec);

						if(d1 < d0 || d0 == 0.0D)
						{
							entity = entity1;
							d0 = d1;
						}
					}
				}
			}

			if(entity != null)
			{
				movingobjectposition = new MovingObjectPosition(entity);
			}

			if(movingobjectposition != null && movingobjectposition.entityHit != null
					&& movingobjectposition.entityHit instanceof EntityPlayer)
			{
				EntityPlayer entityplayer = (EntityPlayer) movingobjectposition.entityHit;

				if(entityplayer.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer
						&& !((EntityPlayer) this.shootingEntity).func_96122_a(entityplayer))
				{
					movingobjectposition = null;
				}
			}

			float f2;
			float f3;

			if(movingobjectposition != null)
			{
				if(movingobjectposition.entityHit != null)
				{
					f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ
							* this.motionZ);
					int i1 = MathHelper.ceiling_double_int((double) f2 * this.damage);

					DamageSource damagesource = null;

					if(this.shootingEntity == null)
					{
						damagesource = DamageSource.causeThrownDamage(this, this);
					}
					else
					{
						damagesource = DamageSource.causeThrownDamage(this, this.shootingEntity);
					}

					if(movingobjectposition.entityHit.attackEntityFrom(damagesource, i1))
					{
						if(movingobjectposition.entityHit instanceof EntityLiving)
						{
							EntityLiving entityliving = (EntityLiving) movingobjectposition.entityHit;

							if(!this.worldObj.isRemote)
							{
								entityliving.setArrowCountInEntity(entityliving.getArrowCountInEntity() + 1);
							}

							if(this.knockbackStrength > 0)
							{
								f3 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);

								if(f3 > 0.0F)
								{
									movingobjectposition.entityHit.addVelocity(this.motionX * (double) this.knockbackStrength
											* 0.6000000238418579D / (double) f3, 0.1D, this.motionZ
											* (double) this.knockbackStrength * 0.6000000238418579D / (double) f3);
								}
							}

							if(this.shootingEntity != null && movingobjectposition.entityHit != this.shootingEntity
									&& movingobjectposition.entityHit instanceof EntityPlayer
									&& this.shootingEntity instanceof EntityPlayerMP)
							{
								((EntityPlayerMP) this.shootingEntity).playerNetServerHandler
										.sendPacketToPlayer(new Packet70GameEvent(6, 0));
							}
						}

						this.setDead();

						switch(this.liquidStored.itemID)
						// liquid type switch. If we shoot lava, make entity
						// burn
						{
							case 9:
							case 8:
								movingobjectposition.entityHit.extinguish();
								break;

							case 10:
							case 11:
								movingobjectposition.entityHit.setFire(5);
								break;
						}
					}
					else
					{
						this.motionX *= -0.10000000149011612D;
						this.motionY *= -0.10000000149011612D;
						this.motionZ *= -0.10000000149011612D;
						this.rotationYaw += 180.0F;
						this.prevRotationYaw += 180.0F;
						this.ticksInAir = 0;
					}
				}
				else
				{
					this.inGround = true;
					int bid = this.worldObj.getBlockId(movingobjectposition.blockX, movingobjectposition.blockY,
							movingobjectposition.blockZ);
					switch(this.liquidStored.itemID)
					// no forge liquid dictionary liquids will have effects :C
					{
						case 9:
						case 8:
							if(this.worldObj.getBlockId(movingobjectposition.blockX, movingobjectposition.blockY + 1,	movingobjectposition.blockZ) == Block.fire.blockID)
							{
								this.worldObj.setBlock(movingobjectposition.blockX, movingobjectposition.blockY + 1,
										movingobjectposition.blockZ, 0);
							}							
							break;

						case 10:
						case 11:
							if(bid == Block.sand.blockID)
							{
								this.worldObj.setBlock(movingobjectposition.blockX, movingobjectposition.blockY,
										movingobjectposition.blockZ, Block.glass.blockID);
							}
							if(this.worldObj.getBlockId(movingobjectposition.blockX, movingobjectposition.blockY + 1,	movingobjectposition.blockZ) == 0)
							{
								this.worldObj.setBlock(movingobjectposition.blockX, movingobjectposition.blockY + 1, movingobjectposition.blockZ, Block.fire.blockID);
							}
							break;
					}
				}
			}

			this.posX += this.motionX;
			this.posY += this.motionY;
			this.posZ += this.motionZ;
			f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

			for(this.rotationPitch = (float) (Math.atan2(this.motionY, (double) f2) * 180.0D / Math.PI); this.rotationPitch
					- this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
			{
				;
			}

			while(this.rotationPitch - this.prevRotationPitch >= 180.0F)
			{
				this.prevRotationPitch += 360.0F;
			}

			while(this.rotationYaw - this.prevRotationYaw < -180.0F)
			{
				this.prevRotationYaw -= 360.0F;
			}

			while(this.rotationYaw - this.prevRotationYaw >= 180.0F)
			{
				this.prevRotationYaw += 360.0F;
			}

			this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
			this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
			float f4 = 0.99F;
			f1 = 0.05F;

			if(this.isInWater())
			{
				for(int j1 = 0; j1 < 4; ++j1)
				{
					f3 = 0.25F;
					this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double) f3, this.posY - this.motionY
							* (double) f3, this.posZ - this.motionZ * (double) f3, this.motionX, this.motionY, this.motionZ);
				}

				f4 = 0.8F;
			}

			this.motionX *= (double) f4;
			this.motionY *= (double) f4;
			this.motionZ *= (double) f4;
			this.motionY -= (double) f1;
			this.setPosition(this.posX, this.posY, this.posZ);
			this.doBlockCollisions();
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		par1NBTTagCompound.setByte("inGround", (byte) (this.inGround ? 1 : 0));
		par1NBTTagCompound.setDouble("damage", this.damage);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		this.inGround = par1NBTTagCompound.getByte("inGround") == 1;

		if(par1NBTTagCompound.hasKey("damage"))
		{
			this.damage = par1NBTTagCompound.getDouble("damage");
		}
	}

	protected boolean canTriggerWalking()
	{
		return false;
	}

	public float getShadowSize()
	{
		return 0.0F;
	}

	public void setDamage(double par1)
	{
		this.damage = par1;
	}

	public double getDamage()
	{
		return this.damage;
	}

	/**
	 * Sets the amount of knockback the arrow applies when it hits a mob.
	 */
	public void setKnockbackStrength(int par1)
	{
		this.knockbackStrength = par1;
	}

	public boolean canAttackWithItem()
	{
		return false;
	}
}
