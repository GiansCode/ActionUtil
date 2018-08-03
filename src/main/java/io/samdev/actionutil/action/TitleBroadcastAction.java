package io.samdev.actionutil.action;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TitleBroadcastAction implements Action
{
    public static void execute(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut)
    {
        Bukkit.getOnlinePlayers().forEach(other ->
            TitleMessageAction.execute(player, title, subtitle, fadeIn, stay, fadeOut)
        );
    }
}
