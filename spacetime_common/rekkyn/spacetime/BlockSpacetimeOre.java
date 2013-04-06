package rekkyn.spacetime;

import java.util.Random;

import net.minecraft.block.BlockOre;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSpacetimeOre extends BlockOre {
    
    public Minecraft mc;
    public WorldClient theWorld;
    
    public BlockSpacetimeOre(int id) {
        super(id);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        
        blockIcon = iconRegister.registerIcon(Spacetime.modid.toLowerCase() + ":"
                + this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1));
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        if (rand.nextInt(100) == 0) {
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
            
                d0 = (double)x + 0.5D + 0.25D * (double)randx;
                d3 = (double)(rand.nextFloat() * 2.0F * (float)randx);
                d2 = (double)z + 0.5D + 0.25D * (double)randz;
                d5 = (double)(rand.nextFloat() * 2.0F * (float)randz);
                
            
            ParticleEffects.spawnParticle("spacetime", d0, d1, d2, d3, d4, d5, 0.16F+(rand.nextFloat())/8, 0.18F+(rand.nextFloat())/8, 0.62F+(rand.nextFloat())/8);


        }
    }
}
