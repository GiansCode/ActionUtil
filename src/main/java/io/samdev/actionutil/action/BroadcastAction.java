package io.samdev.actionutil.action;

import me.clip.placeholderapi.libs.kyori.adventure.text.Component;
import me.clip.placeholderapi.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class BroadcastAction implements Action {
    public static void execute(Player player, Plugin plugin, Component message) {
        Bukkit.broadcastMessage(LegacyComponentSerializer.legacyAmpersand().serialize(message));
    }
}
