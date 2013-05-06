package rekkyn.spacetime.handlers;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import rekkyn.spacetime.item.ISpacetimeCharge;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class HUDHandler implements ITickHandler {
    
    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {
    }
    
    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {
        
        Minecraft minecraft = FMLClientHandler.instance().getClient();
        EntityPlayer player = minecraft.thePlayer;
        ItemStack currentItemStack = null;
        
        if (type.contains(TickType.RENDER)) {
            if (player != null) {
                currentItemStack = player.inventory.getCurrentItem();
                
                if (Minecraft.isGuiEnabled() && minecraft.inGameHasFocus) {
                    if (currentItemStack != null) {
                        renderSpacetimeCharge(minecraft, player, currentItemStack, (Float) tickData[0]);
                    }
                }
            }
        }
    }
    
    private static void renderSpacetimeCharge(Minecraft minecraft, EntityPlayer player, ItemStack currentItemStack,
            float partialTicks) {
        
        boolean display = false;
        
        for (int i = 0; i <= 4; i++) {
            if (player.getCurrentItemOrArmor(i) != null
                    && player.getCurrentItemOrArmor(i).getItem() instanceof ISpacetimeCharge) {
                display = true;
                break;
            }
        }
        
        /*
         * if ( (player.getCurrentItemOrArmor(0).getItem() != null &&
         * player.getCurrentItemOrArmor(0).getItem() instanceof
         * ISpacetimeCharge) || (player.getCurrentItemOrArmor(1).getItem() !=
         * null && player.getCurrentItemOrArmor(0).getItem() instanceof
         * ISpacetimeCharge) || (player.getCurrentItemOrArmor(2).getItem() !=
         * null && player.getCurrentItemOrArmor(0).getItem() instanceof
         * ISpacetimeCharge) || (player.getCurrentItemOrArmor(3).getItem() !=
         * null && player.getCurrentItemOrArmor(0).getItem() instanceof
         * ISpacetimeCharge) || (player.getCurrentItemOrArmor(4).getItem() !=
         * null && player.getCurrentItemOrArmor(0).getItem() instanceof
         * ISpacetimeCharge))
         */
        
        if (display) {
            // setup render
            ScaledResolution res = new ScaledResolution(minecraft.gameSettings, minecraft.displayWidth,
                    minecraft.displayHeight);
            FontRenderer fontRender = minecraft.fontRenderer;
            int width = res.getScaledWidth();
            int height = res.getScaledHeight();
            minecraft.entityRenderer.setupOverlayRendering();
            
            // draw
            String text = SpacetimeChargeHandler.getCurrentCharge(player) + "/"
                    + SpacetimeChargeHandler.getMaxCharge(player);
            int x = 100;
            int y = 200;
            fontRender.drawStringWithShadow(text, x, y, 0x2a2f9f);
        }
    }
    
    @Override
    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.CLIENT, TickType.RENDER);
    }
    
    @Override
    public String getLabel() {
        return "SpacetimeHUDHandler";
    }
    
}
