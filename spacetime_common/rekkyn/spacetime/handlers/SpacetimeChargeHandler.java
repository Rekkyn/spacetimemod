package rekkyn.spacetime.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
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
            if (item instanceof ISpacetimeCharge) {
                charge += ((ISpacetimeCharge) item).getSpacetimeCharge();
            }
        }
        
        return charge;
        
    }
    
}
