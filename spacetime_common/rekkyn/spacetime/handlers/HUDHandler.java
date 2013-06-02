package rekkyn.spacetime.handlers;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

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
                
                if (Minecraft.isGuiEnabled()) {
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
        
        if (player.capabilities.isCreativeMode) {
            display = false;
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
            GL11.glPushMatrix();
            ScaledResolution res = new ScaledResolution(minecraft.gameSettings, minecraft.displayWidth,
                    minecraft.displayHeight);
            
            minecraft.entityRenderer.setupOverlayRendering();
            
            RenderHelper.enableGUIStandardItemLighting();
            FontRenderer fontRender = minecraft.fontRenderer;
            int width = res.getScaledWidth();
            int height = res.getScaledHeight();
            
            GL11.glPushMatrix();
            RenderHelper.enableGUIStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glEnable(GL11.GL_COLOR_MATERIAL);
            
            GL11.glEnable(2929 /* GL_DEPTH_TEST */);
            
            if (!minecraft.inGameHasFocus) {
                GL11.glColor4f(0.24F, 0.24F, 0.24F, 1.0F);
            } else {
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            }
            
            // draw
            GL11.glBindTexture(3553,
                    minecraft.renderEngine.getTexture("/mods/Spacetime/textures/gui/spacetimeInfuser.png"));
            minecraft.ingameGUI.drawTexturedModalRect(width / 2 + 97, height - 40, 0, 166, 12, 40);
            
            int scaledAmount = (int) ((float) SpacetimeChargeHandler.getCurrentCharge(player)
                    / (float) SpacetimeChargeHandler.getMaxCharge(player) * 34);
            int currentCharge = SpacetimeChargeHandler.getCurrentCharge(player);
            int useAmount = 0;
            if (player.getCurrentItemOrArmor(0).getItem() instanceof ISpacetimeCharge) {
                useAmount = ((ISpacetimeCharge) player.getCurrentItemOrArmor(0).getItem()).getUseAmount();
            }
            
            if (currentCharge >= useAmount) {
                minecraft.ingameGUI.drawTexturedModalRect(width / 2 + 97, height - 2 - scaledAmount, 12, 170, 12,
                        scaledAmount - 2);
            } else {
                minecraft.ingameGUI.drawTexturedModalRect(width / 2 + 97, height - 2 - scaledAmount, 24, 170, 12,
                        scaledAmount - 2);
            }
            
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(2929 /* GL_DEPTH_TEST */);
            
            GL11.glPopMatrix();
            GL11.glPopMatrix();
            
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
