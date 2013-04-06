package rekkyn.spacetime;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = Spacetime.modid, name = "Spacetime", version = "0.0")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class Spacetime {
    
    public static final String modid = "Spacetime";
    
    @Instance("Spacetime")
    public static Spacetime instance;
    
    @SidedProxy(clientSide = "rekkyn.spacetime.ClientProxy", serverSide = "rekkyn.spacetime.CommonProxy")
    public static CommonProxy proxy;
    
    @PreInit
    public void preInit(FMLPreInitializationEvent event) {
    }
    
    @Init
    public void load(FMLInitializationEvent event) {
        proxy.registerTiles();
        proxy.registerBlocks();
        proxy.addNames();
        proxy.addRecipes();
    }
    
    @PostInit
    public void postInit(FMLPostInitializationEvent event) {
    }
}
