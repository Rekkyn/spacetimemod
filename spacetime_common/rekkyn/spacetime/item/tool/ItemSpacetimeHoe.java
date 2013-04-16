package rekkyn.spacetime.item.tool;

import rekkyn.spacetime.Spacetime;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;

public class ItemSpacetimeHoe extends ItemHoe {
    
    public ItemSpacetimeHoe(int id, EnumToolMaterial material) {
        super(id, material);
    }
    
    @Override
    public void updateIcons(IconRegister iconRegister) {
        iconIndex = iconRegister.registerIcon("Spacetime:spacetimeHoe");
    }
    
    public boolean getIsRepairable(ItemStack tool, ItemStack repairItem)
    {
        return repairItem.itemID == Spacetime.spacetimeGem.itemID;
    }

    
}
