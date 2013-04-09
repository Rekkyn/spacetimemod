package rekkyn.spacetime;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
    
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        
        if (tileEntity instanceof TileSpacetimeInfuser) {
            
            return new ContainerSpacetimeInfuser((TileSpacetimeInfuser) tileEntity, player.inventory);
        }
        
        return null;
    }
    
    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        
        if (tileEntity instanceof TileSpacetimeInfuser) {
            
            return new GuiSpacetimeInfuser(player.inventory, (TileSpacetimeInfuser) tileEntity);
        }
        
        return null;
    }
}
