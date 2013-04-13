package rekkyn.spacetime.inventory;

import java.util.HashMap;
import java.util.Map;

import rekkyn.spacetime.Spacetime;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InfuserRecipes {
    
    public static final InfuserRecipes infusing() {
        return infusingBase;
    }
    
    private InfuserRecipes() {
    }
    
    
    public ItemStack getInfusingResult(ItemStack i, ItemStack j) {
        if (i.getItem() == Item.diamond || j.getItem() == Item.diamond) {
            if (i.getItem() == Spacetime.spacetimeFluctuation || j.getItem() == Spacetime.spacetimeFluctuation) {
                return new ItemStack(Spacetime.spacetimeGem);
            }

        }
        return null;
    }
        
    private static final InfuserRecipes infusingBase = new InfuserRecipes();
    
}
