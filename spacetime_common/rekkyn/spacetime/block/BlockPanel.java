package rekkyn.spacetime.block;

import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPanel extends BlockPane {
    
    @SideOnly(Side.CLIENT)
    private Icon theIcon;
    private final String texture;
    private final String sideTextureIndex;
    
    public BlockPanel(int id, String texture, String sideTextureIndex, Material material, boolean canDropItself) {
        super(id, texture, sideTextureIndex, material, canDropItself);
        this.sideTextureIndex = sideTextureIndex;
        this.texture = texture;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        blockIcon = par1IconRegister.registerIcon("Spacetime:" + texture);
        theIcon = par1IconRegister.registerIcon("Spacetime:" + sideTextureIndex);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getSideTextureIndex() {
        return theIcon;
    }
    
}
