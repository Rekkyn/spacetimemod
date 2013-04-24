package rekkyn.spacetime;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import rekkyn.spacetime.block.BlockRekkynite;
import rekkyn.spacetime.block.BlockSpacetimeInfuser;
import rekkyn.spacetime.block.BlockSpacetimeOre;
import rekkyn.spacetime.item.ItemIronRod;
import rekkyn.spacetime.item.ItemSpacetimeFluctuation;
import rekkyn.spacetime.item.ItemSpacetimeGem;
import rekkyn.spacetime.item.SpacetimeArmor;
import rekkyn.spacetime.item.tool.ItemSpacetimeAxe;
import rekkyn.spacetime.item.tool.ItemSpacetimeHoe;
import rekkyn.spacetime.item.tool.ItemSpacetimePickaxe;
import rekkyn.spacetime.item.tool.ItemSpacetimeSpade;
import rekkyn.spacetime.item.tool.ItemSpacetimeSword;
import rekkyn.spacetime.network.ClientPacketHandler;
import rekkyn.spacetime.network.EventHandler;
import rekkyn.spacetime.network.ServerPacketHandler;
import rekkyn.spacetime.proxy.CommonProxy;
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
    public static final Item ironRod = new ItemIronRod(1002).setUnlocalizedName("ironRod").setCreativeTab(
            CreativeTabs.tabMaterials).setFull3D();
    
    public static EnumToolMaterial spacetimeMaterial = EnumHelper.addToolMaterial("SPACETIME", 3, 2842, 14F, 7, 27);
    public static EnumArmorMaterial spacetimeArmorMaterial = EnumHelper.addArmorMaterial("SPACETIME", 38, new int[] {4, 9, 7, 3}, 27);
    
    public static final Item spacetimeSword = new ItemSpacetimeSword(1003, spacetimeMaterial)
            .setUnlocalizedName("spacetimeSword");
    public static final Item spacetimePick = new ItemSpacetimePickaxe(1004, spacetimeMaterial)
            .setUnlocalizedName("spacetimePick");
    public static final Item spacetimeShovel = new ItemSpacetimeSpade(1005, spacetimeMaterial)
            .setUnlocalizedName("spacetimeShovel");
    public static final Item spacetimeAxe = new ItemSpacetimeAxe(1006, spacetimeMaterial)
            .setUnlocalizedName("spacetimeAxe");
    public static final Item spacetimeHoe = new ItemSpacetimeHoe(1007, spacetimeMaterial)
            .setUnlocalizedName("spacetimeHoe");
    
    public static final Item spacetimeHelmet = new SpacetimeArmor(1008, spacetimeArmorMaterial, ModLoader.addArmor("SpacetimeArmor"), 0).setUnlocalizedName("spacetimeHelmet");
    public static final Item spacetimeChest = new SpacetimeArmor(1009, spacetimeArmorMaterial, ModLoader.addArmor("SpacetimeArmor"), 1).setUnlocalizedName("spacetimeChest");
    public static final Item spacetimeLegs = new SpacetimeArmor(1010, spacetimeArmorMaterial, ModLoader.addArmor("SpacetimeArmor"), 2).setUnlocalizedName("spacetimeLegs");
    public static final Item spacetimeBoots = new SpacetimeArmor(1011, spacetimeArmorMaterial, ModLoader.addArmor("SpacetimeArmor"), 3).setUnlocalizedName("spacetimeBoots");
    
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
        
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        
    }
    
    @PostInit
    public void postInit(FMLPostInitializationEvent event) {
    }
}
