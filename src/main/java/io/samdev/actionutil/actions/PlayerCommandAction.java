package io.samdev.actionutil.actions;

import io.samdev.actionutil.Action;
import org.bukkit.entity.Player;

public class PlayerCommandAction implements Action
{
    public static void execute(Player player, String command)
    {
        player.performCommand(command);
    }
}
