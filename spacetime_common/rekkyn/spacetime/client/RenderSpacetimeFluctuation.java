package rekkyn.spacetime.client;

import java.nio.FloatBuffer;
import java.util.Random;

import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import rekkyn.spacetime.inventory.TileSpacetimeFluctuation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSpacetimeFluctuation extends TileEntitySpecialRenderer {
    FloatBuffer field_76908_a = GLAllocation.createDirectFloatBuffer(16);
    
    public void renderFluctuation(TileSpacetimeFluctuation block, double par2, double par4, double par6, float par8) {
        
        renderSunbeams(block, par2, par4, par6, par8);
    }
    
    protected void renderSunbeams(TileSpacetimeFluctuation entity, double x, double y, double z, float partialTicks) {
        Tessellator tessellator = Tessellator.instance;
        
        float time = entity.getWorldObj().getTotalWorldTime() + partialTicks;
        
        if (time > 0) {
            RenderHelper.disableStandardItemLighting();
            float f1 = time / 200.0F;
            
            Random random = new Random(432L);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glShadeModel(GL11.GL_SMOOTH);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glDepthMask(false);
            GL11.glPushMatrix();
            GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
            
            for (int i = 0; (float) i < 15; ++i) {
                GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(random.nextFloat() * 360.0F + f1 * 90.0F, 0.0F, 0.0F, 1.0F);
                tessellator.startDrawing(6);
                float f3 = random.nextFloat() * 10.0F + 5.0F;
                float f4 = random.nextFloat() * 2.0F + 2.0F;
                float a = (float)entity.damage / 100.0F;
                tessellator.setColorRGBA_I(13158655, (int) (127 * a));
                tessellator.addVertex(0.0D, 0.0D, 0.0D);
                tessellator.setColorRGBA_I(6947071, 0);
                tessellator.addVertex(-0.866D * f4, f3, (-0.5F * f4));
                tessellator.addVertex(0.866D * f4, f3, (-0.5F * f4));
                tessellator.addVertex(0.0D, f3, (1.0F * f4));
                tessellator.addVertex(-0.866D * f4, f3, (-0.5F * f4));
                tessellator.draw();
            }
            
            GL11.glPopMatrix();
            GL11.glDepthMask(true);
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glShadeModel(GL11.GL_FLAT);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            RenderHelper.enableStandardItemLighting();
        }
    }
    
    @Override
    public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8) {
        this.renderFluctuation((TileSpacetimeFluctuation) par1TileEntity, par2, par4, par6, par8);
    }
}
