package io.samdev.actionutil;

import io.samdev.actionutil.action.*;
import io.samdev.actionutil.translator.BooleanTranslator;
import io.samdev.actionutil.translator.DecimalTranslator;
import io.samdev.actionutil.translator.IntTranslator;
import io.samdev.actionutil.translator.SoundTranslator;
import io.samdev.actionutil.translator.StringTranslator;
import io.samdev.actionutil.translator.Translator;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class ActionUtil extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        registerTranslators();
        registerActionClasses();

        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    public static void executeActions(Player player, String... actions)
    {
        executeActions(player, Arrays.asList(actions));
    }

    public static void executeActions(Player player, List<String> actions)
    {
        ActionExecutor.executeActions(player, actions);
    }

    private void registerTranslators()
    {
        // Java Types
        registerTranslator(new StringTranslator(), String.class);
        registerTranslator(new BooleanTranslator(), boolean.class);
        registerTranslator(new IntTranslator(), int.class, Integer.class);
        registerTranslator(new DecimalTranslator(), double.class, Double.class);

        // Bukkit Types
        registerTranslator(new SoundTranslator(), Sound.class);
    }

    private void registerActionClasses()
    {
        registerActionClass("MESSAGE", MessageAction.class, String.class);
        registerActionClass("BROADCAST", BroadcastAction.class, String.class);

        registerActionClass("CENTERMESSAGE", CenterMessageAction.class, String.class);
        registerActionClass("CENTERBROADCAST", CenterBroadcastAction.class, String.class);

        registerActionClass("JSONMESSAGE", JsonMessageAction.class, String.class);
        registerActionClass("JSONBROADCAST", JsonBroadcastAction.class, String.class);

        registerActionClass("PLAYERCOMMAND", PlayerCommandAction.class, String.class);
        registerActionClass("CONSOLECOMMAND", ConsoleCommandAction.class, String.class);

        registerActionClass("SOUND", SoundAction.class, Sound.class, double.class, double.class);
        registerActionClass("SOUNDBROADCAST", SoundBroadcastAction.class, Sound.class, double.class, double.class);

        registerActionClass("ACTIONBARMESSAGE", ActionbarMessageAction.class, String.class);
        registerActionClass("ACTIONBARBROADCAST", ActionbarBroadcastAction.class, String.class);

        registerActionClass("TITLEMESSAGE", TitleMessageAction.class, String.class, String.class, int.class, int.class, int.class);
        registerActionClass("TITLEBROADCAST", TitleBroadcastAction.class, String.class, String.class, int.class, int.class, int.class);

        registerActionClass("BUNGEE", BungeeAction.class, String.class);
    }

    public static void registerTranslator(Translator<?> translator, Class<?>... classes)
    {
        ActionExecutor.registerTranslator(translator, classes);
    }

    public static void registerActionClass(String key, Class<? extends Action> actionClass, Class<?>... parameterTypes)
    {
        ActionExecutor.registerActionClass(key, actionClass, parameterTypes);
    }
}
