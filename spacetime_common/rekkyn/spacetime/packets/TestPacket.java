package rekkyn.spacetime.packets;

import net.minecraft.entity.player.EntityPlayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class TestPacket extends SpacetimePacket {

        private String text;
        
        public TestPacket(String text) {
                this.text = text;
        }

        public TestPacket() { } // Be sure to always have the default constructor in your class, or the reflection code will fail!

        @Override
        public void write(ByteArrayDataOutput out) {
                out.writeUTF(text);
        }

        @Override
        public void read(ByteArrayDataInput in) throws ProtocolException {
                text = in.readUTF();
        }

        @Override
        public void execute(EntityPlayer player, Side side) throws ProtocolException {
                if (side.isClient()) {
                        player.addChatMessage(text);
                } else {
                        throw new ProtocolException("Cannot send this packet to the server!");
                }
        }
}
