package io.samdev.actionutil.util;

public final class UtilArray
{
    private UtilArray() {}

    public static Object[] prepend(Object[] array, Object value)
    {
        Object[] newArray = new Object[array.length + 1];

        newArray[0] = value;
        System.arraycopy(array, 0, newArray, 1, array.length);

        return newArray;
    }
}
