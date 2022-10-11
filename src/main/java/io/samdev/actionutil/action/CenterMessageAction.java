package io.samdev.actionutil.action;

import io.samdev.actionutil.util.UtilMessage;
import me.clip.placeholderapi.libs.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CenterMessageAction implements Action {
    public static void execute(Player player, Plugin plugin, Component msg) {
        UtilMessage.sendCenteredMessage(player, msg);
    }
}
