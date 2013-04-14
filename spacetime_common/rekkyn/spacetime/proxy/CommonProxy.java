package rekkyn.spacetime.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import rekkyn.spacetime.Spacetime;
import rekkyn.spacetime.inventory.ContainerSpacetimeInfuser;
import rekkyn.spacetime.inventory.GuiSpacetimeInfuser;
import rekkyn.spacetime.inventory.TileSpacetimeInfuser;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
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
        GameRegistry.registerTileEntity(TileSpacetimeInfuser.class, "tileSpacetimeInfuser");
        
    }
    
    public void registerBlocks() {
        MinecraftForge.setBlockHarvestLevel(Spacetime.rekkynite, "pickaxe", 0);
        GameRegistry.registerBlock(Spacetime.rekkynite, "rekkynite");
        
        MinecraftForge.setBlockHarvestLevel(Spacetime.spacetimeOre, "pickaxe", 2);
        GameRegistry.registerBlock(Spacetime.spacetimeOre, "spacetimeOre");
        
        MinecraftForge.setBlockHarvestLevel(Spacetime.spacetimeInfuser, "pickaxe", 2);
        GameRegistry.registerBlock(Spacetime.spacetimeInfuser, "spacetimeInfuser");
        
    }
    
    public void addNames() {
        LanguageRegistry.addName(Spacetime.rekkynite, "Rekkynite");
        LanguageRegistry.addName(Spacetime.spacetimeOre, "Spacetime Ore");
        LanguageRegistry.addName(Spacetime.spacetimeInfuser, "Spacetime Infuser");
        LanguageRegistry.addName(Spacetime.spacetimeFluctuation, "Spacetime Fluct¤ku¤rat¤ki¤ron");
        LanguageRegistry.addName(Spacetime.spacetimeGem, "Spacetime Crystal");
        LanguageRegistry.addName(Spacetime.ironRod, "Iron Rod");

    }
    
    public void addRecipes() {
        GameRegistry.addRecipe(new ItemStack(Spacetime.ironRod), "x", "x", "x", 
                'x', Item.ingotIron);
    }
}