package io.samdev.actionutil.translator;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class StringTranslator implements Translator<String>
{
    public StringTranslator()
    {
        this.placeholderApi = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    private final boolean placeholderApi;

    @Override
    public String translate(Player player, String input) throws TranslationException
    {
        input = ChatColor.translateAlternateColorCodes('&', input);
        input = input.replace("<nl>", System.lineSeparator());

        input = placeholderApi ?
            PlaceholderAPI.setPlaceholders(player, input) :
            input.replace("%player_name%", player.getName());

        return input;
    }
}
