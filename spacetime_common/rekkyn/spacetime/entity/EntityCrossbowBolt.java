package rekkyn.spacetime.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet70GameEvent;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import rekkyn.spacetime.handlers.SpacetimeChargeHandler;
import rekkyn.spacetime.packets.ParticlePacket;
import rekkyn.spacetime.particles.ParticleEffects;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.registry.IThrowableEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityCrossbowBolt extends EntityArrow implements IProjectile, IThrowableEntity {
    private int xTile = -1;
    private int yTile = -1;
    private int zTile = -1;
    private int inTile = 0;
    private int inData = 0;
    private boolean inGround = false;
    
    /** Seems to be some sort of timer for animating an arrow. */
    public int boltShake = 0;
    
    /** The owner of this arrow. */
    public Entity shootingEntity;
    private int ticksInGround;
    private int ticksInAir = 0;
    private int damage = 40;
    
    /** The amount of knockback an arrow applies when it hits a mob. */
    private int knockbackStrength;
    
    public EntityCrossbowBolt(World par1World) {
        super(par1World);
        renderDistanceWeight = 10.0D;
        this.setSize(0.5F, 0.5F);
    }
    
    public EntityCrossbowBolt(World world, double posX, double posY, double posZ) {
        super(world);
        renderDistanceWeight = 10.0D;
        this.setSize(0.5F, 0.5F);
        this.setPosition(posX, posY, posZ);
        yOffset = 0.0F;
    }
    
    public EntityCrossbowBolt(World world, EntityLiving shootingEntity, EntityLiving par3EntityLiving, float power,
            float par5) {
        super(world);
        renderDistanceWeight = 10.0D;
        this.shootingEntity = shootingEntity;
        
        posY = shootingEntity.posY + shootingEntity.getEyeHeight() - 0.10000000149011612D;
        double d0 = par3EntityLiving.posX - shootingEntity.posX;
        double d1 = par3EntityLiving.boundingBox.minY + par3EntityLiving.height / 3.0F - posY;
        double d2 = par3EntityLiving.posZ - shootingEntity.posZ;
        double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
        
        if (d3 >= 1.0E-7D) {
            float f2 = (float) (Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
            float f3 = (float) -(Math.atan2(d1, d3) * 180.0D / Math.PI);
            double d4 = d0 / d3;
            double d5 = d2 / d3;
            this.setLocationAndAngles(shootingEntity.posX + d4, posY, shootingEntity.posZ + d5, f2, f3);
            yOffset = 0.0F;
            float f4 = (float) d3 * 0.2F;
            this.setThrowableHeading(d0, d1 + f4, d2, power, par5);
        }
    }
    
    public EntityCrossbowBolt(World world, EntityLiving shootingEntity, float power) {
        super(world);
        renderDistanceWeight = 10.0D;
        this.shootingEntity = shootingEntity;
        
        this.setSize(0.5F, 0.5F);
        this.setLocationAndAngles(shootingEntity.posX, shootingEntity.posY + shootingEntity.getEyeHeight(),
                shootingEntity.posZ, shootingEntity.rotationYaw, shootingEntity.rotationPitch);
        posX -= MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
        posY -= 0.10000000149011612D;
        posZ -= MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
        this.setPosition(posX, posY, posZ);
        yOffset = 0.0F;
        motionX = -MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI)
                * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI);
        motionZ = MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI)
                * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI);
        motionY = -MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI);
        this.setThrowableHeading(motionX, motionY, motionZ, power * 5.0F, 1.0F);
    }
    
    @Override
    protected void entityInit() {
        dataWatcher.addObject(16, Byte.valueOf((byte) 0));
    }
    
    /**
     * Similar to setArrowHeading, it's point the throwable entity to a x, y, z
     * direction.
     */
    @Override
    public void setThrowableHeading(double x, double y, double z, float power, float par8) {
        float f2 = MathHelper.sqrt_double(x * x + y * y + z * z);
        x /= f2;
        y /= f2;
        z /= f2;
        x += rand.nextGaussian() * (rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * par8;
        y += rand.nextGaussian() * (rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * par8;
        z += rand.nextGaussian() * (rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * par8;
        x *= power;
        y *= power;
        z *= power;
        motionX = x;
        motionY = y;
        motionZ = z;
        float f3 = MathHelper.sqrt_double(x * x + z * z);
        prevRotationYaw = rotationYaw = (float) (Math.atan2(x, z) * 180.0D / Math.PI);
        prevRotationPitch = rotationPitch = (float) (Math.atan2(y, f3) * 180.0D / Math.PI);
        ticksInGround = 0;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    /**
     * Sets the position and rotation. Only difference from the other one is no bounding on the rotation. Args: posX,
     * posY, posZ, yaw, pitch
     */
    public void setPositionAndRotation2(double x, double y, double z, float par7, float par8, int par9) {
        this.setPosition(x, y, z);
        this.setRotation(par7, par8);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    /**
     * Sets the velocity to the args. Args: x, y, z
     */
    public void setVelocity(double motionX, double motionY, double motionZ) {
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        
        if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
            prevRotationYaw = rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);
            prevRotationPitch = rotationPitch = (float) (Math.atan2(motionY, f) * 180.0D / Math.PI);
            prevRotationPitch = rotationPitch;
            prevRotationYaw = rotationYaw;
            this.setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
            ticksInGround = 0;
        }
    }
    
    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate() {
        super.onEntityUpdate();
        
        if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
            prevRotationYaw = rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);
            prevRotationPitch = rotationPitch = (float) (Math.atan2(motionY, f) * 180.0D / Math.PI);
        }
        
        int i = worldObj.getBlockId(xTile, yTile, zTile);
        
        if (i > 0) {
            Block.blocksList[i].setBlockBoundsBasedOnState(worldObj, xTile, yTile, zTile);
            AxisAlignedBB axisalignedbb = Block.blocksList[i].getCollisionBoundingBoxFromPool(worldObj, xTile, yTile,
                    zTile);
            
            if (axisalignedbb != null
                    && axisalignedbb.isVecInside(worldObj.getWorldVec3Pool().getVecFromPool(posX, posY, posZ))) {
                inGround = true;
            }
        }
        
        if (boltShake > 0) {
            --boltShake;
        }
        
        if (inGround) {
            // this.setDead();
            int j = worldObj.getBlockId(xTile, yTile, zTile);
            int k = worldObj.getBlockMetadata(xTile, yTile, zTile);
            
            if (j == inTile && k == inData) {
                ++ticksInGround;
                
                if (worldObj.isRemote) {
                    if (ticksInGround == 1 && ticksExisted > 1) {
                        particleHit(posX, posY, posZ);
                    }
                }
                
                if (ticksInGround == 1200) {
                    this.setDead();
                }
            } else {
                inGround = false;
                motionX *= rand.nextFloat() * 0.2F;
                motionY *= rand.nextFloat() * 0.2F;
                motionZ *= rand.nextFloat() * 0.2F;
                ticksInGround = 0;
                ticksInAir = 0;
            }
        } else {
            ++ticksInAir;
            if (ticksInAir == 1200) {
                if (worldObj.isRemote) {
                    
                    particleHit(posX, posY, posZ);
                }
                this.setDead();
            }
            
            if (posY >= 512) {
                if (worldObj.isRemote) {
                    
                    particleHit(posX, posY, posZ);
                }
                this.setDead();
            }
            
            Vec3 vec3 = worldObj.getWorldVec3Pool().getVecFromPool(posX, posY, posZ);
            Vec3 vec31 = worldObj.getWorldVec3Pool().getVecFromPool(posX + motionX, posY + motionY, posZ + motionZ);
            MovingObjectPosition movingobjectposition = worldObj.rayTraceBlocks_do_do(vec3, vec31, false, true);
            vec3 = worldObj.getWorldVec3Pool().getVecFromPool(posX, posY, posZ);
            vec31 = worldObj.getWorldVec3Pool().getVecFromPool(posX + motionX, posY + motionY, posZ + motionZ);
            
            if (movingobjectposition != null) {
                vec31 = worldObj.getWorldVec3Pool().getVecFromPool(movingobjectposition.hitVec.xCoord,
                        movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
            }
            
            Entity entity = null;
            List list = worldObj.getEntitiesWithinAABBExcludingEntity(this,
                    boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
            double d0 = 0.0D;
            int l;
            float f1;
            
            for (l = 0; l < list.size(); ++l) {
                Entity entity1 = (Entity) list.get(l);
                
                if (entity1.canBeCollidedWith() && (entity1 != shootingEntity || ticksInAir >= 5)) {
                    f1 = 0.3F;
                    AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand(f1, f1, f1);
                    MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec3, vec31);
                    
                    if (movingobjectposition1 != null) {
                        double d1 = vec3.distanceTo(movingobjectposition1.hitVec);
                        
                        if (d1 < d0 || d0 == 0.0D) {
                            entity = entity1;
                            d0 = d1;
                        }
                    }
                }
            }
            
            if (entity != null) {
                movingobjectposition = new MovingObjectPosition(entity);
            }
            
            if (movingobjectposition != null && movingobjectposition.entityHit != null
                    && movingobjectposition.entityHit instanceof EntityPlayer) {
                EntityPlayer entityplayer = (EntityPlayer) movingobjectposition.entityHit;
                
                if (entityplayer.capabilities.disableDamage || shootingEntity instanceof EntityPlayer
                        && !((EntityPlayer) shootingEntity).func_96122_a(entityplayer)) {
                    movingobjectposition = null;
                }
            }
            
            float f2;
            float f3;
            
            if (movingobjectposition != null) {
                if (movingobjectposition.entityHit != null) {
                    
                    DamageSource damagesource = null;
                    
                    if (shootingEntity == null) {
                        damagesource = DamageSource.causeThrownDamage(this, this);
                    } else {
                        damagesource = DamageSource.causeThrownDamage(this, shootingEntity);
                    }
                    
                    if (this.isBurning() && !(movingobjectposition.entityHit instanceof EntityEnderman)) {
                        movingobjectposition.entityHit.setFire(5);
                    }
                    
                    if (movingobjectposition.entityHit.attackEntityFrom(damagesource, damage)) {
                        if (movingobjectposition.entityHit instanceof EntityLiving) {
                            EntityLiving entityliving = (EntityLiving) movingobjectposition.entityHit;
                            
                            if (!worldObj.isRemote) {
                                entityliving.setArrowCountInEntity(entityliving.getArrowCountInEntity() + 1);
                            }
                            
                            if (knockbackStrength > 0) {
                                f3 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
                                
                                if (f3 > 0.0F) {
                                    movingobjectposition.entityHit.addVelocity(motionX * knockbackStrength
                                            * 0.6000000238418579D / f3, 0.1D, motionZ * knockbackStrength
                                            * 0.6000000238418579D / f3);
                                }
                            }
                            
                            if (shootingEntity != null) {
                                EnchantmentThorns.func_92096_a(shootingEntity, entityliving, rand);
                            }
                            
                            if (shootingEntity != null && movingobjectposition.entityHit != shootingEntity
                                    && movingobjectposition.entityHit instanceof EntityPlayer
                                    && shootingEntity instanceof EntityPlayerMP) {
                                ((EntityPlayerMP) shootingEntity).playerNetServerHandler
                                        .sendPacketToPlayer(new Packet70GameEvent(6, 0));
                            }
                            
                            if (shootingEntity instanceof EntityPlayer) {
                                float distance = movingobjectposition.entityHit.getDistanceToEntity(shootingEntity);
                                float amount = MathHelper.clamp_float(((25F / 54F) * (-16F + 7F * distance)), 25, 200);
                                SpacetimeChargeHandler.addChargeToTotal((EntityPlayer) shootingEntity, (int) amount);
                            }
                        }
                        
                        this.playSound("random.bowhit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
                        
                        if (!(movingobjectposition.entityHit instanceof EntityEnderman)) {
                            double x = movingobjectposition.entityHit.posX;
                            double y = movingobjectposition.entityHit.posY;
                            double z = movingobjectposition.entityHit.posZ;
                            
                            for (int lmnop = 0; lmnop < 64; lmnop++) {
                                float xVel = (rand.nextFloat() - 0.5F) * 5;
                                float yVel = (rand.nextFloat() - 0.5F) * 5;
                                float zVel = (rand.nextFloat() - 0.5F) * 5;
                                
                                PacketDispatcher.sendPacketToAllAround(x, y, z, 64D,
                                        movingobjectposition.entityHit.worldObj.provider.dimensionId,
                                        new ParticlePacket("crossbowTrail", x, y, z, xVel, yVel, zVel).makePacket());
                            }
                            this.setDead();
                        }
                    } else {
                        motionX *= -0.10000000149011612D;
                        motionY *= -0.10000000149011612D;
                        motionZ *= -0.10000000149011612D;
                        rotationYaw += 180.0F;
                        prevRotationYaw += 180.0F;
                        ticksInAir = 0;
                    }
                } else {
                    xTile = movingobjectposition.blockX;
                    yTile = movingobjectposition.blockY;
                    zTile = movingobjectposition.blockZ;
                    inTile = worldObj.getBlockId(xTile, yTile, zTile);
                    inData = worldObj.getBlockMetadata(xTile, yTile, zTile);
                    motionX = (float) (movingobjectposition.hitVec.xCoord - posX);
                    motionY = (float) (movingobjectposition.hitVec.yCoord - posY);
                    motionZ = (float) (movingobjectposition.hitVec.zCoord - posZ);
                    f2 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
                    posX -= motionX / f2 * 0.05000000074505806D;
                    posY -= motionY / f2 * 0.05000000074505806D;
                    posZ -= motionZ / f2 * 0.05000000074505806D;
                    this.playSound("random.bowhit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
                    inGround = true;
                    boltShake = 7;
                    
                    if (inTile != 0) {
                        Block.blocksList[inTile].onEntityCollidedWithBlock(worldObj, xTile, yTile, zTile, this);
                    }
                }
                
            }
            
            posX += motionX;
            posY += motionY;
            posZ += motionZ;
            f2 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
            rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);
            
            for (rotationPitch = (float) (Math.atan2(motionY, f2) * 180.0D / Math.PI); rotationPitch
                    - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F) {
                ;
            }
            
            while (rotationPitch - prevRotationPitch >= 180.0F) {
                prevRotationPitch += 360.0F;
            }
            
            while (rotationYaw - prevRotationYaw < -180.0F) {
                prevRotationYaw -= 360.0F;
            }
            
            while (rotationYaw - prevRotationYaw >= 180.0F) {
                prevRotationYaw += 360.0F;
            }
            
            rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
            rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
            
            if (this.isInWater()) {
                for (int j1 = 0; j1 < 4; ++j1) {
                    f3 = 0.25F;
                    worldObj.spawnParticle("bubble", posX - motionX * f3, posY - motionY * f3, posZ - motionZ * f3,
                            motionX, motionY, motionZ);
                }
            }
            
            if (!worldObj.isRemote) {
                for (int lmnop = 0; lmnop < 50; lmnop++) {
                    float rand1 = (rand.nextFloat() - 0.5F) * 2;
                    
                    PacketDispatcher.sendPacketToAllAround(posX, posY, posZ, 64D, worldObj.provider.dimensionId,
                            new ParticlePacket("crossbowTrail", posX + motionX * l / 4.0D, posY + motionY * l / 4.0D,
                                    posZ + motionZ * l / 4.0D, -motionX * rand1, -motionY * rand1, -motionZ * rand1)
                                    .makePacket());
                    
                }
            }
            
            this.setPosition(posX, posY, posZ);
            this.doBlockCollisions();
        }
    }
    
    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    @Override
    public void writeEntityToNBT(NBTTagCompound tagcompound) {
        tagcompound.setShort("xTile", (short) xTile);
        tagcompound.setShort("yTile", (short) yTile);
        tagcompound.setShort("zTile", (short) zTile);
        tagcompound.setByte("inTile", (byte) inTile);
        tagcompound.setByte("inData", (byte) inData);
        tagcompound.setByte("shake", (byte) boltShake);
        tagcompound.setByte("inGround", (byte) (inGround ? 1 : 0));
    }
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readEntityFromNBT(NBTTagCompound tagcompound) {
        xTile = tagcompound.getShort("xTile");
        yTile = tagcompound.getShort("yTile");
        zTile = tagcompound.getShort("zTile");
        inTile = tagcompound.getByte("inTile") & 255;
        inData = tagcompound.getByte("inData") & 255;
        boltShake = tagcompound.getByte("shake") & 255;
        inGround = tagcompound.getByte("inGround") == 1;
    }
    
    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they
     * walk on. used for spiders and wolves to prevent them from trampling crops
     */
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public float getShadowSize() {
        return 0.0F;
    }
    
    @Override
    public double getDamage() {
        return damage;
    }
    
    /**
     * Sets the amount of knockback the arrow applies when it hits a mob.
     */
    @Override
    public void setKnockbackStrength(int knockbackStrength) {
        this.knockbackStrength = knockbackStrength;
    }
    
    @Override
    public Entity getThrower() {
        return shootingEntity;
    }
    
    @Override
    public void setThrower(Entity entity) {
        shootingEntity = entity;
    }
    
    private void particleHit(double posX, double posY, double posZ) {
        for (int lmnop = 0; lmnop < 64; lmnop++) {
            float xVel = (rand.nextFloat() - 0.5F) * 5;
            float yVel = (rand.nextFloat() - 0.5F) * 5;
            float zVel = (rand.nextFloat() - 0.5F) * 5;
            ParticleEffects.spawnParticle("crossbowTrail", posX, posY, posZ, xVel, yVel, zVel);
        }
        
    }
    
}
