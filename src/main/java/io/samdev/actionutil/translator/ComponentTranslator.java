package io.samdev.actionutil.translator;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.libs.kyori.adventure.text.Component;
import me.clip.placeholderapi.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class ComponentTranslator implements Translator<Component> {
    public ComponentTranslator() {
        this.placeholderApi = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    private final boolean placeholderApi;

    @Override
    public Component translate(Player player, String input) throws TranslationException {
        input = input.replace("<nl>", System.lineSeparator());

        input = placeholderApi ?
                PlaceholderAPI.setPlaceholders(player, input) :
                input.replace("%player_name%", player.getName());

        return LegacyComponentSerializer.legacyAmpersand().deserialize(input);
    }
}
