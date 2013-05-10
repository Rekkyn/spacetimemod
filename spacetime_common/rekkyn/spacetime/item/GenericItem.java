package rekkyn.spacetime.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GenericItem extends Item {
    
    boolean hasEffect = false;
    
    public GenericItem(int id, boolean hasEffect) {
        super(id);
        this.hasEffect = hasEffect;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void updateIcons(IconRegister iconRegister) {
        
        iconIndex = iconRegister.registerIcon("Spacetime:"
                + this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1));
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack) {
        if (hasEffect) {
            return true;
        } else {
            return false;
        }
    }
    
}
