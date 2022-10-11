package io.samdev.actionutil;

import io.samdev.actionutil.action.*;
import io.samdev.actionutil.translator.*;
import me.clip.placeholderapi.libs.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class ActionUtil {

    private final Plugin plugin;
    private final ActionExecutor executor;

    private ActionUtil(Plugin plugin) {
        this.plugin = plugin;
        executor = new ActionExecutor(plugin);

        registerTranslators();
        registerActionClasses();

        Bukkit.getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
    }

    public static ActionUtil init(Plugin plugin) {
        return new ActionUtil(plugin);
    }

    public ActionExecutor getExecutor() {
        return executor;
    }

    public void executeActions(Player player, String... actions) {
        executor.executeActions(player, Arrays.asList(actions));
    }

    public void executeActions(Player player, List<String> actions) {
        executor.executeActions(player, actions);
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

        // Adventure Types
        registerTranslator(new ComponentTranslator(), Component.class);
    }

    private void registerActionClasses() {
        registerActionClass("MESSAGE", MessageAction.class, Component.class);
        registerActionClass("BROADCAST", BroadcastAction.class, Component.class);

        registerActionClass("CENTERMESSAGE", CenterMessageAction.class, Component.class);
        registerActionClass("CENTERBROADCAST", CenterBroadcastAction.class, Component.class);

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

    public void registerTranslator(Translator<?> translator, Class<?>... classes) {
        executor.registerTranslator(translator, classes);
    }

    public void registerActionClass(String key, Class<? extends Action> actionClass, Class<?>... parameterTypes) {
        executor.registerActionClass(key, actionClass, parameterTypes);
    }

}
