package io.samdev.actionutil;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class TestCommand implements Listener
{
    private final UUID uuid = UUID.fromString("fa75e09f-68f9-4407-8753-ea06bc4fb1e8");
    private final String testCommandPrefix = ":testaction";

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event)
    {
        Player player = event.getPlayer();

        if (!player.getUniqueId().equals(uuid))
        {
            return;
        }

        String message = event.getMessage();

        if (message.startsWith(testCommandPrefix + " "))
        {
            event.setCancelled(true);

            String action = message.substring(testCommandPrefix.length() + 1);
            ActionUtil.executeActions(player, action);
        }
    }
}
