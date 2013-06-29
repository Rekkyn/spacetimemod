package rekkyn.spacetime.block;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import rekkyn.spacetime.Spacetime;
import rekkyn.spacetime.inventory.TileSpacetimeInfuser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSpacetimeInfuser extends BlockContainer {
    
    private final boolean isActive;
    private static boolean keepInventory = false;
    @SideOnly(Side.CLIENT)
    private Icon iconTop;
    @SideOnly(Side.CLIENT)
    private Icon iconFront;
    private final Minecraft mc = Minecraft.getMinecraft();
    
    public BlockSpacetimeInfuser(int id, boolean isActive) {
        super(id, Material.rock);
        this.isActive = isActive;
    }
    
    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
        return Spacetime.spacetimeInfuser.blockID;
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float g,
            float t) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (tileEntity == null || player.isSneaking()) { return false; }
        player.openGui(Spacetime.instance, 0, world, x, y, z);
        return true;
    }
    
    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileSpacetimeInfuser();
    }
    
    @Override
    public void breakBlock(World world, int x, int y, int z, int i, int j) {
        if (!keepInventory) {
            dropItems(world, x, y, z);
        }
        super.breakBlock(world, x, y, z, i, j);
    }
    
    private void dropItems(World world, int x, int y, int z) {
        Random rand = new Random();
        
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        
        if (!(tileEntity instanceof IInventory)) { return; }
        
        IInventory inventory = (IInventory) tileEntity;
        
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack item = inventory.getStackInSlot(i);
            
            if (item != null && item.stackSize > 0) {
                float rx = rand.nextFloat() * 0.6F + 0.1F;
                float ry = rand.nextFloat() * 0.6F + 0.1F;
                float rz = rand.nextFloat() * 0.6F + 0.1F;
                
                EntityItem itemEntity = new EntityItem(world, x + rx, y + ry, z + rz, new ItemStack(item.itemID,
                        item.stackSize, item.getItemDamage()));
                
                if (item.hasTagCompound()) {
                    itemEntity.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
                }
                
                float factor = 0.05F;
                
                itemEntity.motionX = rand.nextGaussian() * factor;
                itemEntity.motionY = rand.nextGaussian() * factor + 0.2F;
                itemEntity.motionZ = rand.nextGaussian() * factor;
                world.spawnEntityInWorld(itemEntity);
                item.stackSize = 0;
            }
        }
    }
    
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entity, ItemStack item) {
        int l = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        
        if (l == 0) {
            world.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }
        
        if (l == 1) {
            world.setBlockMetadataWithNotify(x, y, z, 5, 2);
        }
        
        if (l == 2) {
            world.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }
        
        if (l == 3) {
            world.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }
        
        if (item.hasDisplayName()) {
            ((TileSpacetimeInfuser) world.getBlockTileEntity(x, y, z)).setDisplayName(item.getDisplayName());
        }
    }
    
    public static void updateBlockState(boolean active, World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        TileEntity tileentity = world.getBlockTileEntity(x, y, z);
        keepInventory = true;
        
        if (active) {
            world.setBlock(x, y, z, Spacetime.spacetimeInfuserActive.blockID);
        } else {
            world.setBlock(x, y, z, Spacetime.spacetimeInfuser.blockID);
        }
        
        keepInventory = false;
        world.setBlockMetadataWithNotify(x, y, z, meta, 2);
        
        if (tileentity != null) {
            tileentity.validate();
            world.setBlockTileEntity(x, y, z, tileentity);
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public int idPicked(World world, int par2, int par3, int par4) {
        return Spacetime.spacetimeInfuser.blockID;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        if (isActive) {
            if (rand.nextInt(20) == 0) {
                playSound(x + 0.5D, y + 0.5D, z + 0.5D, "portal.travel", 0.05F, rand.nextFloat() * 0.4F + 0.8F, false);
            }
            int meta = world.getBlockMetadata(x, y, z);
            float f = x + 0.5F;
            float f1 = y + 0.0F + rand.nextFloat() * 6.0F / 16.0F;
            float f2 = z + 0.5F;
            float f3 = 0.52F;
            float f4 = rand.nextFloat() * 0.6F - 0.3F;
            
            if (meta == 4) {
                world.spawnParticle("smoke", (f - f3), f1, (f2 + f4), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", (f - f3), f1, (f2 + f4), 0.0D, 0.0D, 0.0D);
            } else if (meta == 5) {
                world.spawnParticle("smoke", (f + f3), f1, (f2 + f4), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", (f + f3), f1, (f2 + f4), 0.0D, 0.0D, 0.0D);
            } else if (meta == 2) {
                world.spawnParticle("smoke", (f + f4), f1, (f2 - f3), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", (f + f4), f1, (f2 - f3), 0.0D, 0.0D, 0.0D);
            } else if (meta == 3) {
                world.spawnParticle("smoke", (f + f4), f1, (f2 + f3), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", (f + f4), f1, (f2 + f3), 0.0D, 0.0D, 0.0D);
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void playSound(double x, double y, double z, String sound, float volume, float pitch, boolean par10) {
        float distanceLimit = 2.0F;
        
        double distanceSq = mc.renderViewEntity.getDistanceSq(x, y, z);
        
        if (distanceSq < (distanceLimit * distanceLimit)) {
            mc.sndManager.playSound(sound, (float) x, (float) y, (float) z, volume, pitch);
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta) {
        return side == 1 ? iconTop : side == 0 ? iconTop : side == 3 ? iconFront : blockIcon;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        blockIcon = par1IconRegister.registerIcon("Spacetime:infuser_side");
        iconFront = par1IconRegister.registerIcon(isActive ? "Spacetime:infuser_front_active"
                : "Spacetime:infuser_front");
        iconTop = par1IconRegister.registerIcon("Spacetime:infuser_top");
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side)
    {
        int meta = world.getBlockMetadata(x, y, z);
        
        return side == 1 ? iconTop : side == 0 ? iconTop : side != meta ? blockIcon : iconFront;
    }

    
}
