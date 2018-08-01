package io.samdev.actionutil.actions;

import io.samdev.actionutil.Action;
import org.bukkit.entity.Player;

public class TitleMessageAction implements Action
{
    public static void execute(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut)
    {
        player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
    }
}
