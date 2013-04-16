package rekkyn.spacetime.item.tool;

import rekkyn.spacetime.Spacetime;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;

public class ItemSpacetimePickaxe extends ItemPickaxe {
    
    public ItemSpacetimePickaxe(int id, EnumToolMaterial material) {
        super(id, material);
    }
    
    @Override
    public void updateIcons(IconRegister iconRegister) {
        iconIndex = iconRegister.registerIcon("Spacetime:spacetimePick");
    }
    
    public boolean getIsRepairable(ItemStack tool, ItemStack repairItem)
    {
        return repairItem.itemID == Spacetime.spacetimeGem.itemID;
    }
    
}
