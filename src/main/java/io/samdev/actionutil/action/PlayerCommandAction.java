package io.samdev.actionutil.action;

import org.bukkit.entity.Player;

public class PlayerCommandAction implements Action
{
    public static void execute(Player player, String command)
    {
        player.performCommand(command);
    }
}
