package rekkyn.spacetime;

import java.util.Random;
import net.minecraft.util.DamageSource;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSpacetimeFluctuation extends Item {
    
    Random rand = new Random();

    public ItemSpacetimeFluctuation(int id) {
        super(id);
    }
    
    public void onUpdate(ItemStack item, World world, Entity entity, int par4, boolean par5) {
        if (!world.isRemote) {
            if (rand.nextInt(6000)==0) {
                world.createExplosion(entity, entity.posX, entity.posY, entity.posZ, 3, true);
                entity.attackEntityFrom(DamageSource.setExplosionSource(null), 7);
            }
            
        }
            
    }

    
}
