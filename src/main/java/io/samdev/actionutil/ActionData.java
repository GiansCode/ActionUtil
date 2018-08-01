package io.samdev.actionutil;

import java.lang.reflect.Method;

class ActionData
{
    private final String key;

    private final Method executionMethod;
    private final Class<?>[] parameterTypes;

    ActionData(String key, Method executionMethod, Class<?>[] parameterTypes)
    {
        this.key = key;

        this.executionMethod = executionMethod;
        this.parameterTypes = parameterTypes;
    }

    String getKey()
    {
        return key;
    }

    Method getExecutionMethod()
    {
        return executionMethod;
    }

    Class<?>[] getParameterTypes()
    {
        return parameterTypes;
    }
}
