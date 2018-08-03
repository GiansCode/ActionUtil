package io.samdev.actionutil.action;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class ActionbarMessageAction implements Action
{
    public static void execute(Player player, String msg)
    {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(msg));
    }
}
