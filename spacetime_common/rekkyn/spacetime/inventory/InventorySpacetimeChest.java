package rekkyn.spacetime.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class InventorySpacetimeChest extends InventoryBasic {
    private TileSpacetimeChest associatedChest;
    
    public InventorySpacetimeChest() {
        super("Spacetime Chest", false, 54);
    }
    
    public void setAssociatedChest(TileSpacetimeChest chest) {
        associatedChest = chest;
    }
    
    public void loadInventoryFromNBT(NBTTagList taglist) {
        int i;        
        for (i = 0; i < this.getSizeInventory(); ++i) {
            this.setInventorySlotContents(i, (ItemStack) null);
        }
        
        for (i = 0; i < taglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound = (NBTTagCompound) taglist.tagAt(i);
            int j = nbttagcompound.getByte("Slot") & 255;
            
            if (j >= 0 && j < this.getSizeInventory()) {
                this.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(nbttagcompound));
            }
        }
    }
    
    public NBTTagList saveInventoryToNBT() {
        NBTTagList nbttaglist = new NBTTagList("SpacetimeItems");
        
        for (int i = 0; i < this.getSizeInventory(); ++i) {
            ItemStack itemstack = this.getStackInSlot(i);
            
            if (itemstack != null) {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte) i);
                itemstack.writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }
        
        return nbttaglist;
    }
    
    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return associatedChest != null && !associatedChest.isUseableByPlayer(player) ? false : super
                .isUseableByPlayer(player);
    }
    
    @Override
    public void openChest() {
        if (associatedChest != null) {
            associatedChest.openChest();
        }
        
        super.openChest();
    }
    
    @Override
    public void closeChest() {
        if (associatedChest != null) {
            associatedChest.closeChest();
        }
        
        super.closeChest();
        associatedChest = null;
    }
    
    @Override
    public boolean isStackValidForSlot(int par1, ItemStack par2ItemStack) {
        return true;
    }
}
