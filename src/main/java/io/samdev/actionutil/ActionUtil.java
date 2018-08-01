package io.samdev.actionutil;

import io.samdev.actionutil.actions.*;
import io.samdev.actionutil.translators.BooleanTranslator;
import io.samdev.actionutil.translators.DecimalTranslator;
import io.samdev.actionutil.translators.IntTranslator;
import io.samdev.actionutil.translators.SoundTranslator;
import io.samdev.actionutil.translators.StringTranslator;
import io.samdev.actionutil.translators.TranslationException;
import io.samdev.actionutil.translators.Translator;
import io.samdev.actionutil.util.UtilArray;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import sh.okx.timeapi.api.TimeAPI;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActionUtil extends JavaPlugin implements Listener
{
    private static final Map<String, ActionData> actionDatas = new HashMap<>();
    private static final Map<Class<?>, Translator<?>> translators = new HashMap<>();

    private static ActionUtil instance;

    @Override
    public void onEnable()
    {
        instance = this;

        Bukkit.getPluginManager().registerEvents(this, this);

        registerTranslators();
        registerActionClasses();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Bukkit.getScheduler().runTaskLater(this, () ->
        {
            executeActions(event.getPlayer(),
                "[MESSAGE] &2Hello, this is a message",
                "[BROADCAST] &aHello, this is a broadcast",
                "[DELAY=5s] [ACTIONBARMESSAGE] &cHello World",
                "[DELAY=10s] [SOUND] ENTITY_GENERIC_EXPLODE;3.0;1.0"
            );
        }, 200L);
    }

    private static final Pattern pattern = Pattern.compile("(?<delay>\\[DELAY=(?<delayValue>\\d+[a-z])?])? ?\\[(?<action>.*?)] ?(?<arguments>.*)", Pattern.CASE_INSENSITIVE);
    private static Matcher matcher;

    public static void executeActions(Player player, String... actions)
    {
        executeActions(player, Arrays.asList(actions));
    }

    public static void executeActions(Player player, List<String> actions)
    {
        actions.forEach(action -> handleAction(player, action));
    }

    private static void handleAction(Player player, String input)
    {
        matcher = matcher == null ? pattern.matcher(input) : matcher.reset(input);

        if (!matcher.matches())
        {
            throw new IllegalStateException("action does not follow regex: " + input);
        }

        String action = matcher.group("action").toUpperCase();
        ActionData data = actionDatas.get(action);

        if (data == null)
        {
            throw new IllegalStateException("action " + action + " does not exist");
        }

        String inputArguments = matcher.group("arguments");
        String[] argSplit = inputArguments.split(";");

        Object[] arguments = getTranslatedArgs(player, argSplit, data.getParameterTypes());
        arguments = UtilArray.prepend(arguments, player);

        String delay = matcher.group("delay");

        if (delay == null)
        {
            invoke(data, arguments);
        }
        else
        {
            handleDelay(data, arguments, matcher.group("delayValue"));
        }
    }

    private static void handleDelay(ActionData data, Object[] parameters, String delayValue)
    {
        try
        {
            TimeAPI time = new TimeAPI(matcher.group("delayValue"));
            long ticks = (long) time.getSeconds() * 20;

            Bukkit.getScheduler().runTaskLater(instance, () -> invoke(data, parameters), ticks);
        }
        catch (IllegalArgumentException ex)
        {
            instance.getLogger().severe(delayValue + " is not a valid time");
        }
    }

    private static void invoke(ActionData data, Object[] parameters)
    {
        try
        {
            data.getExecutionMethod().invoke(null, parameters);
        }
        catch (IllegalAccessException /* ignored */ | InvocationTargetException ex)
        {
            instance.getLogger().severe("Error occured while executing action " + data.getKey());
            ex.printStackTrace();
        }
    }

    private static Object[] getTranslatedArgs(Player player, String[] inputs, Class<?>[] parameterTypes)
    {
        if (inputs.length != parameterTypes.length)
        {
            throw new IllegalStateException(String.format("entered arguments size does not match (given %d, expected %d)", inputs.length, parameterTypes.length));
        }

        Object[] arguments = new Object[parameterTypes.length];

        for (int i = 0; i < inputs.length; i++)
        {
            String input = inputs[i];

            Translator<?> translator = translators.get(parameterTypes[i]);

            if (translator == null)
            {
                throw new IllegalStateException("translator does not exist for type " + input);
            }

            try
            {
                arguments[i] = translator.translate(player, input);
            }
            catch (TranslationException ex)
            {
                ex.printStackTrace();
            }
        }

        return arguments;
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
    }

    public static void registerTranslator(Translator<?> translator, Class<?>... classes)
    {
        for (Class<?> clazz : classes)
        {
            translators.put(clazz, translator);
        }
    }

    public static void registerActionClass(String key, Class<?> actionClass, Class<?>... parameterTypes)
    {
        try
        {
            Method method = actionClass.getMethod("execute", prependPlayerType(parameterTypes));

            instance.getLogger().info("Registering action " + key);
            actionDatas.put(key, new ActionData(key, method, parameterTypes));
        }
        catch (NoSuchMethodException ex)
        {
            instance.getLogger().severe("Unable to register action " + key + ": missing execute(...) method");
        }
    }

    /*  "Why don't you use the UtilArray.prepend() method you made?"
     *   Because Java and generics and weird stuff
     */
    private static Class<?>[] prependPlayerType(Class<?>[] array)
    {
        Class<?>[] newArray = new Class<?>[array.length + 1];

        newArray[0] = Player.class;
        System.arraycopy(array, 0, newArray, 1, array.length);

        return newArray;
    }
}
