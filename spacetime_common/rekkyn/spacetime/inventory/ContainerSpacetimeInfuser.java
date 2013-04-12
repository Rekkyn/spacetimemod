package rekkyn.spacetime.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

public class ContainerSpacetimeInfuser extends Container {
    protected TileSpacetimeInfuser tileEntity;
    
    public ContainerSpacetimeInfuser(TileSpacetimeInfuser tileEntity, InventoryPlayer playerInventory) {
        this.tileEntity = tileEntity;
        addSlotToContainer(new Slot(tileEntity, 0, 40, 19));
        addSlotToContainer(new Slot(tileEntity, 2, 40, 51));
        addSlotToContainer(new SlotFurnace(playerInventory.player, tileEntity, 3, 120, 35));
        
        bindPlayerInventory(playerInventory);
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return tileEntity.isUseableByPlayer(player);
    }
    
    protected void bindPlayerInventory(InventoryPlayer player_inventory) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(player_inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
                
            }
        }
        
        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(player_inventory, i, 8 + i * 18, 142));
        }
        
    }
    
    /*
     * @Override public ItemStack transferStackInSlot(EntityPlayer player, int
     * slotIndex) { ItemStack stack = null; Slot slotObject = (Slot)
     * inventorySlots.get(slotIndex);
     * 
     * if (slotObject != null && slotObject.getHasStack()) { ItemStack
     * stackInSlot = slotObject.getStack(); stack = stackInSlot.copy();
     * 
     * if (slotIndex == 0) { if (!mergeItemStack(stackInSlot, 1,
     * inventorySlots.size(), true)) { return null; } } else if
     * (!mergeItemStack(stackInSlot, 0, 1, false)) { return null; }
     * 
     * if (stackInSlot.stackSize == 0) { slotObject.putStack(null); } else {
     * slotObject.onSlotChanged(); } }
     * 
     * return stack; }
     */
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack itemstack = null;
        Slot slot = (Slot) inventorySlots.get(par2);
        
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();
            
            if (par2 == 2) {
                if (!this.mergeItemStack(stack, 3, 39, true)) {
                    return null;
                }
                
                slot.onSlotChange(stack, itemstack);
                
            } else if (par2 != 1 && par2 != 0) {
                if (!this.mergeItemStack(stack, 0, 2, false)) {
                    return null;
                }
            } else if (par2 >= 3 && par2 < 30) {
                if (!this.mergeItemStack(stack, 30, 39, false)) {
                    return null;
                }
            } else if (par2 >= 30 && par2 < 39 && !this.mergeItemStack(stack, 3, 30, false)) {
                return null;
            }
            
            else if (!this.mergeItemStack(stack, 3, 39, false)) {
                return null;
            }
            
            if (stack.stackSize == 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }
            
            if (stack.stackSize == itemstack.stackSize) {
                return null;
            }
            
            slot.onPickupFromSlot(par1EntityPlayer, stack);
        }
        
        return itemstack;
    }
}
