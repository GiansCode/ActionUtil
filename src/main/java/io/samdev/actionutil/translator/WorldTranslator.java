package io.samdev.actionutil.translator;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class WorldTranslator implements Translator<World>
{
    @Override
    public World translate(Player player, String input) throws TranslationException
    {
        return Bukkit.getWorld(input);
    }
}
