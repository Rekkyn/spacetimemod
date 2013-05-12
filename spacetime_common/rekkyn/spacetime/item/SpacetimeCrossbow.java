package rekkyn.spacetime.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SpacetimeCrossbow extends ItemBow {
    
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
        
        boolean flag = player.capabilities.isCreativeMode
                || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, item) > 0;
        
        if (flag || player.inventory.hasItem(Spacetime.crossbowBolt.itemID)) {
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
            
            player.inventory.consumeInventoryItem(Spacetime.crossbowBolt.itemID);
            
            if (!world.isRemote) {
                world.spawnEntityInWorld(entitybolt);
            }
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
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        ArrowNockEvent event = new ArrowNockEvent(par3EntityPlayer, par1ItemStack);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled()) { return event.result; }
        
        if (par3EntityPlayer.capabilities.isCreativeMode
                || par3EntityPlayer.inventory.hasItem(Spacetime.crossbowBolt.itemID)) {
            par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        }
        
        return par1ItemStack;
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
    public void updateIcons(IconRegister iconRegister) {
        iconIndex = iconRegister.registerIcon("Spacetime:spacetimeCrossbow");
    }
    
}
