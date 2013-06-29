package rekkyn.spacetime.block;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import rekkyn.spacetime.Spacetime;
import rekkyn.spacetime.inventory.InventorySpacetimeChest;
import rekkyn.spacetime.inventory.TileSpacetimeChest;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSpacetimeChest extends BlockContainer {
    public BlockSpacetimeChest(int par1) {
        super(par1, Material.rock);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public int getRenderType() {
        return 22;
    }
    
    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
        return Spacetime.spacetimeChest.blockID;
    }
    
    @Override
    public int quantityDropped(Random par1Random) {
        return 1;
    }
    
    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving,
            ItemStack par6ItemStack) {
        byte b0 = 0;
        int l = MathHelper.floor_double((par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        
        if (l == 0) {
            b0 = 2;
        }
        
        if (l == 1) {
            b0 = 5;
        }
        
        if (l == 2) {
            b0 = 3;
        }
        
        if (l == 3) {
            b0 = 4;
        }
        
        par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 2);
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7,
            float par8, float par9) {
        //TODO: MAKE THIS WORK YO
        InventorySpacetimeChest inventoryspacetime = new InventorySpacetimeChest();
        TileSpacetimeChest spacetimechest = (TileSpacetimeChest) world.getBlockTileEntity(x, y, z);
        
        if (inventoryspacetime != null && spacetimechest != null) {
            if (world.isBlockNormalCube(x, y + 1, z)) {
                return true;
            } else if (world.isRemote) {
                return true;
            } else {
                inventoryspacetime.setAssociatedChest(spacetimechest);
                player.displayGUIChest(inventoryspacetime);
                return true;
            }
        } else {
            return true;
        }
    }
    
    @Override
    public TileEntity createNewTileEntity(World par1World) {
        return new TileSpacetimeChest();
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
        for (int l = 0; l < 3; ++l) {
            double d0 = (par2 + par5Random.nextFloat());
            double d1 = (par3 + par5Random.nextFloat());
            d0 = (par4 + par5Random.nextFloat());
            double d2 = 0.0D;
            double d3 = 0.0D;
            double d4 = 0.0D;
            int i1 = par5Random.nextInt(2) * 2 - 1;
            int j1 = par5Random.nextInt(2) * 2 - 1;
            d2 = (par5Random.nextFloat() - 0.5D) * 0.125D;
            d3 = (par5Random.nextFloat() - 0.5D) * 0.125D;
            d4 = (par5Random.nextFloat() - 0.5D) * 0.125D;
            double d5 = par4 + 0.5D + 0.25D * j1;
            d4 = (par5Random.nextFloat() * 1.0F * j1);
            double d6 = par2 + 0.5D + 0.25D * i1;
            d2 = (par5Random.nextFloat() * 1.0F * i1);
            par1World.spawnParticle("portal", d6, d1, d5, d2, d3, d4);
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        blockIcon = par1IconRegister.registerIcon("obsidian");
    }
}
