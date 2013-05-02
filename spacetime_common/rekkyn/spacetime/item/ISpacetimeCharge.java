package rekkyn.spacetime.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ISpacetimeCharge {
    
    void onUpdate(ItemStack itemstack, World world, Entity player, int par4, boolean par5);
    
    boolean getShareTag();
    
    boolean changeCharge(ItemStack itemstack, int x);
    
    int getSpacetimeCharge();
    
    int getSpacetimeMaxCharge();
}
