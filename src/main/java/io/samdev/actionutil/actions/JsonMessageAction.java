package io.samdev.actionutil.actions;

import io.samdev.actionutil.Action;
import org.bukkit.entity.Player;

public class JsonMessageAction implements Action
{
    public static void execute(Player player, String json)
    {
        ConsoleCommandAction.execute(null, "tellraw " + player.getName() + " " + json);
    }
}
