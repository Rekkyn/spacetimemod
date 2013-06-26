package rekkyn.spacetime.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Timer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class TimeSpeedPacket extends SpacetimePacket {
    
    private Float multiplier; 
    private static Minecraft mc = Minecraft.getMinecraft();
    
    public TimeSpeedPacket(float multiplier) {
        this.multiplier = multiplier;
    }
    
    public TimeSpeedPacket() {
    } // Be sure to always have the default constructor in your class, or the
      // reflection code will fail!
    
    @Override
    public void write(ByteArrayDataOutput out) {
        out.writeFloat(multiplier);
    }
    
    @Override
    public void read(ByteArrayDataInput in) throws ProtocolException {
        multiplier = in.readFloat();
    }
    
    @Override
    public void execute(EntityPlayer player, Side side) throws ProtocolException {
        if (side.isClient()) {
            mc.timer.timerSpeed = multiplier;
        } else {
            throw new ProtocolException("Cannot send this packet to the server!");
        }
    }
}
