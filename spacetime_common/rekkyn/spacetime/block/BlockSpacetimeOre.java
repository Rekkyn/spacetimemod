package rekkyn.spacetime.block;

import java.util.Random;

import net.minecraft.block.BlockOre;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import rekkyn.spacetime.Spacetime;
import rekkyn.spacetime.particles.ParticleEffects;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSpacetimeOre extends BlockOre {
    
    public Minecraft mc;
    public WorldClient theWorld;
    
    public BlockSpacetimeOre(int id) {
        super(id);
    }
    
    @Override
    public int idDropped(int par1, Random random, int par2) {
        return Spacetime.spacetimeFluctuation.itemID;
    }
    
    @Override
    public int quantityDropped(Random par1Random) {
        return 1;
    }
    
    @Override
    public int quantityDroppedWithBonus(int i, Random random) {
        return this.quantityDropped(random);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        
        blockIcon = iconRegister.registerIcon("Spacetime:spacetimeOre");
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        if (rand.nextInt(60) == 0) {
            world.playSound(x + 0.5D, y + 0.5D, z + 0.5D, "portal.portal", 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
        }
        
        for (int l = 0; l < 16; ++l) {
            double d0 = x + rand.nextFloat();
            double d1 = y + rand.nextFloat();
            double d2 = z + rand.nextFloat();
            double d3 = 0.0D;
            double d4 = 0.0D;
            double d5 = 0.0D;
            int randx = rand.nextInt(2) * 2 - 1;
            int randz = rand.nextInt(2) * 2 - 1;
            d3 = (rand.nextFloat() - 0.5D) * 0.5D;
            d4 = (rand.nextFloat() - 0.5D) * 0.5D;
            d5 = (rand.nextFloat() - 0.5D) * 0.5D;
            
            d0 = x + 0.5D + 0.25D * randx;
            d3 = rand.nextFloat() * 2.0F * randx;
            d2 = z + 0.5D + 0.25D * randz;
            d5 = rand.nextFloat() * 2.0F * randz;
            
            ParticleEffects.spawnParticle("blue", d0, d1, d2, d3, d4, d5);
            
            if (l % 4 == 0) {
                ParticleEffects.spawnParticle("orange", d0, d1, d2, d3, d4, d5);
            }
            
        }
    }
    
    @Override
    public void onFallenUpon(World world, int x, int y, int z, Entity entity, float fallDistance) {
        if (!world.isRemote) {
            world.createExplosion(entity, x, y, z, fallDistance + 7, true);
        }
        
    }
    
    @Override
    public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion explosion) {
        dropBlockAsItem(world, x, y, z, 0, 0);
    }
    
    @Override
    public boolean canDropFromExplosion(Explosion par1Explosion) {
        return false;
    }
    
}
