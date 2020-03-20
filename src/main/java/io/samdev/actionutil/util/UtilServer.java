package io.samdev.actionutil.util;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import io.samdev.actionutil.ActionUtil;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class UtilServer
{
    private UtilServer() {}
    
    public static void writeToBungee(Player player, Plugin plugin, String... args)
    {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        Arrays.stream(args).forEach(output::writeUTF);

        player.sendPluginMessage(plugin, "BungeeCord", output.toByteArray());
    }
}