package rekkyn.spacetime.inventory;

import java.util.Iterator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

public class ContainerSpacetimeInfuser extends Container {
    protected TileSpacetimeInfuser tileEntity;
    private int lastInfuseTime = 0;
    
    public ContainerSpacetimeInfuser(TileSpacetimeInfuser tileEntity, InventoryPlayer playerInventory) {
        this.tileEntity = tileEntity;
        addSlotToContainer(new Slot(tileEntity, 0, 40, 19));
        addSlotToContainer(new Slot(tileEntity, 1, 40, 51));
        addSlotToContainer(new SlotFurnace(playerInventory.player, tileEntity, 2, 120, 35));
        
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
    
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        Iterator iterator = crafters.iterator();
        while (iterator.hasNext()) {
            ICrafting crafting = (ICrafting) iterator.next();
            if (lastInfuseTime != tileEntity.infuseTime) {
                crafting.sendProgressBarUpdate(this, 0, tileEntity.infuseTime);
            }
        }
        lastInfuseTime = tileEntity.infuseTime;
    }
    
    @Override
    public void updateProgressBar(int par1, int par2) {
        if (par1 == 0) {
            tileEntity.infuseTime = par2;
        }
    }
    
}
