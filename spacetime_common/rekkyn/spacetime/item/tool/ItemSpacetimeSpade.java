package rekkyn.spacetime.item.tool;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import rekkyn.spacetime.Spacetime;

public class ItemSpacetimeSpade extends ItemSpade {
    
    public ItemSpacetimeSpade(int id, EnumToolMaterial material) {
        super(id, material);
    }
    
    @Override
    public void registerIcons(IconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon("Spacetime:spacetimeShovel");
    }
    
    @Override
    public boolean getIsRepairable(ItemStack tool, ItemStack repairItem) {
        return repairItem.itemID == Spacetime.spacetimeGem.itemID;
    }
    
}
