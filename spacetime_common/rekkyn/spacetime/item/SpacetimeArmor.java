package rekkyn.spacetime.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IArmorTextureProvider;
import rekkyn.spacetime.Spacetime;
import rekkyn.spacetime.handlers.SpacetimeChargeHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SpacetimeArmor extends ItemArmor implements IArmorTextureProvider, ISpacetimeCharge {
    
    public int spacetimeMaxCharge;
    public final int useAmount = -50;
    
    public SpacetimeArmor(int id, EnumArmorMaterial material, int armor, int slot) {
        super(id, material, armor, slot);
        
        switch (slot) {
            case 0:
                spacetimeMaxCharge = 125;
                break;
            case 1:
                spacetimeMaxCharge = 200;
                break;
            case 2:
                spacetimeMaxCharge = 175;
                break;
            case 3:
                spacetimeMaxCharge = 100;
                break;
            default:
                spacetimeMaxCharge = 0;
                break;
        }
        
    }
    
    @Override
    public String getArmorTextureFile(ItemStack itemstack) {
        if (itemstack.getItem() == Spacetime.spacetimeLegs) { return "/mods/Spacetime/textures/armor/spacetime_2.png"; }
        return "/mods/Spacetime/textures/armor/spacetime_1.png";
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        
        itemIcon = iconRegister.registerIcon("Spacetime:"
                + this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1));
    }
    
    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity player, int par4, boolean par5) {
        if (player.ticksExisted % 7 == 0) {
            SpacetimeChargeHandler.changeCharge(itemstack, 1);
        }
    }
    
    @Override
    public boolean getShareTag() {
        return true;
    }
    
    
    @Override
    public int getSpacetimeMaxCharge() {
        return spacetimeMaxCharge;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
        list.add(SpacetimeChargeHandler.getSpacetimeCharge(itemstack) + "/" + spacetimeMaxCharge);
    }
    
    @Override
    public void onArmorTickUpdate(World worldObj, EntityPlayer player, ItemStack itemstack) {
        if (player.ticksExisted % 7 == 0) {
            SpacetimeChargeHandler.changeCharge(itemstack, 1);
        }
    }
        
    @Override
    public int getUseAmount() {
        return 0;
    }
    
}
