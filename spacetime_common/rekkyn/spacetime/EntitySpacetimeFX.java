package rekkyn.spacetime;

import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.world.World;

public class EntitySpacetimeFX extends EntityPortalFX {
    
    private double portalPosX;
    private double portalPosY;
    private double portalPosZ;
    
    public EntitySpacetimeFX(World world, double x, double y, double z, double motionX, double motionY, double motionZ,
            float scale, float red, float green, float blue) {
        super(world, x, y, z, motionX, motionY, motionZ);
        particleRed = red;
        particleGreen = green;
        particleBlue = blue;
        
        portalPosX = x;
        portalPosY = y;
        portalPosZ = z;
        
        noClip = true;
        
        particleScale = scale;
    }
    
    @Override
    public void onUpdate() {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        float f = (float) particleAge / (float) particleMaxAge;
        float percentAge = f;
        f = -f + f * f * 2.0F;
        f = 1.0F - f;
        posX = portalPosX + motionX * f;
        posY = portalPosY + motionY * f + (1.0F - percentAge);
        posZ = portalPosZ + motionZ * f;
        
        if (particleAge++ >= particleMaxAge) {
            this.setDead();
        }
    }
    
}
