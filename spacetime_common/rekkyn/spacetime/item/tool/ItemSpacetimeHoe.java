package rekkyn.spacetime.item.tool;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import rekkyn.spacetime.Spacetime;
import rekkyn.spacetime.handlers.SpacetimeChargeHandler;
import rekkyn.spacetime.item.ISpacetimeCharge;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSpacetimeHoe extends ItemHoe implements ISpacetimeCharge {
    
    public static final int spacetimeMaxCharge = 50;
    public static final int useAmount = 200;
    
    public ItemSpacetimeHoe(int id, EnumToolMaterial material) {
        super(id, material);
    }
    
    @Override
    public void registerIcons(IconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon("Spacetime:spacetimeHoe");
    }
    
    @Override
    public boolean getIsRepairable(ItemStack tool, ItemStack repairItem) {
        return repairItem.itemID == Spacetime.spacetimeGem.itemID;
    }
    
    @Override
    public void onPlayerStoppedUsing(ItemStack item, World world, EntityPlayer player, int chargetime) {
        int i = this.getMaxItemUseDuration(item) - chargetime;
        float charge = i / 20.0F;
        charge = (charge * charge + charge * 2.0F) / 3.0F;
        
        if (charge < 1.0D) { return; }

        if (charge > 1.0F) {
            charge = 1.0F;
        }
        
        ItemStack firework = new ItemStack(Item.firework);
        
        NBTTagCompound compound = new NBTTagCompound();
        NBTTagCompound fireworkCompound = new NBTTagCompound("Fireworks");
        NBTTagList explosionList = new NBTTagList("Explosions");
        
        NBTTagCompound explosionCompound = new NBTTagCompound("Explosion");
        
        ArrayList<Integer> arraylist = new ArrayList<Integer>();
        arraylist.add(Integer.valueOf(ItemDye.dyeColors[4]));
        arraylist.add(Integer.valueOf(ItemDye.dyeColors[14]));
        int[] aint = new int[arraylist.size()];
        
        for (int j = 0; j < aint.length; ++j) {
            aint[j] = arraylist.get(j).intValue();
        }
        explosionCompound.setIntArray("Colors", aint);
        explosionCompound.setByte("Type", (byte) 4);
        explosionCompound.setBoolean("Flicker", true);
        explosionCompound.setBoolean("Trail", true);
        
        explosionList.appendTag(explosionCompound);
        
        fireworkCompound.setByte("Flight", (byte) 2);
        fireworkCompound.setTag("Explosions", explosionList);
        
        compound.setTag("Fireworks", fireworkCompound);
        
        firework.setTagCompound(compound);
        
        if (charge == 1.0F) {
            EntityFireworkRocket entityfireworkrocket = new EntityFireworkRocket(world, player.posX, player.posY,
                    player.posZ, firework);
            
            if (!world.isRemote) {
                world.spawnEntityInWorld(entityfireworkrocket);
            }
            player.mountEntity(entityfireworkrocket);
        }
        
        SpacetimeChargeHandler.addChargeToTotal(player, -useAmount);

    }
    
    /*
     * @Override public ItemStack onEaten(ItemStack par1ItemStack, World
     * par2World, EntityPlayer par3EntityPlayer) { return par1ItemStack; }
     */
    
    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 72000;
    }
    
    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.bow;
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
        if (SpacetimeChargeHandler.getCurrentCharge(player) >= useAmount || player.capabilities.isCreativeMode) {
        player.setItemInUse(item, this.getMaxItemUseDuration(item));
        }
        return item;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return false;
    }

    @Override
    public int getSpacetimeMaxCharge() {
        return spacetimeMaxCharge;
    }
    
    @Override
    public int getUseAmount() {
        return useAmount;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
        list.add(SpacetimeChargeHandler.getSpacetimeCharge(itemstack) + "/" + spacetimeMaxCharge);
    }
    
    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity player, int par4, boolean par5) {
        if (((EntityPlayer) player).getItemInUse() != itemstack) {
                SpacetimeChargeHandler.changeCharge(itemstack, 1);
        }
    }


    
}
