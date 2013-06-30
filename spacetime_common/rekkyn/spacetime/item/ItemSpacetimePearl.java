package rekkyn.spacetime.item;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import rekkyn.spacetime.handlers.SpacetimeChargeHandler;
import rekkyn.spacetime.packets.ParticlePacket;
import rekkyn.spacetime.particles.ParticleEffects;
import cpw.mods.fml.common.network.PacketDispatcher;

public class ItemSpacetimePearl extends Item implements ISpacetimeCharge {
    
    // TODO:balance this
    public static final int spacetimeMaxCharge = 500;
    public static final int useAmount = 2000;
    
    private final Minecraft mc = Minecraft.getMinecraft();
    
    public ItemSpacetimePearl(int id) {
        super(id);
    }
    
    @Override
    public int getSpacetimeMaxCharge() {
        return spacetimeMaxCharge;
    }
    
    @Override
    public int getUseAmount() {
        return useAmount;
    }
    
    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 72000;
    }
    
    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.bow;
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
        if (SpacetimeChargeHandler.getCurrentCharge(player) >= useAmount || player.capabilities.isCreativeMode) {
            player.setItemInUse(item, this.getMaxItemUseDuration(item));
        }
        return item;
    }
    
    @Override
    public void onPlayerStoppedUsing(ItemStack item, World world, EntityPlayer player, int chargetime) {
        int i = this.getMaxItemUseDuration(item) - chargetime;
        float charge = i / 20.0F;
        charge = (charge * charge + charge * 2.0F) / 3.0F;
        
        if (charge < 1.0D) { return; }
        
        if (charge > 1.0F) {
            charge = 1.0F;
        }
        
        if (charge == 1.0F && !world.isRemote) {
            if (mc.renderViewEntity.rayTrace(200, 1.0F) != null) {
                world.playSoundAtEntity(player, "mob.endermen.portal", 1.0F,
                        1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);
                for (int lmnop = 0; lmnop < 4; lmnop++) {
                    spawnParticles(player.posX, player.posY, player.posZ, world);
                }
                int x = mc.renderViewEntity.rayTrace(200, 1.0F).blockX;
                int y = mc.renderViewEntity.rayTrace(200, 1.0F).blockY;
                int z = mc.renderViewEntity.rayTrace(200, 1.0F).blockZ;
                int side = mc.renderViewEntity.rayTrace(200, 1.0F).sideHit;
                if (side == 0) {
                    player.setPositionAndUpdate(x + 0.5, y - 2, z + 0.5);
                } else if (side == 1) {
                    player.setPositionAndUpdate(x + 0.5, y + 1, z + 0.5);
                } else if (side == 2) {
                    player.setPositionAndUpdate(x + 0.5, y, z - 0.5);
                } else if (side == 3) {
                    player.setPositionAndUpdate(x + 0.5, y, z + 1.5);
                } else if (side == 4) {
                    player.setPositionAndUpdate(x - 0.5, y, z + 0.5);
                } else if (side == 5) {
                    player.setPositionAndUpdate(x + 1.5, y, z + 0.5);
                } else {
                    return;
                }
                player.fallDistance = 0.0F;
                world.playSoundAtEntity(player, "mob.endermen.portal", 1.0F,
                        1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);
                for (int lmnop = 0; lmnop < 32; lmnop++) {
                    float xVel = (world.rand.nextFloat() - 0.5F) * 5;
                    float yVel = (world.rand.nextFloat() - 0.5F) * 5;
                    float zVel = (world.rand.nextFloat() - 0.5F) * 5;
                    ParticleEffects.spawnParticle("crossbowTrail", player.posX, player.posY, player.posZ, xVel, yVel,
                            zVel);
                }
                SpacetimeChargeHandler.addChargeToTotal(player, -useAmount);
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
