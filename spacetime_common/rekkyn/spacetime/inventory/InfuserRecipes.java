package rekkyn.spacetime.inventory;

import java.util.Map;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import rekkyn.spacetime.Spacetime;

public class InfuserRecipes {
    
    public static final InfuserRecipes infusing() {
        return infusingBase;
    }
    
    private InfuserRecipes() {
    }
    
    public ItemStack getInfusingResult(ItemStack i, ItemStack j) {
        ItemStack diamondTool = null;
        ItemStack spacetimeTool = null;
        ItemStack spacetimeToolOutput = null;
        
        if (i.getItem() == Item.diamond || j.getItem() == Item.diamond) {
            if (i.getItem() == Spacetime.spacetimeFluctuation || j.getItem() == Spacetime.spacetimeFluctuation) {
                return new ItemStack(Spacetime.spacetimeGem);
            }
            
        }
        
        if (i.getItem() == Item.pickaxeDiamond && j.getItem() == Spacetime.spacetimePick) {
            diamondTool = i;
            spacetimeTool = j;
        } else if (i.getItem() == Spacetime.spacetimePick && j.getItem() == Item.pickaxeDiamond) {
            diamondTool = j;
            spacetimeTool = i;
        } else if (i.getItem() == Item.swordDiamond && j.getItem() == Spacetime.spacetimeSword) {
            diamondTool = i;
            spacetimeTool = j;
        } else if (i.getItem() == Spacetime.spacetimeSword && j.getItem() == Item.swordDiamond) {
            diamondTool = j;
            spacetimeTool = i;
        } else if (i.getItem() == Item.shovelDiamond && j.getItem() == Spacetime.spacetimeShovel) {
            diamondTool = i;
            spacetimeTool = j;
        } else if (i.getItem() == Spacetime.spacetimeShovel && j.getItem() == Item.shovelDiamond) {
            diamondTool = j;
            spacetimeTool = i;
        } else if (i.getItem() == Item.axeDiamond && j.getItem() == Spacetime.spacetimeAxe) {
            diamondTool = i;
            spacetimeTool = j;
        } else if (i.getItem() == Spacetime.spacetimeAxe && j.getItem() == Item.axeDiamond) {
            diamondTool = j;
            spacetimeTool = i;
        }
        
        if (diamondTool != null && spacetimeTool != null) {
            Map enchants = EnchantmentHelper.getEnchantments(diamondTool);
            if (enchants.isEmpty()) {
                return null;
            }
            spacetimeToolOutput = spacetimeTool.copy();
            if (diamondTool.hasDisplayName() && !spacetimeTool.hasDisplayName()) {
                String name = diamondTool.getDisplayName();
                spacetimeToolOutput.setItemName(name);
            }
            EnchantmentHelper.setEnchantments(enchants, spacetimeToolOutput);
            return spacetimeToolOutput;
        }
        
        return null;
    }
    
    private static final InfuserRecipes infusingBase = new InfuserRecipes();
    
}
