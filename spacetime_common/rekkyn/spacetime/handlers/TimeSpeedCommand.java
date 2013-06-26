package rekkyn.spacetime.handlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import rekkyn.spacetime.packets.TimeSpeedPacket;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TimeSpeedCommand extends CommandBase {
    
    private List aliases;
    
    public TimeSpeedCommand() {
        aliases = new ArrayList();
        aliases.add("timespeed");
        aliases.add("speed");
        aliases.add("ts");
    }
    
    @Override
    public String getCommandName() {
        return "timespeed";
    }
    
    @Override
    public String getCommandUsage(ICommandSender icommandsender) {
        return "/timespeed <multiplier>";
    }
    
    @Override
    public List getCommandAliases() {
        return aliases;
    }
    
    @Override
    public void processCommand(ICommandSender icommandsender, String[] astring) {
        if (astring.length == 0) { throw new WrongUsageException(getCommandUsage(icommandsender), new Object[0]); }
        
        float multiplier;
        try {
            multiplier = Float.parseFloat(astring[0]);
        } catch (NumberFormatException e) {
            throw new WrongUsageException(getCommandUsage(icommandsender), new Object[0]);
        }
        
        icommandsender.sendChatToPlayer("\u00a79Whoosh!");
        MinecraftServer.timerSpeed = multiplier;
        PacketDispatcher.sendPacketToAllPlayers(new TimeSpeedPacket(multiplier).makePacket());
        
        notifyAdmins(icommandsender, "Set time speed to %s", new Object[] { Float.valueOf(astring[0]) });
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
}
