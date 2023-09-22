package org.example.Telegram.KeyBoard.Reply;

import org.example.Telegram.Models.Emoji;
import org.example.Telegram.Models.Waiter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

public class ReplyKeyBoardOnShift extends ReplyKeyBoardButton{
    public SendMessage buttonOnShiftStart(Waiter waiter,String text){
        initializeObjectInlineKeyboard();
        rowHorizontal.add(new KeyboardButton("На смену "+ Emoji.WAITER.get()));
        keyboardRowsVertical.add(rowHorizontal);

        keyboardMarkup.setKeyboard(keyboardRowsVertical);
        waiter.setReplyMarkupSendMessage(keyboardMarkup);
        waiter.setTextInSendMessage(text);
        return waiter.getSendMessage();
    }
}
