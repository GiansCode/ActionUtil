package io.samdev.actionutil.translator;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface Translator<T>
{
    T translate(Player player, String input) throws TranslationException;
}
