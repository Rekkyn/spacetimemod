package rekkyn.spacetime;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemSpacetimeIngot extends Item {

    public ItemSpacetimeIngot(int id) {
        super(id);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack) {
        return true;
    }
    
}
