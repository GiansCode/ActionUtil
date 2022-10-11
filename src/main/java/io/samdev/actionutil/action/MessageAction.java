package io.samdev.actionutil.action;

import me.clip.placeholderapi.libs.kyori.adventure.text.Component;
import me.clip.placeholderapi.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class MessageAction implements Action {
    public static void execute(Player player, Plugin plugin, Component msg) {
        player.sendMessage(LegacyComponentSerializer.legacyAmpersand().serialize(msg));
    }
}
