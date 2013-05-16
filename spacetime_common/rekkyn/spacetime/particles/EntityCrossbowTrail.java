package rekkyn.spacetime.particles;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityCrossbowTrail extends EntityFX {
    float field_70561_a;
    
    public EntityCrossbowTrail(World world, double lastTickPosX, double lastTickPosY, double lastTickPosZ, double par8,
            double par10, double par12) {
        this(world, lastTickPosX, lastTickPosY, lastTickPosZ, par8, par10, par12, 1.0F);
    }
    
    public EntityCrossbowTrail(World world, double lastTickPosX, double lastTickPosY, double lastTickPosZ,
            double motionX, double motionY, double motionZ, float par14) {
        super(world, lastTickPosX, lastTickPosY, lastTickPosZ, 0.0D, 0.0D, 0.0D);
        this.motionX *= 0.10000000149011612D;
        this.motionY *= 0.10000000149011612D;
        this.motionZ *= 0.10000000149011612D;
        this.motionX += motionX * 0.4D;
        this.motionY += motionY * 0.4D;
        this.motionZ += motionZ * 0.4D;
        particleRed = particleGreen = particleBlue = (float) (Math.random() * 0.30000001192092896D + 0.6000000238418579D);
        particleScale *= 0.75F;
        field_70561_a = particleScale;
        particleMaxAge = (int) (6.0D / (Math.random() * 0.8D + 0.6D));
        particleMaxAge = (int) (particleMaxAge * par14);
        noClip = false;
        this.setParticleTextureIndex(65);
        // this.onUpdate();
    }
    
    @Override
    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6,
            float par7) {
        float f6 = (particleAge + par2) / particleMaxAge * 32.0F;
        
        if (f6 < 0.0F) {
            f6 = 0.0F;
        }
        
        if (f6 > 1.0F) {
            f6 = 1.0F;
        }
        
        particleScale = field_70561_a * f6;
        super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
    }
    
    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate() {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        
        if (particleAge++ >= particleMaxAge) {
            this.setDead();
        }
        
        this.moveEntity(motionX, motionY, motionZ);
        particleGreen = (float) (particleGreen * 0.96D);
        particleRed = (float) (particleRed * 0.9D);
        motionX *= 0.699999988079071D;
        motionY *= 0.699999988079071D;
        motionZ *= 0.699999988079071D;
        
        if (onGround) {
            motionX *= 0.699999988079071D;
            motionZ *= 0.699999988079071D;
        }
    }
}
