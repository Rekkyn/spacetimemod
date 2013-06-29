package rekkyn.spacetime.client;

import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.tileentity.TileEntityEnderChestRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnderChest;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import rekkyn.spacetime.Spacetime;
import rekkyn.spacetime.inventory.TileSpacetimeChest;

public class RenderSpacetimeChest extends TileEntitySpecialRenderer {
    
    private ModelChest theEnderChestModel = new ModelChest();
    
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float par8)
    {
        this.renderChest((TileSpacetimeChest)tileentity, x, y, z, par8);
    }

    
    public void renderChest(TileSpacetimeChest chest, double par2, double par4, double par6,
            float par8) {
        int i = 0;
        
        if (chest.func_70309_m()) {
            i = chest.getBlockMetadata();
        }
        
        this.bindTextureByName("/mods/Spacetime/textures/blocks/spacetimeChest.png");
        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef((float) par2, (float) par4 + 1.0F, (float) par6 + 1.0F);
        GL11.glScalef(1.0F, -1.0F, -1.0F);
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        short short1 = 0;
        
        if (i == 2) {
            short1 = 180;
        }
        
        if (i == 3) {
            short1 = 0;
        }
        
        if (i == 4) {
            short1 = 90;
        }
        
        if (i == 5) {
            short1 = -90;
        }
        
        GL11.glRotatef(short1, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        float f1 = chest.prevLidAngle
                + (chest.lidAngle - chest.prevLidAngle) * par8;
        f1 = 1.0F - f1;
        f1 = 1.0F - f1 * f1 * f1;
        theEnderChestModel.chestLid.rotateAngleX = -(f1 * (float) Math.PI / 2.0F);
        theEnderChestModel.renderAll();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

}
