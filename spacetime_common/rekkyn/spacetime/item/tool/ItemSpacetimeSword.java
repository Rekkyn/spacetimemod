package rekkyn.spacetime.item.tool;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSword;

public class ItemSpacetimeSword extends ItemSword {
    
    public ItemSpacetimeSword(int id, EnumToolMaterial material) {
        super(id, material);
    }
    
    @Override
    public void updateIcons(IconRegister iconRegister) {
        iconIndex = iconRegister.registerIcon("Spacetime:spacetimeSword");
    }
    
}