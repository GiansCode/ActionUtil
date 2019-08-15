package io.samdev.actionutil;

import io.samdev.actionutil.action.*;
import io.samdev.actionutil.translator.*;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class ActionUtil extends JavaPlugin {
    private static ActionUtil instance;

    public ActionUtil() {
        setup();
    }

    @Override
    public void onEnable() {
        setup();
    }

    private void setup() {
        instance = this;
        executor = new ActionExecutor(this);

        registerTranslators();
        registerActionClasses();

        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    private ActionExecutor executor;

    public ActionExecutor getExecutor() {
        return executor;
    }

    public static void executeActions(Player player, String... actions) {
        instance.getExecutor().executeActions(player, Arrays.asList(actions));
    }

    public static void executeActions(Player player, List<String> actions) {
        instance.getExecutor().executeActions(player, actions);
    }

    private void registerTranslators() {
        // Java Types
        registerTranslator(new StringTranslator(), String.class);
        registerTranslator(new BooleanTranslator(), boolean.class);
        registerTranslator(new IntTranslator(), int.class, Integer.class);
        registerTranslator(new DecimalTranslator(), double.class, Double.class);

        // Bukkit Types
        registerTranslator(new SoundTranslator(), Sound.class);
        registerTranslator(new WorldTranslator(), World.class);
    }

    private void registerActionClasses() {
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

        registerActionClass("TELEPORT", TeleportAction.class, World.class, Double.class, Double.class, Double.class, Float.class, Float.class);
    }

    public static void registerTranslator(Translator<?> translator, Class<?>... classes) {
        instance.getExecutor().registerTranslator(translator, classes);
    }

    public static void registerActionClass(String key, Class<? extends Action> actionClass, Class<?>... parameterTypes) {
        instance.getExecutor().registerActionClass(key, actionClass, parameterTypes);
    }
}
