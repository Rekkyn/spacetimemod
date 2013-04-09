package rekkyn.spacetime.block;

import java.util.Random;

import rekkyn.spacetime.Spacetime;
import rekkyn.spacetime.inventory.TileSpacetimeInfuser;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSpacetimeInfuser extends BlockContainer {
    
    public BlockSpacetimeInfuser(int id) {
        super(id, Material.rock);
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float g,
            float t) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (tileEntity == null || player.isSneaking()) {
            return false;
        }
        player.openGui(Spacetime.instance, 0, world, x, y, z);
        return true;
    }
    
    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileSpacetimeInfuser();
    }
    
    
    @Override
    public void breakBlock(World world, int x, int y, int z, int i, int j){
            dropItems(world, x, y, z);
            super.breakBlock(world, x, y, z, i, j);
            }
   
   
    private void dropItems(World world, int x, int y, int z){
            Random rand = new Random();
           
            TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
           
            if(!(tileEntity instanceof IInventory)){
                    return;
            }
   
            IInventory inventory = (IInventory) tileEntity;
   
            for(int i = 0; i < inventory.getSizeInventory(); i++){
                    ItemStack item = inventory.getStackInSlot(i);
                   
                    if(item != null && item.stackSize > 0){
                    float rx = rand.nextFloat() * 0.6F + 0.1F;
                    float ry = rand.nextFloat() * 0.6F + 0.1F;
                    float rz = rand.nextFloat() * 0.6F + 0.1F;
                   
                    EntityItem itemEntity = new EntityItem(world, x + rx, y + ry, z + rz, new ItemStack(item.itemID, item.stackSize, item.getItemDamage()));
                   
                    if(item.hasTagCompound()){
                            itemEntity.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
                    }
   
                    float factor = 0.5F;
                   
                    itemEntity.motionX = rand.nextGaussian() * factor;
                    itemEntity.motionY = rand.nextGaussian() * factor + 0.2F;
                    itemEntity.motionZ = rand.nextGaussian() * factor;
                    world.spawnEntityInWorld(itemEntity);
                    item.stackSize = 0;
                    }
            }
    }

}
