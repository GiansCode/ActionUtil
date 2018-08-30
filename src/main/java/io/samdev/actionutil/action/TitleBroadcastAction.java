package io.samdev.actionutil.action;

import io.samdev.actionutil.util.UtilPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TitleBroadcastAction implements Action
{
    public static void execute(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut)
    {
        UtilPlayer.sendTitle(Bukkit.getOnlinePlayers(), title, subtitle, fadeIn, stay, fadeOut);
    }
}
