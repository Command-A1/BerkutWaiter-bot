package org.example.Telegram.Models;
import com.vdurmont.emoji.EmojiParser;

public enum Emoji {
    WHITE_CHECK_MARK(":white_check_mark:"),
    PEN(":lower_left_crayon:");
    private final String value;

    public String get() {
        return EmojiParser.parseToUnicode(value);
    }

    Emoji(String value) {
        this.value = value;
    }


}
