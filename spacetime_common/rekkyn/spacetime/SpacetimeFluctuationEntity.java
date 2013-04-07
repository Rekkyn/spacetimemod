package rekkyn.spacetime;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class SpacetimeFluctuationEntity extends EntityItem {
    
    public SpacetimeFluctuationEntity(World world, double x, double y, double z, ItemStack item) {
        super(world, x, y, z, item);
        delayBeforeCanPickup = 40;
        
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        
        if (!worldObj.isRemote) {
            if (rand.nextInt(3000) == 0) {
                worldObj.createExplosion(this, posX, posY, posZ, 3, true);
            }
            
            int lightningx = (int) (posX + (rand.nextInt(11) - 5));
            int lightningy = (int) (posY + (rand.nextInt(11) - 5));
            int lightningz = (int) (posZ + (rand.nextInt(11) - 5));
            
            if (rand.nextInt(50) == 0) {
                
                if (worldObj.canBlockSeeTheSky(lightningx, lightningy, lightningz)
                        && worldObj.getPrecipitationHeight(lightningx, lightningz) == lightningy) {
                    worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, lightningx, lightningy, lightningz));
                }
            }
        }
        
        for (int l = 0; l < 4; ++l) {
            double d1 = posY + rand.nextFloat();
            int randx = rand.nextInt(2) * 2 - 1;
            int randz = rand.nextInt(2) * 2 - 1;
            double d4 = (rand.nextFloat() - 0.5D) * 0.5D;
            
            double d0 = posX + 0.5D + 0.25D * randx;
            double d3 = rand.nextFloat() * 2.0F * randx;
            double d2 = posZ + 0.5D + 0.25D * randz;
            double d5 = rand.nextFloat() * 2.0F * randz;
            
            ParticleEffects.spawnParticle("spacetime", d0, d1, d2, d3, d4, d5, 0.16F + rand.nextFloat() / 8,
                    0.18F + rand.nextFloat() / 8, 0.62F + rand.nextFloat() / 8);
            if (l % 4 == 0) {
                ParticleEffects.spawnParticle("spacetime", d0, d1, d2, d3, d4, d5, 0.81F + rand.nextFloat() / 8,
                        0.41F + rand.nextFloat() / 8, 0.05F + rand.nextFloat() / 8);
            }
            
        }
        
    }
    
    @Override
    protected void dealFireDamage(int par1) {
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, int par2) {
        return false;
    }
    
}
