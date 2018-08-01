package io.samdev.actionutil.actions;

import io.samdev.actionutil.Action;
import org.bukkit.entity.Player;

public class MessageAction implements Action
{
    public static void execute(Player player, String msg)
    {
        player.sendMessage(msg);
    }
}
