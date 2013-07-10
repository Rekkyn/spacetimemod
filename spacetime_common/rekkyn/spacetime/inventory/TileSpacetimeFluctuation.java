package rekkyn.spacetime.inventory;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileSpacetimeFluctuation extends TileEntity {
    
    @SideOnly(Side.CLIENT)
    private long field_82137_b;
    @SideOnly(Side.CLIENT)
    private float field_82138_c;
    
    public int damage = 100;
    
    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 65536.0D;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        // TODO: make this better yo
        return AxisAlignedBB.getBoundingBox(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY,
                Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }
    
    @Override
    public void updateEntity() {
        if (damage <= 0) {
            worldObj.destroyBlock(xCoord, yCoord, zCoord, false);
        }
        if (worldObj.rand.nextInt(10) == 0) {
            damage++;
        }
        if (damage >= 100) {
            damage = 100;
        }
        
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(xCoord - 10,
                yCoord - 10, zCoord - 10, xCoord + 10, yCoord + 10, zCoord + 10));
        Entity entity;
        for (Iterator iterator = list.iterator(); iterator.hasNext(); pullEntities(entity)) {
            entity = (Entity) iterator.next();
        }
    }
    
    public void pullEntities(Entity entity) {
        double dist = entity.getDistance(xCoord, yCoord, zCoord);
        if (dist > 10) { return; }
        double xDist = xCoord + 0.5 - entity.posX;
        double yDist = yCoord + 0.5 - entity.posY;
        double zDist = zCoord + 0.5 - entity.posZ;
        double power = (10 - dist) / 150;
        double xMotion = 1 / dist * xDist * power;
        double yMotion = 1 / dist * yDist * power;
        double zMotion = 1 / dist * zDist * power;
        entity.addVelocity(xMotion, yMotion, zMotion);
    }
    
    @Override
    public void writeToNBT(NBTTagCompound tagcompound) {
        super.writeToNBT(tagcompound);
        tagcompound.setInteger("damage", damage);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound tagcompound) {
        super.readFromNBT(tagcompound);
        damage = tagcompound.getInteger("damage");
    }
    
    public void setDamage(int damage) {
        this.damage = damage;
    }
}
