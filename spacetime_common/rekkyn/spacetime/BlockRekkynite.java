package rekkyn.spacetime;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.world.World;

public class BlockRekkynite extends Block {
    
    protected BlockRekkynite(int id, Material material, boolean active) {
        super(id, material);
        
        isActive = active;
        if (isActive) {
            super.setLightValue(1.0F);
        }
        
    }
    
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int neighbourID) {
        if (world.isBlockIndirectlyGettingPowered(x, y, z)) {
            world.setBlock(x, y, z, Spacetime.rekkyniteGlowing.blockID);
        } else {
            world.setBlock(x, y, z, Spacetime.rekkynite.blockID);
        }
    }
    
    @Override
    public int idDropped(int i, Random random, int j) {
        return Spacetime.rekkynite.blockID;
    }
    
    @Override
    public int quantityDropped(Random random) {
        return 1;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {

        blockIcon = iconRegister.registerIcon(Spacetime.modid.toLowerCase() + ":" + this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1));
    }

    
    private boolean isActive;
    
}
