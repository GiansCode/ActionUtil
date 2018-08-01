package io.samdev.actionutil.translators;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundTranslator implements Translator<Sound>
{
    @Override
    public Sound translate(Player player, String input) throws TranslationException
    {
        try
        {
            return Sound.valueOf(input.toUpperCase());
        }
        catch (IllegalArgumentException ex)
        {
            throw new TranslationException(input.toUpperCase() + " is not a valid sound");
        }
    }
}
