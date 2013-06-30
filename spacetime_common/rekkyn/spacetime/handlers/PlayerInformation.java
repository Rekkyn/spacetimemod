package rekkyn.spacetime.handlers;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import rekkyn.spacetime.inventory.InventorySpacetimeChest;

public class PlayerInformation implements IExtendedEntityProperties {
    public static final String IDENTIFIER = "spacetime";
    
    public static PlayerInformation forPlayer(Entity player) {
        return (PlayerInformation) player.getExtendedProperties(IDENTIFIER);
    }
    
    private InventorySpacetimeChest chest = new InventorySpacetimeChest();
    
    @Override
    public void init(Entity entity, World world) {
    }
    
    @Override
    public void saveNBTData(NBTTagCompound compound) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag("SpacetimeItems", chest.saveInventoryToNBT());
        compound.setCompoundTag(IDENTIFIER, nbt);
    }
    
    @Override
    public void loadNBTData(NBTTagCompound playerNbt) {
        NBTTagCompound nbt = playerNbt.getCompoundTag(IDENTIFIER);
        if (nbt.hasKey("SpacetimeItems"))
        {
            NBTTagList taglist = nbt.getTagList("SpacetimeItems");
            chest.loadInventoryFromNBT(taglist);
        }
    }
    
    public InventorySpacetimeChest getSpacetimeChest() {
        return chest;
    }
}