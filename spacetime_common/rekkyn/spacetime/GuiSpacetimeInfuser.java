package rekkyn.spacetime;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class GuiSpacetimeInfuser extends GuiContainer {
    public GuiSpacetimeInfuser(InventoryPlayer player_inventory, TileSpacetimeInfuser tile_entity) {
        super(new ContainerSpacetimeInfuser(tile_entity, player_inventory));
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
    }
}
