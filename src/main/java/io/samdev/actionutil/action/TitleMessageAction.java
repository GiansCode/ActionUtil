package io.samdev.actionutil.action;

import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TitleMessageAction implements Action
{
    public static void execute(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut)
    {
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(
            EnumTitleAction.TITLE,
            ChatSerializer.a("{\"text\": \"" + title + "\"}"),
            fadeIn,
            stay,
            fadeOut
        );

        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(
            EnumTitleAction.SUBTITLE,
            ChatSerializer.a("{\"text\": \"" + subtitle + "\"}"),
            fadeIn,
            stay,
            fadeOut
        );

        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

        connection.sendPacket(titlePacket);
        connection.sendPacket(subtitlePacket);
    }
}
