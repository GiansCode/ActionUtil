package io.samdev.actionutil.util;

import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class Reflection
{
    private Reflection() {}

    private static final String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

    public static boolean isV1_8()
    {
        return version.contains("v1_8");
    }

    static Class<?> getNmsClass(String className)
    {
        return getClass("net.minecraft.server." + version + "." + className);
    }

    static Class<?> getBukkitClass(String className)
    {
        return getClass("org.bukkit." + className);
    }

    static Class<?> getCraftBukkitClass(String className)
    {
        return getClass("org.bukkit.craftbukkit." + version + "." + className);
    }

    private static Class<?> getClass(String className)
    {
        try
        {
            return Class.forName(className);
        }
        catch (ClassNotFoundException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... parameterTypes)
    {
        try
        {
            Constructor<T> constructor = clazz.getDeclaredConstructor(parameterTypes);

            if (!constructor.isAccessible())
            {
                constructor.setAccessible(true);
            }

            return constructor;
        }
        catch (NoSuchMethodException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    static Field getField(Class<?> clazz, String fieldName)
    {
        try
        {
            Field field = clazz.getDeclaredField(fieldName);

            if (!field.isAccessible())
            {
                field.setAccessible(true);
            }

            return field;
        }
        catch (NoSuchFieldException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes)
    {
        try
        {
            Method method = clazz.getDeclaredMethod(methodName, parameterTypes);

            if (!method.isAccessible())
            {
                method.setAccessible(true);
            }

            return method;
        }
        catch (NoSuchMethodException ex)
        {
            throw new RuntimeException(ex);
        }
    }
}
