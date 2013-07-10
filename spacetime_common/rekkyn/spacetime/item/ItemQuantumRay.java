package rekkyn.spacetime.item;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import rekkyn.spacetime.Spacetime;
import rekkyn.spacetime.inventory.TileSpacetimeFluctuation;
import rekkyn.spacetime.packets.ParticlePacket;
import rekkyn.spacetime.packets.SpacetimeFluctuationPacket;
import cpw.mods.fml.common.network.PacketDispatcher;

public class ItemQuantumRay extends Item {
    
    private final Minecraft mc = Minecraft.getMinecraft();
    
    public ItemQuantumRay(int id) {
        super(id);
    }
    
    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 100000;
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
        player.setItemInUse(item, this.getMaxItemUseDuration(item));
        return item;
    }
    
    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.bow;
    }
    
    @Override
    public void onUsingItemTick(ItemStack stack, EntityPlayer player, int count) {
        if (!player.worldObj.isRemote) {
            if (mc.renderViewEntity.rayTrace(200, 1.0F) != null) {
                spawnParticles(player.posX, player.posY, player.posZ, player.worldObj);
                
                MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(player.worldObj,
                        player, true);
                
                if (movingobjectposition != null) {
                    if (movingobjectposition.typeOfHit == EnumMovingObjectType.TILE) {
                        int x = movingobjectposition.blockX;
                        int y = movingobjectposition.blockY;
                        int z = movingobjectposition.blockZ;
                        
                        if (player.worldObj.getBlockId(x, y, z) == Spacetime.spacetimeOre.blockID) {
                            TileSpacetimeFluctuation tile = (TileSpacetimeFluctuation) player.worldObj
                                    .getBlockTileEntity(x, y, z);
                            if (tile != null) {
                                PacketDispatcher.sendPacketToAllPlayers(new SpacetimeFluctuationPacket(x, y, z,
                                        --tile.damage).makePacket());
                                if (tile.damage <= 0) {
                                    if (!player.inventory.addItemStackToInventory(new ItemStack(
                                            Spacetime.spacetimeFluctuation))) {
                                        player.dropPlayerItem(new ItemStack(Spacetime.spacetimeFluctuation));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void spawnParticles(double x, double y, double z, World world) {
        for (int l = 0; l < 8; ++l) {
            double d1 = y + world.rand.nextFloat();
            int randx = world.rand.nextInt(2) * 2 - 1;
            int randz = world.rand.nextInt(2) * 2 - 1;
            double d4 = (world.rand.nextFloat() - 0.5D) * 0.5D;
            
            double d0 = x + 0.5D + 0.25D * randx;
            double d3 = world.rand.nextFloat() * 2.0F * randx;
            double d2 = z + 0.5D + 0.25D * randz;
            double d5 = world.rand.nextFloat() * 2.0F * randz;
            
            PacketDispatcher.sendPacketToAllAround(x, y, z, 64D, world.provider.dimensionId, new ParticlePacket("blue",
                    d0, d1, d2, d3, d4, d5).makePacket());
            
            if (l % 4 == 0) {
                PacketDispatcher.sendPacketToAllAround(x, y, z, 64D, world.provider.dimensionId, new ParticlePacket(
                        "orange", d0, d1, d2, d3, d4, d5).makePacket());
            }
            
        }
        
    }
    
}