package io.samdev.actionutil.action;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class SoundBroadcastAction implements Action
{
    public static void execute(Player player, Plugin plugin, Sound sound, double volume, double pitch)
    {
        Bukkit.getOnlinePlayers().forEach(other ->
            other.playSound(other.getLocation(), sound, (float) volume, (float) pitch)
        );
    }
}
