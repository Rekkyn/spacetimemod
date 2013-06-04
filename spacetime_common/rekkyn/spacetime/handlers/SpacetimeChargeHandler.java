package rekkyn.spacetime.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import rekkyn.spacetime.item.ISpacetimeCharge;

public class SpacetimeChargeHandler {
    
    public static int getMaxCharge(EntityPlayer player) {
        int maxCharge = 0;
        
        for (int i = 0; i <= 4; i++) {
            if (player.getCurrentItemOrArmor(i) == null) {
                continue;
            }
            Item item = player.getCurrentItemOrArmor(i).getItem();
            if (item instanceof ISpacetimeCharge) {
                maxCharge += ((ISpacetimeCharge) item).getSpacetimeMaxCharge();
            }
        }
        
        return maxCharge;
        
    }
    
    public static int getCurrentCharge(EntityPlayer player) {
        int charge = 0;
        
        for (int i = 0; i <= 4; i++) {
            if (player.getCurrentItemOrArmor(i) == null) {
                continue;
            }
            Item item = player.getCurrentItemOrArmor(i).getItem();
            if (player.getCurrentItemOrArmor(i).getItem() instanceof ISpacetimeCharge) {
                charge += getSpacetimeCharge(player.getCurrentItemOrArmor(i));
            }
        }
        
        return charge;
        
    }
    
    public static boolean addChargeToTotal(EntityPlayer player, int amount) {
        if (player.capabilities.isCreativeMode) { return true; }
        if (getCurrentCharge(player) + amount < 0) { return false; }
        int amountLeft = amount;
        while (amountLeft != 0) {
            int numberOfItems = 0;
            for (int i = 0; i <= 4; i++) {
                if (player.getCurrentItemOrArmor(i) == null) {
                    continue;
                }
                Item item = player.getCurrentItemOrArmor(i).getItem();
                if (item instanceof ISpacetimeCharge && getSpacetimeCharge(player.getCurrentItemOrArmor(i)) > 0) {
                    numberOfItems++;
                }
            }
            
            int addToEach = amountLeft / numberOfItems;
            
            for (int i = 0; i <= 4; i++) {
                if (player.getCurrentItemOrArmor(i) == null) {
                    continue;
                }
                ItemStack itemstack = player.getCurrentItemOrArmor(i);
                Item item = itemstack.getItem();
                if (item instanceof ISpacetimeCharge) {
                    amountLeft -= changeChargeWithRemainder(itemstack, addToEach);
                    if (addToEach == 0 || getCurrentCharge(player) == getMaxCharge(player)) {
                        amountLeft = 0;
                    }
                }
                
            }
        }
        return true;
    }
    
    public static boolean changeCharge(ItemStack itemstack, int x) {
        if (itemstack.stackTagCompound == null) {
            itemstack.setTagCompound(new NBTTagCompound());
            itemstack.stackTagCompound.setInteger("SpacetimeCharge",
                    ((ISpacetimeCharge) itemstack.getItem()).getSpacetimeMaxCharge());
        }
        
        int spacetimeCharge = getSpacetimeCharge(itemstack);
        if (spacetimeCharge + x < 0) { return false; }
        spacetimeCharge += x;
        if (spacetimeCharge > ((ISpacetimeCharge) itemstack.getItem()).getSpacetimeMaxCharge()) {
            spacetimeCharge = ((ISpacetimeCharge) itemstack.getItem()).getSpacetimeMaxCharge();
        }
        itemstack.stackTagCompound.setInteger("SpacetimeCharge", spacetimeCharge);
        return true;
    }
    
    public static int getSpacetimeCharge(ItemStack itemstack) {
        if (itemstack.stackTagCompound == null) {
            itemstack.setTagCompound(new NBTTagCompound());
            itemstack.stackTagCompound.setInteger("SpacetimeCharge",
                    ((ISpacetimeCharge) itemstack.getItem()).getSpacetimeMaxCharge());
        }
        return itemstack.stackTagCompound.getInteger("SpacetimeCharge");
    }
    
    public static int changeChargeWithRemainder(ItemStack itemstack, int amount) {
        int maxCharge = ((ISpacetimeCharge) itemstack.getItem()).getSpacetimeMaxCharge();
        if (getSpacetimeCharge(itemstack) + amount <= 0) {
            int amountSubtracted = -getSpacetimeCharge(itemstack);
            changeCharge(itemstack, amountSubtracted);
            return amountSubtracted;
        } else if (getSpacetimeCharge(itemstack) + amount >= maxCharge) {
            int amountAdded = maxCharge - getSpacetimeCharge(itemstack);
            changeCharge(itemstack, amountAdded);
            return amountAdded;
        } else {
            changeCharge(itemstack, amount);
            return amount;
        }
    }
    
}
