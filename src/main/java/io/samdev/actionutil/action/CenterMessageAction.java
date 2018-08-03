package io.samdev.actionutil.action;

import io.samdev.actionutil.util.UtilMessage;
import org.bukkit.entity.Player;

public class CenterMessageAction implements Action
{
    public static void execute(Player player, String msg)
    {
        UtilMessage.sendCenteredMessage(player, msg);
    }
}
