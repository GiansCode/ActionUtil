package io.samdev.actionutil.action;

import io.samdev.actionutil.util.UtilPlayer;
import org.bukkit.entity.Player;

import java.util.Collections;

public class ActionbarMessageAction implements Action
{
    public static void execute(Player player, String msg)
    {
        UtilPlayer.sendActionbar(Collections.singletonList(player), msg);
    }
}
