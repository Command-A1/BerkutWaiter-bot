package org.example.Telegram.KeyBoard.Reply;

import org.example.Telegram.Models.Waiter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

public class ReplyKeyBoardOnShift extends ReplyKeyBoardButton{
    public SendMessage buttonOnShift(Waiter waiter){
        initializeObjectInlineKeyboard();
        rowHorizontal.add(new KeyboardButton("На смену"));
        keyboardRowsVertical.add(rowHorizontal);

        keyboardMarkup.setKeyboard(keyboardRowsVertical);
        waiter.setReplyMarkupSendMessage(keyboardMarkup);
        waiter.setTextInSendMessage("Ну что же приступим к работе");
        return waiter.getSendMessage();
    }
}
