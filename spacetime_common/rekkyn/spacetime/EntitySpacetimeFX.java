package rekkyn.spacetime;

import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.world.World;

public class EntitySpacetimeFX extends EntityPortalFX {
    
    public EntitySpacetimeFX(World world, double x, double y, double z, double motionX, double motionY, double motionZ,
            float scale) {
        super(world, x, y, z, motionX, motionY, motionZ/*, scale*/);
        particleRed = 0.16F;
        particleGreen = 0.18F;
        particleBlue = 0.62F;
    }
    
}
