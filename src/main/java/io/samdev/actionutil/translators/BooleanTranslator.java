package io.samdev.actionutil.translators;

import org.bukkit.entity.Player;

public class BooleanTranslator implements Translator<Boolean>
{
    @Override
    public Boolean translate(Player player, String input) throws TranslationException
    {
        switch (input.toLowerCase())
        {
            case "true":
                return true;

            case "false":
                return false;

            default:
                throw new TranslationException(input + " is not a valid boolean");
        }
    }
}
