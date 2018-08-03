package io.samdev.actionutil.util;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import io.samdev.actionutil.ActionUtil;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class UtilServer
{
    private UtilServer() {}

    private static final ActionUtil plugin = JavaPlugin.getPlugin(ActionUtil.class);

    public static void writeToBungee(Player player, String... args)
    {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        Arrays.stream(args).forEach(output::writeUTF);

        player.sendPluginMessage(plugin, "BungeeCord", output.toByteArray());
    }
}