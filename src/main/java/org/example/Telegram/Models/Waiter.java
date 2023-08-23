package org.example.Telegram.Models;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class Waiter {
    public String getIdWaiter() {
        return idWaiter;
    }

    public void setIdWaiter(String idWaiter) {
        this.idWaiter = idWaiter;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        this.hasFirstName = false;
        this.hasSecondName = true;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
        this.hasSecondName = false;
    }

    public boolean isOnShift() {
        return onShift;
    }

    public void setOnShift(boolean onShift) {
        this.onShift = onShift;
    }

    public Waiter(String idWaiter) {
        this.idWaiter = idWaiter;
        this.sendMessage = new SendMessage();
        this.sendMessage.setChatId(idWaiter);
    }


    public SendMessage getSendMessage() {
        return sendMessage;
    }

    public void setTextInSendMessage(String text) {
        this.sendMessage.setText(text);
    }

    public void setReplyMarkupSendMessage(ReplyKeyboardMarkup keyboardMarkup) {
        sendMessage.setReplyMarkup(keyboardMarkup);
    }

    public void setReplyMarkupSendMessage(InlineKeyboardMarkup keyboardMarkup) {
        sendMessage.setReplyMarkup(keyboardMarkup);
    }

    @Override
    public String toString() {
        return "Ваше имя: " + firstName + "\nВаша фамилия: " + secondName;
    }

    public boolean isHasFirstName() {
        return hasFirstName;
    }

    public void setHasFirstName(boolean hasFirstName) {
        this.hasFirstName = hasFirstName;
    }

    public boolean isHasSecondName() {
        return hasSecondName;
    }

    public void setHasSecondName(boolean hasSecondName) {
        this.hasSecondName = hasSecondName;
    }
    public void setTextCheck(){
        sendMessage.setText("Проверим ваши данные\n\nИмя: "+ firstName+"\n\nФамилия: "+ secondName);
    }


    private boolean hasFirstName = false;
    private boolean hasSecondName = false;
    private SendMessage sendMessage;
    private boolean onShift;
    private String idWaiter;
    private String firstName;
    private String secondName;


}
