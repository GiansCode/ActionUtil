package io.samdev.actionutil.action;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class SoundAction implements Action {
    public static void execute(Player player, Plugin plugin, Sound sound, double volume, double pitch) {
        player.playSound(player.getLocation(), sound, (float) volume, (float) pitch);
    }
}
