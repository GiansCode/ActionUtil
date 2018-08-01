package io.samdev.actionutil.translators;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface Translator<T>
{
    T translate(Player player, String input) throws TranslationException;
}
