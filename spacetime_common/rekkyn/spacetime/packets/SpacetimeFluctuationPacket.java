package rekkyn.spacetime.packets;

import rekkyn.spacetime.inventory.TileSpacetimeFluctuation;
import net.minecraft.entity.player.EntityPlayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class SpacetimeFluctuationPacket extends SpacetimePacket {
    
    public int damage;
    public int x, y, z;
    
    public SpacetimeFluctuationPacket(int x, int y, int z, int damage) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.damage = damage;
    }
    
    public SpacetimeFluctuationPacket() {
    } // Be sure to always have the default constructor in your class, or the
      // reflection code will fail!
    
    @Override
    public void write(ByteArrayDataOutput out) {
        out.writeInt(x);
        out.writeInt(y);
        out.writeInt(z);
        out.writeInt(damage);
    }
    
    @Override
    public void read(ByteArrayDataInput in) throws ProtocolException {
        x = in.readInt();
        y = in.readInt();
        z = in.readInt();
        damage = in.readInt();
    }
    
    @Override
    public void execute(EntityPlayer player, Side side) throws ProtocolException {
        if (side.isClient()) {
            TileSpacetimeFluctuation tile = (TileSpacetimeFluctuation) player.worldObj.getBlockTileEntity(x, y, z);
            if (tile != null) {
            tile.setDamage(damage);
            }
        } else {
            throw new ProtocolException("Cannot send this packet to the server!");
        }
    }
}
