package rekkyn.spacetime;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.world.World;

public class ParticleEffects {
    private static Minecraft mc = Minecraft.getMinecraft();
    private static World theWorld = mc.theWorld;
    private static RenderEngine renderEngine = mc.renderEngine;
    
    public static EntityFX spawnParticle(String particleName, double x, double y, double z, double motionX,
            double motionY, double motionZ, float red, float green, float blue) {
        if (mc != null && mc.renderViewEntity != null && mc.effectRenderer != null) {
            int particleSetting = mc.gameSettings.particleSetting;
            
            if (particleSetting == 1 && theWorld.rand.nextInt(3) == 0) {
                particleSetting = 2;
            }
            
            double xdist = mc.renderViewEntity.posX - x;
            double ydist = mc.renderViewEntity.posY - y;
            double zdist = mc.renderViewEntity.posZ - z;
            EntityFX effect = null;
            double maxDist = 16.0D;
            
            if (xdist * xdist + ydist * ydist + zdist * zdist > maxDist * maxDist) {
                return null;
            } else if (particleSetting > 1) {
                return null;
            } else {
                if (particleName.equals("spacetime")) {
                    effect = new EntitySpacetimeFX(theWorld, x, y, z, motionX, motionY, motionZ, 1.0F, red, green, blue);
                }
                
                mc.effectRenderer.addEffect(effect);
                return effect;
            }
        }
        return null;
    }
}