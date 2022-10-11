package io.samdev.actionutil.action;

import io.samdev.actionutil.util.UtilPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Collections;

public class TitleMessageAction implements Action {
    public static void execute(Player player, Plugin plugin, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        UtilPlayer.sendTitle(Collections.singletonList(player), title, subtitle, fadeIn, stay, fadeOut);
    }
}
