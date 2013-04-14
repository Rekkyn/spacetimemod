package rekkyn.spacetime.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class ItemIronRod extends Item {

    public ItemIronRod(int id) {
        super(id);
    }
    
    public void updateIcons(IconRegister iconRegister)
    {
             iconIndex = iconRegister.registerIcon("Spacetime:ironRod");
    }
    
}
