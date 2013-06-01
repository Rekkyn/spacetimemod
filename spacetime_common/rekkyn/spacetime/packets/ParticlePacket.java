package rekkyn.spacetime.packets;

import net.minecraft.entity.player.EntityPlayer;
import rekkyn.spacetime.particles.ParticleEffects;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class ParticlePacket extends SpacetimePacket {
    
    public String particleName;
    public double x, y, z;
    public double velocityX, velocityY, velocityZ;
    
    public ParticlePacket(String particleName, double x, double y, double z, double velocityX, double velocityY,
            double velocityZ) {
        this.particleName = particleName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
    }
    
    public ParticlePacket() {
    } // Be sure to always have the default constructor in your class, or the
      // reflection code will fail!
    
    @Override
    public void write(ByteArrayDataOutput out) {
        
        out.writeUTF(particleName);
        out.writeDouble(x);
        out.writeDouble(y);
        out.writeDouble(z);
        out.writeDouble(velocityX);
        out.writeDouble(velocityY);
        out.writeDouble(velocityZ);
        
    }
    
    @Override
    public void read(ByteArrayDataInput in) {
        
        particleName = in.readUTF();
        x = in.readDouble();
        y = in.readDouble();
        z = in.readDouble();
        velocityX = in.readDouble();
        velocityY = in.readDouble();
        velocityZ = in.readDouble();
        
    }
    
    @Override
    public void execute(EntityPlayer player, Side side) throws ProtocolException {
        if (side.isClient()) {
            ParticleEffects.spawnParticle(particleName, x, y, z, velocityX, velocityY, velocityZ);
        } else {
            throw new ProtocolException("Cannot send this packet to the server!");
        }
    }
}
