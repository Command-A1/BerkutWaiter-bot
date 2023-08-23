package org.example.Telegram.KeyBoard.Reply;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public abstract class ReplyKeyBoardButton {
    protected ReplyKeyboardMarkup keyboardMarkup;
    protected List<KeyboardRow> keyboardRowsVertical;
    protected KeyboardRow rowHorizontal;
    protected void initializeObjectInlineKeyboard(){
        keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        keyboardRowsVertical = new ArrayList<>();
        rowHorizontal = new KeyboardRow();
    }


}
