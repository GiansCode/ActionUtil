package io.samdev.actionutil.action;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class JsonMessageAction implements Action
{
    public static void execute(Player player, Plugin plugin, String json)
    {
        ConsoleCommandAction.execute(null, null, "tellraw " + player.getName() + " " + json);
    }
}
