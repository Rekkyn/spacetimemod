package rekkyn.spacetime.inventory;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class GuiSpacetimeInfuser extends GuiContainer {
    
    private TileSpacetimeInfuser infuserInventory;
    
    public GuiSpacetimeInfuser(InventoryPlayer player_inventory, TileSpacetimeInfuser tileEntity) {
        super(new ContainerSpacetimeInfuser(tileEntity, player_inventory));
        infuserInventory = tileEntity;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int i, int j) {
        
        fontRenderer.drawString("Spacetime Infuser", 8, 6, 4210752);
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 6, ySize - 96, 4210752);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        
        mc.renderEngine.bindTexture("/mods/Spacetime/textures/gui/spacetimeInfuser.png");
        
        int x = (width - xSize) / 2;
        
        int y = (height - ySize) / 2;
        
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        
        int i1 = infuserInventory.getProgressScaled(48);
        drawTexturedModalRect(x + 62, y + 25, 176, 0, i1 + 1, 35);
    }
}
