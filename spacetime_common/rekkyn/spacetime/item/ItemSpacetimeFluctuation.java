package rekkyn.spacetime.item;

import java.util.List;
import java.util.Random;

import rekkyn.spacetime.particles.ParticleEffects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSpacetimeFluctuation extends Item {
    
    Random rand = new Random();
    
    public static int explosionChance = 3000;
    public static int lightningChance = 80;
    
    public ItemSpacetimeFluctuation(int id) {
        super(id);
    }
    
    @Override
    public void onUpdate(ItemStack item, World world, Entity entity, int par4, boolean par5) {
        double x = entity.posX;
        double y = entity.posY;
        double z = entity.posZ;
        
        if (!world.isRemote) {
            if (rand.nextInt(explosionChance) == 0) {
                world.createExplosion(entity, entity.posX, entity.posY, entity.posZ, 3, true);
                entity.attackEntityFrom(DamageSource.setExplosionSource(null), 7);
            }
            
            int lightningx = (int) (entity.posX + (rand.nextInt(11) - 5));
            int lightningy = (int) (entity.posY + (rand.nextInt(11) - 5));
            int lightningz = (int) (entity.posZ + (rand.nextInt(11) - 5));
            
            if (rand.nextInt(lightningChance) == 0) {
                
                if (world.canBlockSeeTheSky(lightningx, lightningy, lightningz)
                        && world.getPrecipitationHeight(lightningx, lightningz) == lightningy) {
                    world.addWeatherEffect(new EntityLightningBolt(world, lightningx, lightningy, lightningz));
                }
            }
        }
        
        for (int l = 0; l < 4; ++l) {
            double d1 = y + rand.nextFloat();
            int randx = rand.nextInt(2) * 2 - 1;
            int randz = rand.nextInt(2) * 2 - 1;
            double d4 = (rand.nextFloat() - 0.5D) * 0.5D;
            
            double d0 = x + 0.5D + 0.25D * randx;
            double d3 = rand.nextFloat() * 2.0F * randx;
            double d2 = z + 0.5D + 0.25D * randz;
            double d5 = rand.nextFloat() * 2.0F * randz;
            
            ParticleEffects.spawnParticle("blue", d0, d1, d2, d3, d4, d5);
            if (l % 4 == 0) {
                ParticleEffects.spawnParticle("orange", d0, d1, d2, d3, d4, d5);
            }
            
        }
        
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    /**
     * allows items to add custom lines of information to the mouseover description
     */
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        par3List.add("¤4Careful, it explodes.");
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack) {
        return true;
    }
    
    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }
    
    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
        return new SpacetimeFluctuationEntity(world, location.posX, location.posY, location.posZ, itemstack);
    }
    
}
