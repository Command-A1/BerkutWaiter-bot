package org.example.Telegram;

import org.example.DataBase.WaiterDB.WaiterStatusManagement;
import org.example.Telegram.KeyBoard.Reply.ReplyKeyBoardOnShift;
import org.example.Telegram.Models.Waiter;

import org.example.DataBase.OrderDB.DbLibrary;
import org.example.DataBase.OrderDB.CheckOrder;
import org.example.DataBase.WaiterDB.RegistrationWaiter;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.sql.SQLException;
import java.util.HashMap;

public class TelegramBot extends TelegramLongPollingBot {
    public TelegramBot() {
        DbLibrary.setDishesName();
        CheckOrder.continuousOutPut();
    }

    private ReplyKeyBoardOnShift replyKeyBoardOnShift = new ReplyKeyBoardOnShift();
    private HashMap<Long, Waiter> mapWaiter = new HashMap<>();
//    private ReplyKeyboardRegestration replyKeyboardRegestration;
//    private InLineKeyBoardCheckData inLineKeyBoardCheckData;
    private RegistrationWaiter registrationWaiter = new RegistrationWaiter();
    private Update update;

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


            if (update.getMessage().hasText()) {
                switch (update.getMessage().getText()) {
                    case "/start":
                        startWaiter();
                    case "На смену \uD83E\uDD35":
                        onShiftReplyButton();
                        break;
                    default:
                        sendMessageOnlyText("Не понимаю о чем вы. Попробуйте на кнопочки потыкать", update.getMessage().getChatId());
                        break;
                }
            }

        } else if (update.hasCallbackQuery()) {
            if (update.getCallbackQuery().getData().equals("отправить")) {
                registrationWaiter.recordInDBWaiter(mapWaiter.get(update.getCallbackQuery().getMessage().getChatId()));
                deleteMessage();
                sendMessageOnlyText("Отправлено", update.getCallbackQuery().getMessage().getChatId());
            }
        }

    }

    private void startWaiter() {
        try {
            sendMessageOnlyText("Привет, " + WaiterStatusManagement.getNameWaiter(String.valueOf(update.getMessage().getChatId())), update.getMessage().getChatId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        executeMessage(replyKeyBoardOnShift.buttonOnShift(mapWaiter.get(update.getMessage().getChatId())));
    }

    private void onShiftReplyButton() {
        WaiterStatusManagement.changeStateShiftToOn(String.valueOf(update.getMessage().getChatId()));
    }

//    private void outputEnterDataWaiter() {
//        inLineKeyBoardCheckData = new InLineKeyBoardCheckData();
//        mapWaiter.get(update.getMessage().getChatId()).setReplyMarkupSendMessage(inLineKeyBoardCheckData.returnButtonCheckData());
//        mapWaiter.get(update.getMessage().getChatId()).setTextCheck();
//        executeMessage(mapWaiter.get(update.getMessage().getChatId()).getSendMessage());
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
    public void executeMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.out.println("Ошибка " + e.getMessage());
        }
    }

}
