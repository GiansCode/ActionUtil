package io.samdev.actionutil.actions;

import io.samdev.actionutil.Action;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundBroadcastAction implements Action
{
    public static void execute(Player player, Sound sound, double volume, double pitch)
    {
        Bukkit.getOnlinePlayers().forEach(other ->
            other.playSound(other.getLocation(), sound, (float) volume, (float) pitch)
        );
    }
}
