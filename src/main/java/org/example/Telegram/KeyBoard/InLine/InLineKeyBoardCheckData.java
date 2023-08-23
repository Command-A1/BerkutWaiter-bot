package org.example.Telegram.KeyBoard.InLine;

import org.example.Telegram.KeyBoard.Reply.ReplyKeyboardRegestration;
import org.example.Telegram.Models.Emoji;
import org.example.Telegram.Models.Waiter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class InLineKeyBoardCheckData extends InLineKeyboardButton {

    public InlineKeyboardMarkup returnButtonCheckData() {
        initializationInlineKeyboard();
        rowInLine.add(new InlineKeyboardButton("Отправить "+ Emoji.WHITE_CHECK_MARK.get()));
        rowInLine.get(0).setCallbackData("отправить");
        rowsInLine.add(rowInLine);
        markupInLine.setKeyboard(rowsInLine);
        return markupInLine;
    }
}
