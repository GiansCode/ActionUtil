package io.samdev.actionutil.util;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;

import static io.samdev.actionutil.util.Reflection.getBukkitClass;
import static io.samdev.actionutil.util.Reflection.getConstructor;
import static io.samdev.actionutil.util.Reflection.getCraftBukkitClass;
import static io.samdev.actionutil.util.Reflection.getField;
import static io.samdev.actionutil.util.Reflection.getMethod;
import static io.samdev.actionutil.util.Reflection.getNmsClass;

public final class UtilPlayer
{
    private UtilPlayer() {}

    /* Packet Sending */
    private static Class<?> craftPlayerClass;
    private static Method getHandleMethod;

    private static Class<?> entityPlayerClass;
    private static Field playerConnectionField;

    private static Method sendPacketMethod;

    /* ChatSerializer */
    private static Method chatSerializerMethod;

    /* Actionbar */
    private static Constructor<?> packetPlayOutChatConstructor;

    /* Title */
    private static Constructor<?> packetPlayOutTitleConstructor;

    private static Object titleEnum;
    private static Object subtitleEnum;

    /* Bukkit API */
    private static Method sendTitleMethod;

    private static Method spigotMethod;
    private static Method sendMessageMethod;

    static
    {
        if (Reflection.isV1_8())
        {
            /* Packet Sending */
            craftPlayerClass = getCraftBukkitClass("entity.CraftPlayer");
            getHandleMethod = getMethod(craftPlayerClass, "getHandle");

            entityPlayerClass = getNmsClass("EntityPlayer");
            playerConnectionField = getField(entityPlayerClass, "playerConnection");

            Class<?> packetClass = getNmsClass("Packet");

            Class<?> playerConnectionClass = getNmsClass("PlayerConnection");
            sendPacketMethod = getMethod(playerConnectionClass, "sendPacket", packetClass);

            /* ChatSerializer */
            Class<?> iChatBaseComponentClass = getNmsClass("IChatBaseComponent");

            Class<?> chatSerializerClass = getNmsClass("IChatBaseComponent$ChatSerializer");
            chatSerializerMethod = getMethod(chatSerializerClass, "a", String.class);

            /* Actionbar */
            Class<?> packetPlayOutChatClass = getNmsClass("PacketPlayOutChat");
            packetPlayOutChatConstructor = getConstructor(packetPlayOutChatClass, iChatBaseComponentClass, byte.class);

            /* Title */
            Class<?> packetPlayOutTitleClass = getNmsClass("PacketPlayOutTitle");

            Class<?> enumTitleActionClass = getNmsClass("PacketPlayOutTitle$EnumTitleAction");

            try
            {
                titleEnum = getField(enumTitleActionClass, "TITLE").get(null);
                subtitleEnum = getField(enumTitleActionClass, "SUBTITLE").get(null);
            }
            catch (IllegalAccessException ignored) {}

            packetPlayOutTitleConstructor = getConstructor(packetPlayOutTitleClass, enumTitleActionClass, iChatBaseComponentClass, int.class, int.class, int.class);
        }
        else
        {
            /* Bukkit API */
            Class<?> playerClass = getBukkitClass("entity.Player");
            sendTitleMethod = getMethod(playerClass, "sendTitle", String.class, String.class, int.class, int.class, int.class);

            spigotMethod = getMethod(playerClass, "spigot");
            Class<?> spigotClass = getBukkitClass("entity.Player$Spigot");

            sendMessageMethod = getMethod(spigotClass, "sendMessage", ChatMessageType.class, BaseComponent.class);
        }
    }

    public static void sendActionbar(Collection<? extends Player> players, String msg)
    {
        if (Reflection.isV1_8())
        {
            // Use packets, no native API method
            try
            {
                Object iChatBaseComponent = createIChatBaseComponent(msg);
                Object packetPlayOutChat = packetPlayOutChatConstructor.newInstance(iChatBaseComponent, (byte) 2);

                players.forEach(player -> sendPacket(player, packetPlayOutChat));
            }
            catch (ReflectiveOperationException ex)
            {
                ex.printStackTrace();
            }
        }
        else
        {
            // Use Player#spigot().sendMessage(ChatMessageType.ACTION_BAR, BaseComponent);
            try
            {
                TextComponent component = new TextComponent(msg);

                for (Player player : players)
                {
                    Object playerSpigot = spigotMethod.invoke(player);
                    sendMessageMethod.invoke(playerSpigot, ChatMessageType.ACTION_BAR, component);
                }
            }
            catch (ReflectiveOperationException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    public static void sendTitle(Collection<? extends Player> players, String title, String subtitle, int fadeIn, int stay, int fadeOut)
    {
        if (Reflection.isV1_8())
        {
            // Use packets, no native API method
            try
            {
                Object titleBaseComponent = createIChatBaseComponent(title);
                Object titlePacket = packetPlayOutTitleConstructor.newInstance(titleEnum, titleBaseComponent, fadeIn, stay, fadeOut);

                Object subtitlePacket = null;

                if (subtitle != null)
                {
                    Object subtitleBaseComponent = createIChatBaseComponent(subtitle);
                    subtitlePacket = packetPlayOutTitleConstructor.newInstance(subtitleEnum, subtitleBaseComponent, fadeIn, stay, fadeOut);
                }

                for (Player player : players)
                {
                    sendPacket(player, titlePacket);

                    if (subtitlePacket != null)
                    {
                        sendPacket(player, subtitlePacket);
                    }
                }
            }
            catch (ReflectiveOperationException ex)
            {
                ex.printStackTrace();
            }
        }
        else
        {
            // Use Player#sendTitle(String, String, int, int, int);
            try
            {
                for (Player player : players)
                {
                    sendTitleMethod.invoke(player, title, subtitle, fadeIn, stay, fadeOut);
                }
            }
            catch (ReflectiveOperationException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    private static Object createIChatBaseComponent(String text) throws ReflectiveOperationException
    {
        return chatSerializerMethod.invoke(null, "{\"text\":\"" + text + "\"}");
    }

    private static void sendPacket(Player player, Object packet)
    {
        try
        {
            Object playerConnection = playerConnectionField.get(
                entityPlayerClass.cast(
                    getHandleMethod.invoke(
                        craftPlayerClass.cast(player)
                    )
                )
            );

            sendPacketMethod.invoke(playerConnection, packet);
        }
        catch (ReflectiveOperationException ex)
        {
            ex.printStackTrace();
        }
    }
}
