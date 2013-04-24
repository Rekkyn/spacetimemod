package rekkyn.spacetime.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IArmorTextureProvider;
import rekkyn.spacetime.Spacetime;

public class SpacetimeArmor extends ItemArmor implements IArmorTextureProvider {
    
    public SpacetimeArmor(int id, EnumArmorMaterial material, int armor, int slot) {
        super(id, material, armor, slot);
    }
    
    @Override
    public String getArmorTextureFile(ItemStack itemstack) {
        if (itemstack.getItem() == Spacetime.spacetimeLegs) {
            return "/mods/Spacetime/textures/armor/spacetime_2.png";
        }
        return "/mods/Spacetime/textures/armor/spacetime_1.png";
        
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void updateIcons(IconRegister iconRegister) {

        iconIndex = iconRegister.registerIcon("Spacetime:" + this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1));
    }

    
}
