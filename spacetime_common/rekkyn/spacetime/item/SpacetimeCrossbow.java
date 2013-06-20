package rekkyn.spacetime.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import rekkyn.spacetime.Spacetime;
import rekkyn.spacetime.entity.EntityCrossbowBolt;
import rekkyn.spacetime.handlers.SpacetimeChargeHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SpacetimeCrossbow extends ItemBow implements ISpacetimeCharge {
    
    public static final int spacetimeMaxCharge = 100;
    public static final int useAmount = 400;
    
    public SpacetimeCrossbow(int id) {
        super(id);
        this.setMaxDamage(2842);
    }
    
    /**
     * called when the player releases the use item button. Args: itemstack,
     * world, entityplayer, itemInUseCount
     */
    @Override
    public void onPlayerStoppedUsing(ItemStack item, World world, EntityPlayer player, int par4) {
        int j = this.getMaxItemUseDuration(item) - par4;
        
        ArrowLooseEvent event = new ArrowLooseEvent(player, item, j);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled()) { return; }
        j = event.charge;
        
        boolean inCreative = player.capabilities.isCreativeMode
                || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, item) > 0;
        
        if (inCreative || player.inventory.hasItem(Spacetime.crossbowBolt.itemID)) {
            float f = j / 20.0F;
            f = (f * f + f * 2.0F) / 3.0F;
            
            if (f < 1.0D) { return; }
            
            if (f > 1.0F) {
                f = 1.0F;
            }
            
            EntityCrossbowBolt entitybolt = new EntityCrossbowBolt(world, player, f * 2.0F);
            
            int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, item);
            
            if (k > 0) {
                entitybolt.setDamage(entitybolt.getDamage() + k * 0.5D + 0.5D);
            }
            
            int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, item);
            
            if (l > 0) {
                entitybolt.setKnockbackStrength(l);
            }
            
            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, item) > 0) {
                entitybolt.setFire(100);
            }
            
            item.damageItem(1, player);
            world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
            
            if (!inCreative) {
                player.inventory.consumeInventoryItem(Spacetime.crossbowBolt.itemID);
            }
            
            if (!world.isRemote) {
                world.spawnEntityInWorld(entitybolt);
            }
            SpacetimeChargeHandler.addChargeToTotal(player, -useAmount);
        }
    }
    
    @Override
    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        return par1ItemStack;
    }
    
    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 72000;
    }
    
    /**
     * returns the action that specifies what animation to play when the items
     * is being used
     */
    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.bow;
    }
    
    /**
     * Called whenever this item is equipped and the right mouse button is
     * pressed. Args: itemStack, world, entityPlayer
     */
    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
        if (SpacetimeChargeHandler.getCurrentCharge(player) >= useAmount || player.capabilities.isCreativeMode) {
            ArrowNockEvent event = new ArrowNockEvent(player, item);
            MinecraftForge.EVENT_BUS.post(event);
            if (event.isCanceled()) { return event.result; }
            
            if (player.capabilities.isCreativeMode || player.inventory.hasItem(Spacetime.crossbowBolt.itemID)) {
                player.setItemInUse(item, this.getMaxItemUseDuration(item));
            }
        }
        
        return item;
    }
    
    /**
     * Return the enchantability factor of the item, most of the time is based
     * on material.
     */
    @Override
    public int getItemEnchantability() {
        return 0;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon("Spacetime:spacetimeCrossbow");
    }
    
    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity player, int par4, boolean par5) {
        if (!world.isRemote) {
            if (((EntityPlayer) player).isUsingItem()) {
                if (((EntityPlayer) player).getCurrentEquippedItem() != itemstack) {
                    if (player.ticksExisted % 5 == 0) {
                        SpacetimeChargeHandler.changeCharge(itemstack, 1);
                    }
                }
            } else if (player.ticksExisted % 5 == 0) {
                SpacetimeChargeHandler.changeCharge(itemstack, 1);
            }
        }
    }
    
    @Override
    public int getSpacetimeMaxCharge() {
        return spacetimeMaxCharge;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
        list.add(SpacetimeChargeHandler.getSpacetimeCharge(itemstack) + "/" + spacetimeMaxCharge);
    }
    
    @Override
    public int getUseAmount() {
        return useAmount;
    }
    
}
