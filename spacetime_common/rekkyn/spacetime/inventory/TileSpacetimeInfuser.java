package rekkyn.spacetime.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileSpacetimeInfuser extends TileEntity implements ISidedInventory {
    
    private static final int totalInfuseTime = 20 * 10;
    
    private ItemStack[] inventory = new ItemStack[3];
    
    public int infuseTime;
    
    private static final int[] side1 = new int[] { 0 };
    private static final int[] side2 = new int[] { 2, 1 };
    private static final int[] side3 = new int[] { 1 };
    
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
        
        infuseTime = tagCompound.getShort("InfuseTime");
        
    }
    
    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setShort("InfuseTime", (short) infuseTime);
        
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
        return i != 2;
    }
    
    private boolean canInfuse() {
        if (inventory[0] == null || inventory[1] == null) {
            return false;
        } else {
            ItemStack itemstack = InfuserRecipes.infusing().getInfusingResult(inventory[0], inventory[1]);
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
    
    @SideOnly(Side.CLIENT)
    public int getProgressScaled(int par1) {
        return infuseTime * par1 / totalInfuseTime;
    }
    
    public void infuseItem() {
        if (this.canInfuse()) {
            ItemStack itemstack = InfuserRecipes.infusing().getInfusingResult(inventory[0], inventory[1]);
            
            if (inventory[2] == null) {
                inventory[2] = itemstack.copy();
            } else if (inventory[2].isItemEqual(itemstack)) {
                inventory[2].stackSize += itemstack.stackSize;
            }
            
            --inventory[0].stackSize;
            --inventory[1].stackSize;
            
            if (inventory[0].stackSize <= 0) {
                inventory[0] = null;
            }
            if (inventory[1].stackSize <= 0) {
                inventory[1] = null;
            }
            
        }
    }
    
    @Override
    public void updateEntity() {
        boolean infusing = false;
        if (!worldObj.isRemote) {
            if (this.canInfuse()) {
                ++infuseTime;
                
                if (infuseTime == totalInfuseTime) {
                    infuseTime = 0;
                    this.infuseItem();
                    infusing = true;
                }
            } else {
                infuseTime = 0;
            }
        }
        if (infusing) {
            this.onInventoryChanged();
        }
        
    }
    
    @Override
    public boolean func_102007_a(int slot, ItemStack item, int par3) {
        return this.isStackValidForSlot(slot, item);
    }
    
    @Override
    public boolean func_102008_b(int par1, ItemStack par2ItemStack, int par3) {
        return par3 != 0 || par1 != 1 || par2ItemStack.itemID == Item.bucketEmpty.itemID;
    }
    
    @Override
    public int[] getSizeInventorySide(int par1) {
        return par1 == 0 ? side2 : par1 == 1 ? side1 : side3;
    }
    
}
