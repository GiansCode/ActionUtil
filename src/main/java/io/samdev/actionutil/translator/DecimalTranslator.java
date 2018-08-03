package io.samdev.actionutil.translator;

import org.bukkit.entity.Player;

public class DecimalTranslator implements Translator<Double>
{
    @Override
    public Double translate(Player player, String input) throws TranslationException
    {
        try
        {
            return Double.parseDouble(input);
        }
        catch (NumberFormatException ex)
        {
            throw new TranslationException(input + " is not a valid decimal");
        }
    }
}
