package org.example.Telegram.KeyBoard.InLine;

import org.example.Telegram.Models.Emoji;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class InLineKeyBoardCheckData extends InLineKeyboardButton {

    public InlineKeyboardMarkup returnButtonCheckData() {
        initializationInlineKeyboard();
        rowHorizontalInLine.add(new InlineKeyboardButton("Отправить "+ Emoji.WHITE_CHECK_MARK.get()));
        rowHorizontalInLine.get(0).setCallbackData("отправить");
        rowsVerticalInLine.add(rowHorizontalInLine);
        markupInLine.setKeyboard(rowsVerticalInLine);
        return markupInLine;
    }
}
