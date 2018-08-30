package io.samdev.actionutil.action;

import io.samdev.actionutil.util.UtilPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ActionbarBroadcastAction implements Action
{
    public static void execute(Player player, String msg)
    {
        UtilPlayer.sendActionbar(Bukkit.getOnlinePlayers(), msg);
    }
}
