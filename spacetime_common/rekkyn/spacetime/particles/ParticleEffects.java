package rekkyn.spacetime.particles;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.world.World;

public class ParticleEffects {
    private static Minecraft mc = Minecraft.getMinecraft();
    private static World theWorld = mc.theWorld;
    private static RenderEngine renderEngine = mc.renderEngine;
    private static Random rand = new Random();
    
    public static EntityFX spawnParticle(String particleName, double x, double y, double z, double motionX,
            double motionY, double motionZ) {
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
            
            if (particleName.equals("crossbowTrail")) {
                mc.effectRenderer
                        .addEffect(new EntityCrossbowTrail(theWorld, x, y, z, motionX, motionY, motionZ, 4.0F));
            }
            
            if (xdist * xdist + ydist * ydist + zdist * zdist > maxDist * maxDist) {
                return null;
            } else if (particleSetting > 1) {
                return null;
            } else {
                if (particleName.equals("blue")) {
                    effect = new EntitySpacetimeFX(theWorld, x, y, z, motionX, motionY, motionZ, 1.0F,
                            0.10F + rand.nextFloat() / 16, 0.06F + rand.nextFloat() / 8, 0.29F + rand.nextFloat() / 8);
                } else if (particleName.equals("orange")) {
                    effect = new EntitySpacetimeFX(theWorld, x, y, z, motionX, motionY, motionZ, 1.0F,
                            0.81F + rand.nextFloat() / 8, 0.41F + rand.nextFloat() / 8, 0.05F + rand.nextFloat() / 8);
                } else if (particleName.equals("purple")) {
                    effect = new EntitySpacetimeFX(theWorld, x, y, z, motionX, motionY, motionZ, 1.0F,
                            0.29F + rand.nextFloat() / 8, 0.16F + rand.nextFloat() / 8, 0.62F + rand.nextFloat() / 8);
                }
                
                if (effect != null) {
                    mc.effectRenderer.addEffect(effect);
                }
                return effect;
            }
        }
        return null;
    }
}