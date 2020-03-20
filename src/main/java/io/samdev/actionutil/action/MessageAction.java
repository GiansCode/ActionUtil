package io.samdev.actionutil.action;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class MessageAction implements Action
{
    public static void execute(Player player, Plugin plugin, String msg)
    {
        player.sendMessage(msg);
    }
}
