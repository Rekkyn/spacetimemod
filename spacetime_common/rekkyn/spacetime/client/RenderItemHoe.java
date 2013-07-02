package rekkyn.spacetime.client;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderItemHoe implements IItemRenderer {
    private RenderManager renderManager;
    private Minecraft mc;
    private int spinCount = 0;
    
    public RenderItemHoe() {
        renderManager = RenderManager.instance;
        mc = Minecraft.getMinecraft();
    }
    
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
    }
    
    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return false;
    }
    
    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        EntityLiving entity = (EntityLiving) data[1];
        ItemRenderer irInstance = mc.entityRenderer.itemRenderer;
        
        GL11.glPopMatrix();
        if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
            this.renderItem(entity, item, 0);
        } else {
            GL11.glPushMatrix();
            
            float f2 = 3F - 1F / 3F;
            GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(-60.0F, 0.0F, 0.0F, 1.0F);
            GL11.glScalef(f2, f2, f2);
            GL11.glTranslatef(-0.25F, -0.1875F, 0.1875F);
            
            float f3 = 0.75F;
            GL11.glTranslatef(0.0625F, 0.25F, 0.0F);
            // GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(f3, -f3, f3);
            GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            
            this.renderItem(entity, item, 0);
            GL11.glPopMatrix();
        }
        GL11.glPushMatrix();
    }
    
    private void renderItem(EntityLiving entity, ItemStack item, int par3) {
        Icon icon = entity.getItemIcon(item, par3);
        
        if (icon == null) { return; }
        
        Tessellator tessellator = Tessellator.instance;
        float f = icon.getMinU();
        float f1 = icon.getMaxU();
        float f2 = icon.getMinV();
        float f3 = icon.getMaxV();
        float f4 = 0.0F;
        float f5 = 0.3F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glTranslatef(-f4, -f5, 0.0F);
        float f6 = 2.0F;
        GL11.glScalef(f6, f6, f6);
        GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
        Random rand = new Random();
        int i = rand.nextInt(30000);
        if (i == 0) {
            spinCount = 100;
        }
        if (spinCount > 0) {
            GL11.glRotatef((float) spinCount / 50 * 360.0F, 1.0F, 1.0F, 0.0F);
            spinCount--;
        }
        
        GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
        ItemRenderer.renderItemIn2D(tessellator, f1, f2, f, f3, icon.getSheetWidth(), icon.getSheetHeight(), 0.0625F);
        
        GL11.glDepthFunc(GL11.GL_EQUAL);
        GL11.glDisable(GL11.GL_LIGHTING);
        mc.renderEngine.bindTexture("%blur%/misc/glint.png");
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
        float f7 = 0.76F;
        GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
        GL11.glMatrixMode(GL11.GL_TEXTURE);
        GL11.glPushMatrix();
        float f8 = 0.125F;
        GL11.glScalef(f8, f8, f8);
        float f9 = (Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
        GL11.glTranslatef(f9, 0.0F, 0.0F);
        GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
        ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glScalef(f8, f8, f8);
        f9 = (Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
        GL11.glTranslatef(-f9, 0.0F, 0.0F);
        GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
        ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
        GL11.glPopMatrix();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        renderSunbeams(entity, 0);
    }
    
    protected void renderSunbeams(EntityLiving entity, float par2) {
        Tessellator tessellator = Tessellator.instance;
        
        if (entity.ticksExisted > 0) {
            RenderHelper.disableStandardItemLighting();
            float f1 = (entity.ticksExisted + par2) / 200.0F;
            
            Random random = new Random(432L);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glShadeModel(GL11.GL_SMOOTH);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glDepthMask(false);
            GL11.glPushMatrix();
            GL11.glTranslatef(0.25F, 0.75F, -0.03125F);
            
            for (int i = 0; (float) i < 10; ++i) {
                GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(random.nextFloat() * 360.0F + f1 * 90.0F, 0.0F, 0.0F, 1.0F);
                tessellator.startDrawing(6);
                float f3 = random.nextFloat() * 0.5F + 0.5F;
                float f4 = random.nextFloat() * 0.5F + 0.25F;
                tessellator.setColorRGBA_I(12172007, 123);
                tessellator.addVertex(0.0D, 0.0D, 0.0D);
                tessellator.setColorRGBA_I(255, 0);
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
    
}