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
import rekkyn.spacetime.handlers.ClientPacketHandler;
import rekkyn.spacetime.handlers.EventHandler;
import rekkyn.spacetime.handlers.ServerPacketHandler;
import rekkyn.spacetime.item.GenericItem;
import rekkyn.spacetime.item.ItemSpacetimeFluctuation;
import rekkyn.spacetime.item.SpacetimeArmor;
import rekkyn.spacetime.item.SpacetimeCrossbow;
import rekkyn.spacetime.item.tool.ItemSpacetimeAxe;
import rekkyn.spacetime.item.tool.ItemSpacetimeHoe;
import rekkyn.spacetime.item.tool.ItemSpacetimePickaxe;
import rekkyn.spacetime.item.tool.ItemSpacetimeSpade;
import rekkyn.spacetime.item.tool.ItemSpacetimeSword;
import rekkyn.spacetime.proxy.CommonProxy;
import rekkyn.spacetime.world.SpacetimeWorldGen;
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
import cpw.mods.fml.common.registry.GameRegistry;

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
    public static final Item spacetimeGem = new GenericItem(1001, true).setCreativeTab(
            CreativeTabs.tabMaterials).setUnlocalizedName("spacetimeGem");
    public static final Item ironRod = new GenericItem(1002, false).setUnlocalizedName("ironRod")
            .setCreativeTab(CreativeTabs.tabMaterials).setFull3D();
    
    public static EnumToolMaterial spacetimeMaterial = EnumHelper.addToolMaterial("SPACETIME", 3, 2842, 14F, 7, 27);
    public static EnumArmorMaterial spacetimeArmorMaterial = EnumHelper.addArmorMaterial("SPACETIME", 38, new int[] {
            4, 9, 7, 3 }, 27);
    
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
    
    public static final Item spacetimeHelmet = new SpacetimeArmor(1008, spacetimeArmorMaterial,
            ModLoader.addArmor("SpacetimeArmor"), 0).setUnlocalizedName("spacetimeHelmet");
    public static final Item spacetimeChest = new SpacetimeArmor(1009, spacetimeArmorMaterial,
            ModLoader.addArmor("SpacetimeArmor"), 1).setUnlocalizedName("spacetimeChest");
    public static final Item spacetimeLegs = new SpacetimeArmor(1010, spacetimeArmorMaterial,
            ModLoader.addArmor("SpacetimeArmor"), 2).setUnlocalizedName("spacetimeLegs");
    public static final Item spacetimeBoots = new SpacetimeArmor(1011, spacetimeArmorMaterial,
            ModLoader.addArmor("SpacetimeArmor"), 3).setUnlocalizedName("spacetimeBoots");
    
    public static final SpacetimeCrossbow spacetimeCrossbow = (SpacetimeCrossbow) new SpacetimeCrossbow(1012)
            .setUnlocalizedName("spacetimeBow").setFull3D();
    
    public static final Item obsidianShard = new GenericItem(1013, false)
    .setUnlocalizedName("obsidianShard").setCreativeTab(CreativeTabs.tabMaterials);
    public static final Item crossbowBolt = new GenericItem(1014, false)
    .setUnlocalizedName("crossbowBolt").setCreativeTab(CreativeTabs.tabCombat);
    
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
        proxy.registerStuffNJazz();
        
        GameRegistry.registerWorldGenerator(new SpacetimeWorldGen());
        
        NetworkRegistry.instance().registerGuiHandler(instance, proxy);
        
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        
    }
    
    @PostInit
    public void postInit(FMLPostInitializationEvent event) {
    }
}
