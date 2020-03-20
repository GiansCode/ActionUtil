package io.samdev.actionutil.action;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class TeleportAction implements Action
{
    public static void execute(Player player, Plugin plugin, World world, Double x, Double y, Double z, Float yaw, Float pitch)
    {
        Location location = new Location(
            world,
            x == null ? player.getLocation().getX() : x,
            y == null ? player.getLocation().getY() : y,
            z == null ? player.getLocation().getZ() : z,
            yaw == null ? player.getLocation().getYaw() : yaw,
            pitch == null ? player.getLocation().getPitch() : pitch
        );

        player.teleport(location);
    }
}
