package rekkyn.spacetime;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SpacetimeFluctuationEntity extends EntityItem {
    
    public SpacetimeFluctuationEntity(World world, double x, double y, double z, ItemStack item) {
        super(world, x, y, z, item);
        delayBeforeCanPickup = 40;

    }
    
}
