package rekkyn.spacetime.proxy;

import rekkyn.spacetime.handlers.HUDHandler;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerRenderInformation() {
        TickRegistry.registerTickHandler(new HUDHandler(), Side.CLIENT);
    }
    
}