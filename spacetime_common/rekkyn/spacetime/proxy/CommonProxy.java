package rekkyn.spacetime.proxy;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.MinecraftForge;
import rekkyn.spacetime.Spacetime;
import rekkyn.spacetime.inventory.ContainerSpacetimeInfuser;
import rekkyn.spacetime.inventory.GuiSpacetimeInfuser;
import rekkyn.spacetime.inventory.TileSpacetimeChest;
import rekkyn.spacetime.inventory.TileSpacetimeInfuser;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class CommonProxy implements IGuiHandler {
    public static final String extendedPropertiesIdentifier = "spacetime";

    public void registerStuffNJazz() {
        ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(
                new WeightedRandomChestContent(new ItemStack(Spacetime.spacetimeFluctuation), 1, 1, 50));
        ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(
                new WeightedRandomChestContent(new ItemStack(Spacetime.spacetimeFluctuation), 1, 1, 3));
        ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST).addItem(
                new WeightedRandomChestContent(new ItemStack(Spacetime.spacetimeFluctuation), 1, 1, 3));
        ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_CHEST).addItem(
                new WeightedRandomChestContent(new ItemStack(Spacetime.spacetimeFluctuation), 1, 1, 3));
        ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(
                new WeightedRandomChestContent(new ItemStack(Spacetime.spacetimeFluctuation), 1, 1, 6));
        ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_LIBRARY).addItem(
                new WeightedRandomChestContent(new ItemStack(Spacetime.spacetimeFluctuation), 1, 1, 5));
        ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(
                new WeightedRandomChestContent(new ItemStack(Spacetime.spacetimeFluctuation), 1, 1, 6));
        ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(
                new WeightedRandomChestContent(new ItemStack(Spacetime.spacetimeFluctuation), 1, 1, 3));
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
        GameRegistry.registerTileEntity(TileSpacetimeChest.class, "tileSpacetimeChest");
    }
    
    public void registerBlocks() {
        MinecraftForge.setBlockHarvestLevel(Spacetime.rekkynite, "pickaxe", 0);
        GameRegistry.registerBlock(Spacetime.rekkynite, "rekkynite");
        
        MinecraftForge.setBlockHarvestLevel(Spacetime.spacetimeOre, "pickaxe", 3);
        GameRegistry.registerBlock(Spacetime.spacetimeOre, "spacetimeOre");
        
        MinecraftForge.setBlockHarvestLevel(Spacetime.spacetimeInfuser, "pickaxe", 2);
        GameRegistry.registerBlock(Spacetime.spacetimeInfuser, "spacetimeInfuser");
        
        MinecraftForge.setBlockHarvestLevel(Spacetime.spacetimePanel, "pickaxe", 1);
        GameRegistry.registerBlock(Spacetime.spacetimePanel, "spacetimePanel");
        
        GameRegistry.registerBlock(Spacetime.spacetimeChest, "spacetimeChest");
        
    }
    
    public void addNames() {
        LanguageRegistry.addName(Spacetime.rekkynite, "Rekkynite");
        LanguageRegistry.addName(Spacetime.spacetimeOre, "Spacetime Ore");
        LanguageRegistry.addName(Spacetime.spacetimeInfuser, "Spacetime Infuser");
        LanguageRegistry.addName(Spacetime.spacetimeFluctuation, "Spacetime Fluct\u00a7ku\u00a7rat\u00a7ki\u00a7ron");
        LanguageRegistry.addName(Spacetime.spacetimeGem, "Spacetime Crystal");
        LanguageRegistry.addName(Spacetime.ironRod, "Iron Rod");
        LanguageRegistry.addName(Spacetime.spacetimeSword, "\u00a79Spacetime Sword");
        LanguageRegistry.addName(Spacetime.spacetimePick, "\u00a79Spacetime Pick");
        LanguageRegistry.addName(Spacetime.spacetimeShovel, "\u00a79Spacetime Shovel");
        LanguageRegistry.addName(Spacetime.spacetimeAxe, "\u00a79Spacetime Axe");
        LanguageRegistry.addName(Spacetime.spacetimeHoe, "\u00a79Spacetime Hoe");
        LanguageRegistry.addName(Spacetime.spacetimeHelmet, "\u00a79Spacetime Helmet");
        LanguageRegistry.addName(Spacetime.spacetimeChestplate, "\u00a79Spacetime Chestplate");
        LanguageRegistry.addName(Spacetime.spacetimeLegs, "\u00a79Spacetime Leggings");
        LanguageRegistry.addName(Spacetime.spacetimeBoots, "\u00a79Spacetime Boots");
        LanguageRegistry.addName(Spacetime.spacetimeCrossbow, "\u00a79Spacetime Crossbow");
        LanguageRegistry.addName(Spacetime.obsidianShard, "Obsidian Shard");
        LanguageRegistry.addName(Spacetime.crossbowBolt, "Crossbow Bolt");
        LanguageRegistry.instance().addStringLocalization("item.spacetimeJar.name", "Spacetime Jar");
        LanguageRegistry.instance().addStringLocalization("item.spacetimeJar.full.name", "Spacetime Jar");
        LanguageRegistry.addName(Spacetime.spacetimePanel, "\u00a79Spacetime Panel");
        LanguageRegistry.addName(Spacetime.spacetimeChest, "\u00a79Spacetime Chest");
        LanguageRegistry.addName(Spacetime.spacetimePearl, "\u00a79Spacetime Pearl");
        
    }
    
    public void addRecipes() {
        GameRegistry.addRecipe(new ItemStack(Spacetime.rekkynite), "sss", "sFs", "sss", 's', Block.stone, 'F',
                Spacetime.spacetimeFluctuation);
        
        GameRegistry.addRecipe(new ItemStack(Spacetime.ironRod), "x", "x", "x", 'x', Item.ingotIron);
        
        GameRegistry.addRecipe(new ItemStack(Spacetime.spacetimeSword), "x", "x", "|", 'x', Spacetime.spacetimeGem,
                '|', Spacetime.ironRod);
        GameRegistry.addRecipe(new ItemStack(Spacetime.spacetimePick), "xxx", " | ", " | ", 'x',
                Spacetime.spacetimeGem, '|', Spacetime.ironRod);
        GameRegistry.addRecipe(new ItemStack(Spacetime.spacetimeShovel), "x", "|", "|", 'x', Spacetime.spacetimeGem,
                '|', Spacetime.ironRod);
        GameRegistry.addRecipe(new ItemStack(Spacetime.spacetimeAxe), "xx", "x|", " |", 'x', Spacetime.spacetimeGem,
                '|', Spacetime.ironRod);
        GameRegistry.addRecipe(new ItemStack(Spacetime.spacetimeHoe), "xx", " |", " |", 'x', Spacetime.spacetimeGem,
                '|', Spacetime.ironRod);
        
        GameRegistry.addRecipe(new ItemStack(Spacetime.spacetimeHelmet), "xxx", "x x", 'x', Spacetime.spacetimeGem);
        GameRegistry.addRecipe(new ItemStack(Spacetime.spacetimeChestplate), "x x", "xxx", "xxx", 'x',
                Spacetime.spacetimeGem);
        GameRegistry
                .addRecipe(new ItemStack(Spacetime.spacetimeLegs), "xxx", "x x", "x x", 'x', Spacetime.spacetimeGem);
        GameRegistry.addRecipe(new ItemStack(Spacetime.spacetimeBoots), "x x", "x x", 'x', Spacetime.spacetimeGem);
        
        GameRegistry.addRecipe(new ItemStack(Spacetime.spacetimeCrossbow), "XX|", "S|X", "|SX", 'X',
                Spacetime.spacetimeGem, '|', Spacetime.ironRod, 'S', Item.silk);
        
        GameRegistry.addRecipe(new ItemStack(Spacetime.obsidianShard, 4), new Object[] { "#", Character.valueOf('#'),
                Block.obsidian });
        GameRegistry.addRecipe(new ItemStack(Block.obsidian), new Object[] { "##", "##", Character.valueOf('#'),
                Spacetime.obsidianShard });
        
        GameRegistry.addRecipe(new ItemStack(Spacetime.crossbowBolt, 1),
                new Object[] { "^", "|", "|", Character.valueOf('^'), Spacetime.obsidianShard, Character.valueOf('|'),
                        Spacetime.ironRod });
        
        NBTTagCompound compound = new NBTTagCompound();
        compound.setString("SkullOwner", "Rekkyn");
        ItemStack rekkynHead = new ItemStack(Item.skull, 1, 3);
        rekkynHead.setTagCompound(compound);
        
        GameRegistry.addRecipe(rekkynHead, new Object[] { "xxx", "xFx", "xxx", Character.valueOf('x'),
                Item.rottenFlesh, Character.valueOf('F'), Spacetime.spacetimeFluctuation });
        
        GameRegistry.addShapelessRecipe(new ItemStack(Spacetime.spacetimeJar, 1, 1), Spacetime.spacetimeFluctuation,
                Spacetime.spacetimeJar);
        
        GameRegistry.addRecipe(new ItemStack(Spacetime.spacetimeInfuser), "PPP", "PFP", "OOO", 'P',
                Spacetime.spacetimePanel, 'F', Spacetime.spacetimeFluctuation, 'O', Block.obsidian);
        
        GameRegistry.addRecipe(new ItemStack(Spacetime.spacetimePanel), "OIO", "IDI", "OIO", 'I', Item.ingotIron, 'D',
                Item.diamond, 'O', Block.obsidian);
        
        GameRegistry.addRecipe(new ItemStack(Spacetime.spacetimeJar), "GGG", "P P", "PPP", 'G', Spacetime.spacetimeGem, 'P',
                Spacetime.spacetimePanel);
        
    }
}