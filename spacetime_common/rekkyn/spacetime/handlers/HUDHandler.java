package rekkyn.spacetime.handlers;

import java.util.EnumSet;

import rekkyn.spacetime.item.ISpacetimeCharge;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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
        
        if (currentItemStack.getItem() instanceof ISpacetimeCharge) {
            ISpacetimeCharge item = (ISpacetimeCharge)currentItemStack.getItem();
        // setup render
        ScaledResolution res = new ScaledResolution(minecraft.gameSettings, minecraft.displayWidth,
                minecraft.displayHeight);
        FontRenderer fontRender = minecraft.fontRenderer;
        int width = res.getScaledWidth();
        int height = res.getScaledHeight();
        minecraft.entityRenderer.setupOverlayRendering();
        
        // draw
        String text = item.getSpacetimeCharge() + "/" + item.getSpacetimeMaxCharge();
        int x = 100;
        int y = 200;
        int color = 0x2a2f9f;
        fontRender.drawStringWithShadow(text, x, y, color);
        }
    }
    
    @Override
    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.CLIENT, TickType.RENDER);
    }
    
    @Override
    public String getLabel() {
        return "SpacetimeChargeHandler";
    }
    
}
