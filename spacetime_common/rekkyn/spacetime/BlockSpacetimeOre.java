package rekkyn.spacetime;

import net.minecraft.block.BlockOre;
import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSpacetimeOre extends BlockOre {
    
    public BlockSpacetimeOre(int id) {
        super(id);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        
        blockIcon = iconRegister.registerIcon(Spacetime.modid.toLowerCase() + ":"
                + this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1));
    }
    
}
