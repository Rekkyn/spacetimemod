package rekkyn.spacetime.item.tool;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import rekkyn.spacetime.Spacetime;
import rekkyn.spacetime.handlers.SpacetimeChargeHandler;
import rekkyn.spacetime.item.ISpacetimeCharge;
import rekkyn.spacetime.particles.ParticleEffects;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSpacetimeSword extends ItemSword implements ISpacetimeCharge {
    
    public static final int spacetimeMaxCharge = 500;
    public static final int useAmount = 1650;
    
    public ItemSpacetimeSword(int id, EnumToolMaterial material) {
        super(id, material);
    }
    
    @Override
    public void registerIcons(IconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon("Spacetime:spacetimeSword");
    }
    
    @Override
    public boolean getIsRepairable(ItemStack tool, ItemStack repairItem) {
        return repairItem.itemID == Spacetime.spacetimeGem.itemID;
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
        
        if (SpacetimeChargeHandler.addChargeToTotal(player, -useAmount)) {
            
            for (int l = 0; l < 32; ++l) {
                double d1 = player.posY + world.rand.nextFloat();
                int randx = world.rand.nextInt(2) * 2 - 1;
                int randz = world.rand.nextInt(2) * 2 - 1;
                double d4 = (world.rand.nextFloat() - 0.5D) * 0.5D;
                
                double d0 = player.posX + 0.5D + 0.25D * randx;
                double d3 = world.rand.nextFloat() * 2.0F * randx;
                double d2 = player.posZ + 0.5D + 0.25D * randz;
                double d5 = world.rand.nextFloat() * 2.0F * randz;
                
                if (world.isRemote) {
                    ParticleEffects.spawnParticle("blue", d0, d1, d2, d3, d4, d5);
                    
                    float xVel = (world.rand.nextFloat() - 0.5F) * 5;
                    float yVel = (world.rand.nextFloat() - 0.5F) * 5;
                    float zVel = (world.rand.nextFloat() - 0.5F) * 5;
                    ParticleEffects.spawnParticle("crossbowTrail", player.posX, player.posY, player.posZ, xVel, yVel,
                            zVel);
                    
                    if (l % 4 == 0) {
                        ParticleEffects.spawnParticle("orange", d0, d1, d2, d3, d4, d5);
                    }
                }
                
            }
            
            if (!player.isSneaking()) {
                player.addVelocity(-MathHelper.sin(player.rotationYaw * (float) Math.PI / 180.0F) * 0.7F,
                        -MathHelper.sin(player.rotationPitch / 180.0F * (float) Math.PI) * 0.2F + 0.4,
                        MathHelper.cos(player.rotationYaw * (float) Math.PI / 180.0F) * 0.7F);
            } else {
                player.addVelocity(MathHelper.sin(player.rotationYaw * (float) Math.PI / 180.0F) * 0.7F,
                        MathHelper.sin(player.rotationPitch / 180.0F * (float) Math.PI) * 0.2F + 0.4,
                        -MathHelper.cos(player.rotationYaw * (float) Math.PI / 180.0F) * 0.7F);
            }
            player.fallDistance *= 0.4;
        }
        
        return itemstack;
    }
    
    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity player, int par4, boolean par5) {
        if (!world.isRemote) {
            SpacetimeChargeHandler.changeCharge(itemstack, 10);
        }
    }
    
    @Override
    public boolean getShareTag() {
        return true;
    }
    
    @Override
    public int getSpacetimeMaxCharge() {
        return spacetimeMaxCharge;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
        list.add(SpacetimeChargeHandler.getSpacetimeCharge(itemstack) + "/" + spacetimeMaxCharge);
    }
    
    @Override
    public int getUseAmount() {
        return useAmount;
    }
    
}
