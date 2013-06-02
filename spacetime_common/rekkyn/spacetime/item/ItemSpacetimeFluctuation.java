package rekkyn.spacetime.item;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import rekkyn.spacetime.particles.ParticleEffects;
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
            
            int randX = (int) (entity.posX + (rand.nextInt(11) - 5));
            int randY = (int) (entity.posY + (rand.nextInt(11) - 5));
            int randZ = (int) (entity.posZ + (rand.nextInt(11) - 5));
            
            if (rand.nextInt(lightningChance) == 0) {
                
                if (world.canBlockSeeTheSky(randX, randY, randZ) && world.getPrecipitationHeight(randX, randZ) == randY) {
                    world.addWeatherEffect(new EntityLightningBolt(world, randX, randY, randZ));
                }
            }
            
            /*
             * if (entity instanceof EntityPlayer) { EntityPlayer player =
             * (EntityPlayer) entity; if (rand.nextInt(10) == 0) { if
             * (world.getBlockId(randX, randY, randZ) == 0 &&
             * world.getBlockId(randX, randY + 1, randZ) == 0) { boolean safe =
             * false; for (int i = 1; i < 4; i++) { if (world.getBlockId(randZ,
             * randY - i, randZ) != 0) { safe = true; } } if (safe) {
             * player.setPositionAndUpdate(randX, randY, randZ);
             * System.out.println("Teleport!"); } } } }
             */
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
        par3List.add("\u00a74Careful, it explodes.");
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
        SpacetimeFluctuationEntity entityitem = new SpacetimeFluctuationEntity(world, location.posX, location.posY,
                location.posZ, itemstack);
        
        entityitem.motionX = location.motionX;
        entityitem.motionY = location.motionY;
        entityitem.motionZ = location.motionZ;
        
        return entityitem;
    }
    
}
