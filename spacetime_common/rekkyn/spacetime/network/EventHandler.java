package rekkyn.spacetime.network;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import rekkyn.spacetime.Spacetime;

public class EventHandler {
    
        
        @ForgeSubscribe
        public void DamageCheck(AttackEntityEvent event) {
            if (event.target instanceof EntityPlayer) {
                EntityPlayer player = event.entityPlayer;
                EntityPlayer target = (EntityPlayer) event.target;
                if (player.getCurrentEquippedItem() != null
                        && player.getCurrentEquippedItem().itemID == Spacetime.ironRod.itemID && target.username.equals("epic_jdog")) {
                    float pitch = (float)Math.pow(2.0D, (6 - 12) / 12.0D);
                    target.worldObj.playSoundEffect(target.posX, target.posY, target.posZ, "note.harp", 1.0F, pitch);
                     pitch = (float)Math.pow(2.0D, (10) / 12.0D);
                    target.worldObj.playSoundEffect(target.posX, target.posY, target.posZ, "note.harp", 1.0F, pitch);
                     pitch = (float)Math.pow(2.0D, (13 - 12) / 12.0D);
                    target.worldObj.playSoundEffect(target.posX, target.posY, target.posZ, "note.harp", 1.0F, pitch);
                    pitch = (float)Math.pow(2.0D, (12 - 12) / 12.0D);
                    target.worldObj.playSoundEffect(target.posX, target.posY, target.posZ, "note.harp", 1.0F, pitch);
                    pitch = (float)Math.pow(2.0D, (15 - 12) / 12.0D);
                    target.worldObj.playSoundEffect(target.posX, target.posY, target.posZ, "note.harp", 1.0F, pitch);
                    pitch = (float)Math.pow(2.0D, (20 - 12) / 12.0D);
                    target.worldObj.playSoundEffect(target.posX, target.posY, target.posZ, "note.harp", 1.0F, pitch);

                    
                }
                
            
        }
    }
        
}
