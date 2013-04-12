package rekkyn.spacetime.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileSpacetimeInfuser extends TileEntity implements IInventory {
    
    private ItemStack[] inventory;
    
    public TileSpacetimeInfuser() {
        inventory = new ItemStack[2];
    }
    
    @Override
    public int getSizeInventory() {
        return inventory.length;
    }
    
    @Override
    public ItemStack getStackInSlot(int slotIndex) {
        return inventory[slotIndex];
    }
    
    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        inventory[slot] = stack;
        
        if (stack != null && stack.stackSize > getInventoryStackLimit()) {
            stack.stackSize = getInventoryStackLimit();
        }
    }
    
    @Override
    public ItemStack decrStackSize(int slotIndex, int amount) {
        
        ItemStack stack = getStackInSlot(slotIndex);
        
        if (stack != null) {
            
            if (stack.stackSize <= amount) {
                setInventorySlotContents(slotIndex, null);
            } else {
                stack = stack.splitStack(amount);
                if (stack.stackSize == 0) {
                    setInventorySlotContents(slotIndex, null);
                }
            }
        }
        
        return stack;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(int slotIndex) {
        
        ItemStack stack = getStackInSlot(slotIndex);
        
        if (stack != null) {
            setInventorySlotContents(slotIndex, null);
        }
        
        return stack;
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this
                && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
    }
    
    @Override
    public void openChest() {
    }
    
    @Override
    public void closeChest() {
    }
    
    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        
        NBTTagList tagList = tagCompound.getTagList("Inventory");
        
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
            
            byte slot = tag.getByte("Slot");
            
            if (slot >= 0 && slot < inventory.length) {
                inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
            }
        }
    }
    
    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        
        NBTTagList itemList = new NBTTagList();
        
        for (int i = 0; i < inventory.length; i++) {
            ItemStack stack = inventory[i];
            
            if (stack != null) {
                NBTTagCompound tag = new NBTTagCompound();
                
                tag.setByte("Slot", (byte) i);
                stack.writeToNBT(tag);
                itemList.appendTag(tag);
            }
        }
        
        tagCompound.setTag("Inventory", itemList);
    }
    
    @Override
    public String getInvName() {
        return "TileSpacetimeInfuser";
    }
    
    @Override
    public boolean isInvNameLocalized() {
        return true;
    }
    
    @Override
    public boolean isStackValidForSlot(int i, ItemStack itemstack) {
        return true;
    }
    
    private boolean canInfuse() {
        if (inventory[0] == null) {
            return false;
        } else {
            ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(inventory[0]);
            if (itemstack == null) {
                return false;
            }
            if (inventory[2] == null) {
                return true;
            }
            if (!inventory[2].isItemEqual(itemstack)) {
                return false;
            }
            int result = inventory[2].stackSize + itemstack.stackSize;
            return result <= getInventoryStackLimit() && result <= itemstack.getMaxStackSize();
        }
    }
    
    /**
     * Turn one item from the furnace source stack into the appropriate smelted
     * item in the furnace result stack
     */
    public void infuseItem() {
        if (this.canInfuse()) {
            ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(inventory[0]);
            
            if (inventory[2] == null) {
                inventory[2] = itemstack.copy();
            } else if (inventory[2].isItemEqual(itemstack)) {
                inventory[2].stackSize += itemstack.stackSize;
            }
            
            --inventory[0].stackSize;
            
            if (inventory[0].stackSize <= 0) {
                inventory[0] = null;
            }
        }
    }
    
}
