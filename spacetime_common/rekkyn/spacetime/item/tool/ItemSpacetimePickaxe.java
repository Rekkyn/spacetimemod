package rekkyn.spacetime.item.tool;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import rekkyn.spacetime.Spacetime;

public class ItemSpacetimePickaxe extends ItemPickaxe {
    
    public ItemSpacetimePickaxe(int id, EnumToolMaterial material) {
        super(id, material);
    }
    
    @Override
    public void registerIcons(IconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon("Spacetime:spacetimePick");
    }
    
    @Override
    public boolean getIsRepairable(ItemStack tool, ItemStack repairItem) {
        return repairItem.itemID == Spacetime.spacetimeGem.itemID;
    }
    
}
