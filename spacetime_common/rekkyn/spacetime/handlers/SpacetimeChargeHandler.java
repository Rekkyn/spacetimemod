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
        if (getCurrentCharge(player) < amount) { return false; }
        int numberOfItems = 0;
        int amountLeft = amount;
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
        
        for (int i = 0; i <= 4; i++) {
            if (player.getCurrentItemOrArmor(i) == null) {
                continue;
            }
            ItemStack itemstack = player.getCurrentItemOrArmor(i);
            Item item = itemstack.getItem();
            if (item instanceof ISpacetimeCharge) {
                System.out.println(i + "," + amountLeft + "," + numberOfItems);
                if (amountLeft == 0) {
                    break;
                }
                amountLeft -= ((ISpacetimeCharge) item).subtractToZero(itemstack, amountLeft / numberOfItems);
                numberOfItems--;
            }
            
        }
        return true;
    }
    
}
