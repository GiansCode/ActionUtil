package io.samdev.actionutil.action;

import org.bukkit.entity.Player;

public class MessageAction implements Action
{
    public static void execute(Player player, String msg)
    {
        player.sendMessage(msg);
    }
}
