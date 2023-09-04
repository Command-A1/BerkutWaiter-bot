package org.example.Telegram.KeyBoard.Reply;

import org.example.Telegram.Models.Waiter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

public class ReplyKeyboardRegestration extends ReplyKeyBoardButton {
    public SendMessage buttonRegistration(Waiter waiter) {
        waiter.setTextInSendMessage("Давай знакомиться!");
        rowHorizontal.add(new KeyboardButton("Регистрация"));

        keyboardRowsVertical.add(rowHorizontal);

        keyboardMarkup.setKeyboard(keyboardRowsVertical);
        waiter.setReplyMarkupSendMessage(keyboardMarkup);

        return waiter.getSendMessage();
    }
}
