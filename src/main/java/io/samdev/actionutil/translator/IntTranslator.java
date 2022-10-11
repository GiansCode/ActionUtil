package io.samdev.actionutil.translator;

import org.bukkit.entity.Player;

public class IntTranslator implements Translator<Integer> {
    @Override
    public Integer translate(Player player, String input) throws TranslationException {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            throw new TranslationException(input + " is not a valid integer");
        }
    }
}
