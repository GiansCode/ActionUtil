package io.samdev.actionutil.action;

import me.clip.placeholderapi.libs.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CenterBroadcastAction implements Action {
    public static void execute(Player player, Plugin plugin, Component msg) {
        Bukkit.getOnlinePlayers().forEach(other ->
                CenterMessageAction.execute(player, null, msg)
        );
    }
}
