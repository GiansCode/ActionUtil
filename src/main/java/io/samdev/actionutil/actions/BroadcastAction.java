package io.samdev.actionutil.actions;

import io.samdev.actionutil.Action;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BroadcastAction implements Action
{
    public static void execute(Player player, String message)
    {
        Bukkit.broadcastMessage(message);
    }
}
