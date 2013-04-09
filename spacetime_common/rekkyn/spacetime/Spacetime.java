package rekkyn.spacetime;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import rekkyn.spacetime.block.*;
import rekkyn.spacetime.item.*;
import rekkyn.spacetime.network.*;
import rekkyn.spacetime.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = Spacetime.modid, name = "Spacetime", version = "0.0")
@NetworkMod(
        clientSideRequired = true, serverSideRequired = false, clientPacketHandlerSpec = @SidedPacketHandler(
                channels = { "Spacetime" }, packetHandler = ClientPacketHandler.class),
        serverPacketHandlerSpec = @SidedPacketHandler(
                channels = { "Spacetime" }, packetHandler = ServerPacketHandler.class))
public class Spacetime {
    
    public static final String modid = "Spacetime";
        
    public static final BlockRekkynite rekkynite = (BlockRekkynite) new BlockRekkynite(450, Material.rock, false)
            .setHardness(1.7F).setResistance(10F).setUnlocalizedName("rekkynite").setCreativeTab(CreativeTabs.tabBlock);
    public static final BlockRekkynite rekkyniteGlowing = (BlockRekkynite) new BlockRekkynite(451, Material.rock, true)
            .setHardness(1.7F).setResistance(10F).setUnlocalizedName("rekkyniteGlowing").setLightValue(1.0F);
    public static final BlockSpacetimeOre spacetimeOre = (BlockSpacetimeOre) new BlockSpacetimeOre(452).setHardness(5F)
            .setResistance(50F).setUnlocalizedName("spacetimeOre").setCreativeTab(CreativeTabs.tabBlock)
            .setLightValue(1.0F);
    public static final BlockSpacetimeInfuser spacetimeInfuser = (BlockSpacetimeInfuser) new BlockSpacetimeInfuser(453)
            .setHardness(50.0F).setResistance(2000.0F).setUnlocalizedName("spacetimeInfuser")
            .setCreativeTab(CreativeTabs.tabDecorations);
    
    public static final ItemSpacetimeFluctuation spacetimeFluctuation = (ItemSpacetimeFluctuation) new ItemSpacetimeFluctuation(
            1000).setMaxStackSize(1).setCreativeTab(CreativeTabs.tabMaterials)
            .setUnlocalizedName("spacetimeFluctuation");
    public static final ItemSpacetimeGem spacetimeGem = (ItemSpacetimeGem) new ItemSpacetimeGem(1001).setCreativeTab(
            CreativeTabs.tabMaterials).setUnlocalizedName("spacetimeGem");
    public static final ItemSpacetimeIngot spacetimeIngot = (ItemSpacetimeIngot) new ItemSpacetimeIngot(1002)
            .setCreativeTab(CreativeTabs.tabMaterials).setUnlocalizedName("spacetimeIngot");
    
    @Instance("Spacetime")
    public static Spacetime instance;
    
    @SidedProxy(clientSide = "rekkyn.spacetime.proxy.ClientProxy", serverSide = "rekkyn.spacetime.proxy.CommonProxy")
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
        
        NetworkRegistry.instance().registerGuiHandler(instance, proxy);
        
    }
    
    @PostInit
    public void postInit(FMLPostInitializationEvent event) {
    }
}
