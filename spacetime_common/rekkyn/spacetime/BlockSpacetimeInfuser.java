package rekkyn.spacetime;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSpacetimeInfuser extends BlockContainer {
    
    protected BlockSpacetimeInfuser(int id) {
        super(id, Material.rock);
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float g,
            float t) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (tileEntity == null || player.isSneaking()) {
            return false;
        }
        player.openGui(Spacetime.instance, 0, world, x, y, z);
        return true;
    }
    
    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileSpacetimeInfuser();
    }
    
}
