package io.samdev.actionutil.action;

import io.samdev.actionutil.util.UtilArray;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ActionData
{
    private final String key;

    private final Method executionMethod;
    private final Class<?>[] parameterTypes;

    public ActionData(String key, Method executionMethod, Class<?>[] parameterTypes)
    {
        this.key = key;

        this.executionMethod = executionMethod;
        this.parameterTypes = parameterTypes;
    }

    public String getKey()
    {
        return key;
    }

    public Method getExecutionMethod()
    {
        return executionMethod;
    }

    public Class<?>[] getParameterTypes()
    {
        return parameterTypes;
    }

    public void execute(Player player, Object[] parameters)
    {
        parameters = UtilArray.prepend(parameters, player);
        
        try
        {
            getExecutionMethod().invoke(null, parameters);
        }
        catch (IllegalAccessException /* ignored */ | InvocationTargetException ex)
        {
            ex.printStackTrace();
        }
    }
}
