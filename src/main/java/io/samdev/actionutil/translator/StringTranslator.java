package io.samdev.actionutil.translator;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class StringTranslator implements Translator<String>
{
    @Override
    public String translate(Player player, String input) throws TranslationException
    {
        input = ChatColor.translateAlternateColorCodes('&', input);
        input = PlaceholderAPI.setPlaceholders(player, input);
        input = input.replace("<nl>", System.lineSeparator());

        return input;
    }
}
