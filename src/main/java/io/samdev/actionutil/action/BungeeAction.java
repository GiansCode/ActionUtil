package io.samdev.actionutil.action;

import io.samdev.actionutil.util.UtilServer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class BungeeAction implements Action {
    public static void execute(Player player, Plugin plugin, String server) {
        System.out.println(server);
        UtilServer.writeToBungee(player, plugin, "Connect", server);
    }
}