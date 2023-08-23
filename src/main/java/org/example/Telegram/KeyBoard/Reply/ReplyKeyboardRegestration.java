package org.example.Telegram.KeyBoard.Reply;

import org.example.Telegram.Models.Waiter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

public class ReplyKeyboardRegestration extends ReplyKeyBoardButton {
    private static ReplyKeyboardRegestration replyKeyboardRegestration;

    private ReplyKeyboardRegestration() {
        initializeObjectInlineKeyboard();
    }
    public static synchronized ReplyKeyboardRegestration getReplyKeyboardRegestration(){
        if(replyKeyboardRegestration == null){
            replyKeyboardRegestration = new ReplyKeyboardRegestration();
        }
        return replyKeyboardRegestration;
    }
    public SendMessage buttonRegestration(Waiter waiter) {
        waiter.setTextInSendMessage("Давай знакомиться!");
        rowHorizontal.add(new KeyboardButton("Регистрация"));

        keyboardRowsVertical.add(rowHorizontal);

        keyboardMarkup.setKeyboard(keyboardRowsVertical);
        waiter.setReplyMarkupSendMessage(keyboardMarkup);

        return waiter.getSendMessage();
    }
}
