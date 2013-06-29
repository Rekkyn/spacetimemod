package rekkyn.spacetime.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import rekkyn.spacetime.Spacetime;

public class TileSpacetimeChest extends TileEntity {
    public float lidAngle;
    public float prevLidAngle;
    public int numUsingPlayers;
    private int ticksSinceSync;
    
    @Override
    public void updateEntity() {
        super.updateEntity();
        
        if (++ticksSinceSync % 20 * 4 == 0) {
            worldObj.addBlockEvent(xCoord, yCoord, zCoord, Spacetime.spacetimeChest.blockID, 1, numUsingPlayers);
        }
        
        prevLidAngle = lidAngle;
        float f = 0.1F;
        double d0;
        
        if (numUsingPlayers > 0 && lidAngle == 0.0F) {
            double d1 = xCoord + 0.5D;
            d0 = zCoord + 0.5D;
            worldObj.playSoundEffect(d1, yCoord + 0.5D, d0, "random.chestopen", 0.5F,
                    worldObj.rand.nextFloat() * 0.1F + 0.9F);
        }
        
        if (numUsingPlayers == 0 && lidAngle > 0.0F || numUsingPlayers > 0 && lidAngle < 1.0F) {
            float f1 = lidAngle;
            
            if (numUsingPlayers > 0) {
                lidAngle += f;
            } else {
                lidAngle -= f;
            }
            
            if (lidAngle > 1.0F) {
                lidAngle = 1.0F;
            }
            
            float f2 = 0.5F;
            
            if (lidAngle < f2 && f1 >= f2) {
                d0 = xCoord + 0.5D;
                double d2 = zCoord + 0.5D;
                worldObj.playSoundEffect(d0, yCoord + 0.5D, d2, "random.chestclosed", 0.5F,
                        worldObj.rand.nextFloat() * 0.1F + 0.9F);
            }
            
            if (lidAngle < 0.0F) {
                lidAngle = 0.0F;
            }
        }
    }
    
    /**
     * Called when a client event is received with the event number and
     * argument, see World.sendClientEvent
     */
    @Override
    public boolean receiveClientEvent(int par1, int par2) {
        if (par1 == 1) {
            numUsingPlayers = par2;
            return true;
        } else {
            return super.receiveClientEvent(par1, par2);
        }
    }
    
    @Override
    public void invalidate() {
        this.updateContainingBlockInfo();
        super.invalidate();
    }
    
    public void openChest() {
        ++numUsingPlayers;
        worldObj.addBlockEvent(xCoord, yCoord, zCoord, Spacetime.spacetimeChest.blockID, 1, numUsingPlayers);
    }
    
    public void closeChest() {
        --numUsingPlayers;
        worldObj.addBlockEvent(xCoord, yCoord, zCoord, Spacetime.spacetimeChest.blockID, 1, numUsingPlayers);
    }
    
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
        return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this ? false : par1EntityPlayer.getDistanceSq(
                xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
    }
}
