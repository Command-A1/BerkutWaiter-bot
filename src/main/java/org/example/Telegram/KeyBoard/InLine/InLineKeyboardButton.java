package org.example.Telegram.KeyBoard.InLine;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class InLineKeyboardButton {
    protected ArrayList<Map<Integer, String>> hashMapArrayList;
    protected InlineKeyboardMarkup markupInLine;
    protected List<List<InlineKeyboardButton>> rowsVerticalInLine;
    protected List<InlineKeyboardButton> rowHorizontalInLine;
    public void initializationInlineKeyboard() {
        markupInLine = new InlineKeyboardMarkup();
        rowsVerticalInLine = new ArrayList<>();
        rowHorizontalInLine = new ArrayList<>();
    }
}
