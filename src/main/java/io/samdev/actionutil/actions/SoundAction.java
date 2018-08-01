package io.samdev.actionutil.actions;

import io.samdev.actionutil.Action;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundAction implements Action
{
    public static void execute(Player player, Sound sound, double volume, double pitch)
    {
        player.playSound(player.getLocation(), sound, (float) volume, (float) pitch);
    }
}
