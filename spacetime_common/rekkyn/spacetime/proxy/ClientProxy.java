package rekkyn.spacetime.proxy;

import rekkyn.spacetime.client.RenderCrossbowBolt;
import rekkyn.spacetime.entity.EntityCrossbowBolt;
import rekkyn.spacetime.handlers.HUDHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerStuffNJazz() {
        TickRegistry.registerTickHandler(new HUDHandler(), Side.CLIENT);
        RenderingRegistry.registerEntityRenderingHandler(EntityCrossbowBolt.class, new RenderCrossbowBolt());
    }
    
}