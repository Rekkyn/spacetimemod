package rekkyn.spacetime.block;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import rekkyn.spacetime.Spacetime;
import rekkyn.spacetime.inventory.TileSpacetimeFluctuation;
import rekkyn.spacetime.particles.ParticleEffects;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSpacetimeOre extends BlockContainer {
    
    public Minecraft mc;
    public WorldClient theWorld;
    
    public BlockSpacetimeOre(int id, Material material) {
        super(id, material);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister icon)
    {
        this.blockIcon = icon.registerIcon("Spacetime:rekkyniteGlowing");
    }
    
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return false;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
        return null;
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
            int randy = rand.nextInt(2) * 2 - 1;
            int randz = rand.nextInt(2) * 2 - 1;
            
            d0 = x + 0.5D + 0.25D * randx;
            d3 = rand.nextFloat() * 10.0F * randx;
            d4 = rand.nextFloat() * 10.0F * randy;
            d2 = z + 0.5D + 0.25D * randz;
            d5 = rand.nextFloat() * 10.0F * randz;
            
            ParticleEffects.spawnParticle("blue", d0, d1, d2, d3, d4, d5);
            
            if (l % 4 == 0) {
                ParticleEffects.spawnParticle("orange", d0, d1, d2, d3, d4, d5);
            }
            
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
    
    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileSpacetimeFluctuation();
    }
    
}
