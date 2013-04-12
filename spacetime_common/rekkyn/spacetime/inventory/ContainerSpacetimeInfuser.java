package rekkyn.spacetime.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSpacetimeInfuser extends Container {
    protected TileSpacetimeInfuser tileEntity;
    
    public ContainerSpacetimeInfuser(TileSpacetimeInfuser tileEntity, InventoryPlayer playerInventory) {
        this.tileEntity = tileEntity;
        addSlotToContainer(new Slot(tileEntity, 0, 44, 18));
        addSlotToContainer(new Slot(tileEntity, 2, 44, 61));
        addSlotToContainer(new Slot(tileEntity, 3, 120, 39));
        
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
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
        ItemStack stack = null;
        Slot slotObject = (Slot) inventorySlots.get(slotIndex);
        
        if (slotObject != null && slotObject.getHasStack()) {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();
            
            if (slotIndex == 0) {
                if (!mergeItemStack(stackInSlot, 1, inventorySlots.size(), true)) {
                    return null;
                }
            } else if (!mergeItemStack(stackInSlot, 0, 1, false)) {
                return null;
            }
            
            if (stackInSlot.stackSize == 0) {
                slotObject.putStack(null);
            } else {
                slotObject.onSlotChanged();
            }
        }
        
        return stack;
    }
}
