package rekkyn.spacetime.item.tool;

import java.util.ArrayList;

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
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import rekkyn.spacetime.Spacetime;
import rekkyn.spacetime.particles.ParticleEffects;

public class ItemSpacetimeHoe extends ItemHoe {
    
    public static final int spacetimeMaxCharge = 100;
    public static final int useAmount = -50;
    public int spacetimeCharge = 100;
    
    public ItemSpacetimeHoe(int id, EnumToolMaterial material) {
        super(id, material);
    }
    
    @Override
    public void updateIcons(IconRegister iconRegister) {
        iconIndex = iconRegister.registerIcon("Spacetime:spacetimeHoe");
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
        
        player.setItemInUse(item, this.getMaxItemUseDuration(item));
        
        if (changeCharge(item, useAmount)) {

        
        for (int l = 0; l < 32; ++l) {
            double d1 = player.posY + world.rand.nextFloat();
            int randx = world.rand.nextInt(2) * 2 - 1;
            int randz = world.rand.nextInt(2) * 2 - 1;
            double d4 = (world.rand.nextFloat() - 0.5D) * 0.5D;
            
            double d0 = player.posX + 0.5D + 0.25D * randx;
            double d3 = world.rand.nextFloat() * 2.0F * randx;
            double d2 = player.posZ + 0.5D + 0.25D * randz;
            double d5 = world.rand.nextFloat() * 2.0F * randz;
            
            ParticleEffects.spawnParticle("blue", d0, d1, d2, d3, d4, d5);
            world.spawnParticle("smoke", d0, d1, d2, d3, d4, d5);
            if (l % 4 == 0) {
                ParticleEffects.spawnParticle("orange", d0, d1, d2, d3, d4, d5);
            }
            
        }
        
        if (!player.isSneaking()) {
            player.addVelocity(-MathHelper.sin(player.rotationYaw * (float) Math.PI / 180.0F) * 1.0F,
                    -MathHelper.sin(player.rotationPitch / 180.0F * (float) Math.PI) * 0.2F + 0.4,
                    MathHelper.cos(player.rotationYaw * (float) Math.PI / 180.0F) * 1.0F);
        } else {
            player.addVelocity(MathHelper.sin(player.rotationYaw * (float) Math.PI / 180.0F) * 1.0F,
                    MathHelper.sin(player.rotationPitch / 180.0F * (float) Math.PI) * 0.2F + 0.4,
                    -MathHelper.cos(player.rotationYaw * (float) Math.PI / 180.0F) * 1.0F);
        }
        player.fallDistance = 0;
        }
        
        return item;
    }
    
    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity player, int par4, boolean par5) {
        
        changeCharge(itemstack, 1);
        System.out.println(spacetimeCharge);

    }
    
    @Override
    public void onCreated(ItemStack itemstack, World world, EntityPlayer player) {
        if (itemstack.stackTagCompound == null) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
    }
    
    @Override
    public boolean getShareTag() {
        return true;
    }
    
    public boolean changeCharge(ItemStack itemstack, int x) {
         spacetimeCharge = itemstack.stackTagCompound.getInteger("SpacetimeCharge");
         if (spacetimeCharge + x < 0) return false;
         spacetimeCharge += x;
         if (spacetimeCharge > spacetimeMaxCharge) spacetimeCharge = spacetimeMaxCharge;
         itemstack.stackTagCompound.setInteger("SpacetimeCharge", spacetimeCharge);
         return true;
    }
    
}
