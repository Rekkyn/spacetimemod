package rekkyn.spacetime.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import rekkyn.spacetime.Spacetime;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSpacetimeJar extends Item {
    
    @SideOnly(Side.CLIENT)
    private Icon[] icons;
    
    public static final String[] names = new String[] { "spacetimeJar", "spacetimeJar_full" };
    
    public ItemSpacetimeJar(int id) {
        super(id);
        setHasSubtypes(true);
        setMaxDamage(0);
    }
    
    @Override
    public String getUnlocalizedName(ItemStack item) {
        int i = MathHelper.clamp_int(item.getItemDamage(), 0, 1);
        if (i == 1) {
            return super.getUnlocalizedName() + ".full";
        } else {
            return super.getUnlocalizedName();
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(int itemID, CreativeTabs tabs, List list) {
        for (int i = 0; i < 2; ++i) {
            list.add(new ItemStack(itemID, 1, i));
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister register) {
        icons = new Icon[names.length];
        
        for (int i = 0; i < names.length; ++i) {
            icons[i] = register.registerIcon("Spacetime:" + names[i]);
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIconFromDamage(int damage) {
        int j = MathHelper.clamp_int(damage, 0, 1);
        return icons[j];
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
        if (item.getItemDamage() == 0) { return item; }
        item.setItemDamage(0);
        if (!world.isRemote) {
            player.dropPlayerItemWithRandomChoice(new ItemStack(Spacetime.spacetimeFluctuation), false);
        }
        return item;
    }
    
}
