package io.samdev.actionutil.action;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class JsonBroadcastAction implements Action {
    public static void execute(Player player, Plugin plugin, String json) {
        ConsoleCommandAction.execute(null, null, "tellraw @a " + json);
    }
}
