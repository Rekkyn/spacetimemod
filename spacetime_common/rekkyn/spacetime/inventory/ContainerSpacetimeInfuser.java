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
        int o = 0;
        for (int q = 0; q < 3; q++) {
            for (int p = 0; p < 9; p++) {
                
                addSlotToContainer(new Slot(tileEntity, o, 9 + p * 18, 9 + q * 18));
                
                bindPlayerInventory(playerInventory);
                o++;
            }
        }
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
