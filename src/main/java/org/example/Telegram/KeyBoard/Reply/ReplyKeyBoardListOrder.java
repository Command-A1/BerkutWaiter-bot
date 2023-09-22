package org.example.Telegram.KeyBoard.Reply;

import org.example.Telegram.Models.Emoji;
import org.example.Telegram.Models.Waiter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

public class ReplyKeyBoardListOrder extends ReplyKeyBoardButton {
    public SendMessage buttonMainMenuWaiter(Waiter waiter){
        initializeObjectInlineKeyboard();

        rowHorizontal.add(new KeyboardButton("Мои заказы "+ Emoji.MEMO.get()));
        rowHorizontal.add(new KeyboardButton("Закончить смену "+ Emoji.SLEEPING.get()));
        keyboardRowsVertical.add(rowHorizontal);
        keyboardMarkup.setKeyboard(keyboardRowsVertical);

        waiter.setReplyMarkupSendMessage(keyboardMarkup);
        waiter.setTextInSendMessage("Работаем))");
        return waiter.getSendMessage();
    }
}
