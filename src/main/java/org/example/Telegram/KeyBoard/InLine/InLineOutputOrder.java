package org.example.Telegram.KeyBoard.InLine;

import org.example.Telegram.Models.Emoji;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class InLineOutputOrder extends InLineKeyboardButton {
    public void sendMessageWithOrder() {
        initializationInlineKeyboard();

        rowHorizontalInLine.add(new InlineKeyboardButton("Взять заказ"+ Emoji.WHITE_CHECK_MARK.get()));
        rowHorizontalInLine.get(0).setCallbackData("принять");
        rowHorizontalInLine.add(new InlineKeyboardButton("Взять заказ"+ Emoji.WHITE_CHECK_MARK.get()));
        rowHorizontalInLine.get(1).setCallbackData("принять");
        rowsVerticalInLine.add(rowHorizontalInLine);
        markupInLine.setKeyboard(rowsVerticalInLine);



    }
}
