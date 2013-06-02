package rekkyn.spacetime.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
                charge += ((ISpacetimeCharge) item).getSpacetimeCharge(player.getCurrentItemOrArmor(i));
            }
        }
        
        return charge;
        
    }
    
    public static boolean subChargeFromTotal(EntityPlayer player, int amount) {
        if (player.capabilities.isCreativeMode) { return true; }
        if (getCurrentCharge(player) < amount) { return false; }
        int amountLeft = amount;
        while (amountLeft > 0) {
            int numberOfItems = 0;
            for (int i = 0; i <= 4; i++) {
                if (player.getCurrentItemOrArmor(i) == null) {
                    continue;
                }
                Item item = player.getCurrentItemOrArmor(i).getItem();
                if (item instanceof ISpacetimeCharge
                        && ((ISpacetimeCharge) item).getSpacetimeCharge(player.getCurrentItemOrArmor(i)) > 0) {
                    numberOfItems++;
                }
            }
            
            int subtractFromEach = amountLeft / numberOfItems;
            
            for (int i = 0; i <= 4; i++) {
                if (player.getCurrentItemOrArmor(i) == null) {
                    continue;
                }
                ItemStack itemstack = player.getCurrentItemOrArmor(i);
                Item item = itemstack.getItem();
                if (item instanceof ISpacetimeCharge) {
                    amountLeft -= ((ISpacetimeCharge) item).subtractToZero(itemstack, subtractFromEach);
                    if (subtractFromEach == 0) {
                        amountLeft = 0;
                    }
                }
                
            }
        }
        return true;
    }
    
}
