package rekkyn.spacetime.client;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

import static org.lwjgl.opengl.GL11.*;

import rekkyn.spacetime.inventory.TileSpacetimeFluctuation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSpacetimeFluctuation extends TileEntitySpecialRenderer {
    
    public void renderFluctuation(TileSpacetimeFluctuation block, double x, double y, double z, float partialTicks) {
        //renderSunbeams(block, x, y, z, partialTicks);
        
        renderEffects(block, x, y, z, partialTicks);

    }
    
    private void renderEffects(TileSpacetimeFluctuation block, double x, double y, double z, float partialTicks) {
        if (Minecraft.getMinecraft().renderViewEntity instanceof EntityPlayer) {
            
            /*glPushMatrix();
            glDepthMask(false);
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE);
            glDisable(GL_LIGHTING);*/
            
            glDisable(GL_TEXTURE_2D);
            glShadeModel(GL_SMOOTH);
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE);
            glDisable(GL_ALPHA_TEST);
            glEnable(GL_CULL_FACE);
            glDepthMask(false);
            glPushMatrix();
            
            Minecraft.getMinecraft().renderEngine.bindTexture("/mods/Spacetime/textures/blocks/rekkynite.png");
            
            float rX = ActiveRenderInfo.rotationX;
            float rZ = ActiveRenderInfo.rotationZ;
            float rYZ = ActiveRenderInfo.rotationYZ;
            float rXY = ActiveRenderInfo.rotationXY;
            float rXZ = ActiveRenderInfo.rotationXZ;
            EntityPlayer player = (EntityPlayer) Minecraft.getMinecraft().renderViewEntity;
            double interpPosX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
            double interpPosY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
            double interpPosZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;
            
            x += 0.5;
            y += 0.5;
            z += 0.5;
            
            //glTranslated(-interpPosX, -interpPosY, -interpPosZ);
            
            Tessellator tessellator = Tessellator.instance;
            
            tessellator.startDrawingQuads();
            //tessellator.setBrightness(255);
            //tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
            
            float scale = block.damage / 50.0F;
            
            Vec3 v1 = Vec3.createVectorHelper(-rX * scale - rYZ * scale, -rXZ * scale, -rZ * scale - rXY * scale);
            Vec3 v2 = Vec3.createVectorHelper(-rX * scale + rYZ * scale, rXZ * scale, -rZ * scale + rXY * scale);
            Vec3 v3 = Vec3.createVectorHelper(rX * scale + rYZ * scale, rXZ * scale, rZ * scale + rXY * scale);
            Vec3 v4 = Vec3.createVectorHelper(rX * scale - rYZ * scale, -rXZ * scale, rZ * scale - rXY * scale);
            
            /*tessellator.addVertexWithUV(x + v1.xCoord, y + v1.yCoord, z + v1.zCoord, 0.0D, 1.0D);
            tessellator.addVertexWithUV(x + v2.xCoord, y + v2.yCoord, z + v2.zCoord, 1.0D, 1.0D);
            tessellator.addVertexWithUV(x + v3.xCoord, y + v3.yCoord, z + v3.zCoord, 1.0D, 0.0D);
            tessellator.addVertexWithUV(x + v4.xCoord, y + v4.yCoord, z + v4.zCoord, 0.0D, 0.0D);*/
            
            tessellator.setColorRGBA_I(16777215, 255);
            tessellator.addVertex(x + v1.xCoord, y + v1.yCoord, z + v1.zCoord);
            tessellator.addVertex(x + v2.xCoord, y + v2.yCoord, z + v2.zCoord);
            tessellator.addVertex(x + v3.xCoord, y + v3.yCoord, z + v3.zCoord);
            tessellator.addVertex(x + v4.xCoord, y + v4.yCoord, z + v4.zCoord);
            
            tessellator.draw();
            
            glPopMatrix();
            glDepthMask(true);
            glDisable(GL_CULL_FACE);
            glDisable(GL_BLEND);
            glShadeModel(GL_FLAT);
            glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            glEnable(GL_TEXTURE_2D);
            glEnable(GL_ALPHA_TEST);

                        
            /*glDisable(GL_BLEND);
            glEnable(GL_LIGHTING);
            glDepthMask(true);
            glPopMatrix();*/
        }
    }
    
    protected void renderSunbeams(TileSpacetimeFluctuation entity, double x, double y, double z, float partialTicks) {
        Tessellator tessellator = Tessellator.instance;
        
        float time = entity.getWorldObj().getTotalWorldTime() + partialTicks;
        
        if (time > 0) {
            RenderHelper.disableStandardItemLighting();
            float f1 = time / 200.0F;
            
            Random random = new Random(432L);
            glDisable(GL_TEXTURE_2D);
            glShadeModel(GL_SMOOTH);
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE);
            glDisable(GL_ALPHA_TEST);
            glEnable(GL_CULL_FACE);
            glDepthMask(false);
            glPushMatrix();
            glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
            
            for (int i = 0; (float) i < 15; ++i) {
                glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
                glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
                glRotatef(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
                glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
                glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
                glRotatef(random.nextFloat() * 360.0F + f1 * 90.0F, 0.0F, 0.0F, 1.0F);
                tessellator.startDrawing(6);
                float f3 = random.nextFloat() * 10.0F + 5.0F;
                float f4 = random.nextFloat() * 2.0F + 2.0F;
                float a = entity.damage / 100.0F;
                tessellator.setColorRGBA_I(13158655, (int) (127 * a));
                tessellator.addVertex(0.0D, 0.0D, 0.0D);
                tessellator.setColorRGBA_I(255, 0);
                tessellator.addVertex(-0.866D * f4, f3, -0.5F * f4);
                tessellator.addVertex(0.866D * f4, f3, -0.5F * f4);
                tessellator.addVertex(0.0D, f3, 1.0F * f4);                
                tessellator.draw();
            }
            
            glPopMatrix();
            glDepthMask(true);
            glDisable(GL_CULL_FACE);
            glDisable(GL_BLEND);
            glShadeModel(GL_FLAT);
            glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            glEnable(GL_TEXTURE_2D);
            glEnable(GL_ALPHA_TEST);
            RenderHelper.enableStandardItemLighting();
        }
    }
    
    @Override
    public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8) {
        this.renderFluctuation((TileSpacetimeFluctuation) par1TileEntity, par2, par4, par6, par8);
    }
}
