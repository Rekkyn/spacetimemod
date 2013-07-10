package rekkyn.spacetime.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import rekkyn.spacetime.Spacetime;
import rekkyn.spacetime.client.RenderCrossbowBolt;
import rekkyn.spacetime.client.RenderItemCrossbow;
import rekkyn.spacetime.client.RenderItemHoe;
import rekkyn.spacetime.client.RenderSpacetimeChest;
import rekkyn.spacetime.client.RenderSpacetimeFluctuation;
import rekkyn.spacetime.entity.EntityCrossbowBolt;
import rekkyn.spacetime.handlers.HUDHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerStuffNJazz() {
        super.registerStuffNJazz();
        TickRegistry.registerTickHandler(new HUDHandler(), Side.CLIENT);
        RenderingRegistry.registerEntityRenderingHandler(EntityCrossbowBolt.class, new RenderCrossbowBolt());
        
        MinecraftForgeClient.registerItemRenderer(Spacetime.spacetimeCrossbow.itemID, new RenderItemCrossbow());
        MinecraftForgeClient.registerItemRenderer(Spacetime.spacetimeHoe.itemID, new RenderItemHoe());
        
        ClientRegistry.bindTileEntitySpecialRenderer(rekkyn.spacetime.inventory.TileSpacetimeChest.class,
                new RenderSpacetimeChest());
        ClientRegistry.bindTileEntitySpecialRenderer(rekkyn.spacetime.inventory.TileSpacetimeFluctuation.class,
                new RenderSpacetimeFluctuation());        
    }
    
}