package rekkyn.spacetime;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class CommonProxy implements IGuiHandler {
    public void registerRenderInformation() {
    }
    
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        
        if (ID == 0) {
            TileSpacetimeInfuser tileSpacetimeInfuser = (TileSpacetimeInfuser) world.getBlockTileEntity(x, y, z);
            return new ContainerSpacetimeInfuser(tileSpacetimeInfuser, player.inventory);
        }
        
        return null;
    }
    
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 0) {
            TileSpacetimeInfuser tileSpacetimeInfuser = (TileSpacetimeInfuser) world.getBlockTileEntity(x, y, z);
            return new GuiSpacetimeInfuser(player.inventory, tileSpacetimeInfuser);
        }
        return null;
    }
    
    
    public void registerTiles() {
        
    }
    
    public void registerBlocks() {
        
    }
    
    public void addNames() {
        LanguageRegistry.addName(Spacetime.rekkynite, "Rekkynite");
        LanguageRegistry.addName(Spacetime.spacetimeOre, "Spacetime Ore");
        LanguageRegistry.addName(Spacetime.spacetimeInfuser, "Spacetime Infuser");
        LanguageRegistry.addName(Spacetime.spacetimeFluctuation, "Spacetime Fluct¤ku¤rat¤ki¤ron");
        LanguageRegistry.addName(Spacetime.spacetimeGem, "Spacetime Gem");
        LanguageRegistry.addName(Spacetime.spacetimeIngot, "Spacetime Ingot");
    }
    
    public void addRecipes() {
        
    }
}