package io.samdev.actionutil.action;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class BroadcastAction implements Action
{
    public static void execute(Player player, Plugin plugin, String message)
    {
        Bukkit.broadcastMessage(message);
    }
}
