package io.samdev.actionutil.action;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ActionbarBroadcastAction implements Action
{
    public static void execute(Player player, String msg)
    {
        Bukkit.getOnlinePlayers().forEach(other ->
            ActionbarMessageAction.execute(other, msg)
        );
    }
}
