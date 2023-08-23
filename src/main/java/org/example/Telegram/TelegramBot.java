package org.example.Telegram;

import org.example.Telegram.KeyBoard.InLine.InLineKeyBoardCheckData;
import org.example.Telegram.KeyBoard.Reply.ReplyKeyboardRegestration;
import org.example.Telegram.Models.Waiter;

import org.example.WaiterDataBase.Registration;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;

public class TelegramBot extends TelegramLongPollingBot {
    private HashMap<Long, Waiter> mapWaiter = new HashMap<>();
    private ReplyKeyboardRegestration replyKeyboardRegestration;
    private InLineKeyBoardCheckData inLineKeyBoardCheckData;
    private Registration registration = new Registration();
    private Update update;
    private long chatId=0;

    @Override
    public String getBotUsername() {
        return "@BekutWaiterbot";
    }

    @Override
    public String getBotToken() {
        return "6433196359:AAGIIJWynMnu_FVDd4d2mbLZALCGM1HsAOQ";
    }

    @Override
    public void onUpdateReceived(Update update) {
        this.update = update;
        if (update.hasMessage()) {
            if (!mapWaiter.containsKey(update.getMessage().getChatId()))
                mapWaiter.put(update.getMessage().getChatId(), new Waiter(String.valueOf(update.getMessage().getChatId())));


            if (mapWaiter.get(update.getMessage().getChatId()).isHasFirstName()) {
                mapWaiter.get(update.getMessage().getChatId()).setFirstName(update.getMessage().getText());
                sendMessageOnlyText("Введите вашу Фамилию", update.getMessage().getChatId());
            } else if (mapWaiter.get(update.getMessage().getChatId()).isHasSecondName()) {
                mapWaiter.get(update.getMessage().getChatId()).setSecondName(update.getMessage().getText());
                outputEnterDataWaiter();
            }else if(update.getMessage().hasText()){
                switch (update.getMessage().getText()){
                    case "/start":
                        sendMessageOnlyText("Введите ваше Имя",update.getMessage().getChatId());
                        mapWaiter.get(update.getMessage().getChatId()).setHasFirstName(true);
                }
            }

        } else if (update.hasCallbackQuery()) {
            if (update.getCallbackQuery().getData().equals("отправить")) {
                registration.recordInDBWaiter(mapWaiter.get(update.getCallbackQuery().getMessage().getChatId()));
                deleteMessage();
                sendMessageOnlyText("Отправлено",update.getCallbackQuery().getMessage().getChatId());
            }
        }

    }

    private void outputEnterDataWaiter() {
        inLineKeyBoardCheckData = new InLineKeyBoardCheckData();
        mapWaiter.get(update.getMessage().getChatId()).setReplyMarkupSendMessage(inLineKeyBoardCheckData.returnButtonCheckData());
        mapWaiter.get(update.getMessage().getChatId()).setTextCheck();
        executeMessage(mapWaiter.get(update.getMessage().getChatId()).getSendMessage());
    }

//    private void initializeMenuRegestration() {
//        replyKeyboardRegestration = ReplyKeyboardRegestration.getReplyKeyboardRegestration();
//        executeMessage(replyKeyboardRegestration.buttonRegestration(mapWaiter.get(update.getMessage().getChatId())));
//    }

    private void sendMessageOnlyText(String text, long chatId) {
        SendMessage e = new SendMessage();
        e.setText(text);
        e.setChatId(chatId);
        executeMessage(e);
    }
    private void deleteMessage() {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
        deleteMessage.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    private void executeMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.out.println("Ошибка " + e.getMessage());
        }
    }
}
