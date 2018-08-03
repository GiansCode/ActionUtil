package io.samdev.actionutil.action;

import io.samdev.actionutil.util.UtilServer;
import org.bukkit.entity.Player;

public class BungeeAction implements Action
{
    public static void execute(Player player, String server)
    {
        UtilServer.writeToBungee(player, "Connect", server);
    }
}