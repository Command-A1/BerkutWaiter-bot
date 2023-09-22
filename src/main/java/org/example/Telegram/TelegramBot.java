package org.example.Telegram;

import org.example.DataBase.OrderDB.EditStatusOrderInDB;
import org.example.DataBase.OrderDB.Order;
import org.example.DataBase.WaiterDB.WaiterStatusManagement;
import org.example.Telegram.KeyBoard.Reply.ReplyKeyBoardListOrder;
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
import java.util.Arrays;
import java.util.HashMap;

public class TelegramBot extends TelegramLongPollingBot {
    public TelegramBot() {
        DbLibrary.setDishesName();

    }

    private ReplyKeyBoardOnShift replyKeyBoardOnShift = new ReplyKeyBoardOnShift();
    private HashMap<Long, Waiter> mapWaiter = new HashMap<>();
    private ReplyKeyBoardListOrder replyKeyBoardListOrder = new ReplyKeyBoardListOrder();
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
                        try {
                            startWaiter();
                        } catch (SQLException e) {
                            System.out.println(e);
                        }
                        break;
                    case "На смену \uD83E\uDD35":
                        onShiftReplyButtonOn();
                        CheckOrder.continuousOutPut();
                        break;
                    case "Мои заказы \uD83D\uDCDD":
                        outPutOrdersWaiterAccepted();
                        break;
                    case "Закончить смену \uD83D\uDE34":
                        onShiftReplyButtonOff();
                        break;
                    default:
                        sendMessageOnlyText("Не понимаю о чем вы. Попробуйте на кнопочки потыкать", update.getMessage().getChatId());
                        break;
                }
            }

        } else if (update.hasCallbackQuery()) {
//            if (update.getCallbackQuery().getData().equals("отправить")) {
//                registrationWaiter.recordInDBWaiter(mapWaiter.get(update.getCallbackQuery().getMessage().getChatId()));
//                deleteMessage();
//                sendMessageOnlyText("Отправлено", update.getCallbackQuery().getMessage().getChatId());
//            }
            if (update.getCallbackQuery().getData().equals("принять")) {
                acceptOrder();
            }
//            else if (update.getCallbackQuery().getData().equals("отменить")) {
//                cancelOrder();
//            }
        }

    }

    private void startWaiter() throws SQLException {
        if (!WaiterStatusManagement.checkWaiterStatusShift(String.valueOf(update.getMessage().getChatId()))) {
            try {
                sendMessageOnlyText("Привет, " + WaiterStatusManagement.getFullNameWaiter(String.valueOf(update.getMessage().getChatId())), update.getMessage().getChatId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            executeMessage(replyKeyBoardOnShift.buttonOnShiftStart(mapWaiter.get(update.getMessage().getChatId()), "Жми 'на смену' и пошли работать"));
        } else {
            executeMessage(replyKeyBoardListOrder.buttonMainMenuWaiter(mapWaiter.get(update.getMessage().getChatId())));
        }
    }

    private void onShiftReplyButtonOn() {
        WaiterStatusManagement.changeStateShiftToOn(String.valueOf(update.getMessage().getChatId()));
        executeMessage(replyKeyBoardListOrder.buttonMainMenuWaiter(mapWaiter.get(update.getMessage().getChatId())));
    }

    private void onShiftReplyButtonOff() {
        mapWaiter.get(update.getMessage().getChatId()).clearListOrdersAccepted();
        WaiterStatusManagement.changeStateShiftToOff(String.valueOf(update.getMessage().getChatId()));
        executeMessage(replyKeyBoardOnShift.buttonOnShiftStart(mapWaiter.get(update.getMessage().getChatId()), "Жду 'на смену' тебя снова"));
    }

    public void outPutOrdersWaiterAccepted() {
        if (!mapWaiter.get(update.getMessage().getChatId()).getOrdersWaiterAccepted().isEmpty()) {
            for (Order order : mapWaiter.get(update.getMessage().getChatId()).getOrdersWaiterAccepted())
                sendMessageOnlyText(order.toString(), update.getMessage().getChatId());
        } else sendMessageOnlyText("У тебя нет взятых заказов", update.getMessage().getChatId());
    }

    //    private void outputEnterDataWaiter() {
//        inLineKeyBoardCheckData = new InLineKeyBoardCheckData();
//        mapWaiter.get(update.getMessage().getChatId()).setReplyMarkupSendMessage(inLineKeyBoardCheckData.returnButtonCheckData());
//        mapWaiter.get(update.getMessage().getChatId()).setTextCheck();
//        executeMessage(mapWaiter.get(update.getMessage().getChatId()).getSendMessage());
//    }
//    private  void cancelOrder(){
//        deleteMessage();
//        EditStatusOrderInDB.changeStateConfirmOrderOnFalse(Arrays.asList(update.getCallbackQuery().getMessage().getText().split(":")).get(1));
//        mapWaiter.get(update.getCallbackQuery().getMessage().getChatId()).removeOrderInListOrderWaiterAccepted(Arrays.asList(update.getCallbackQuery().getMessage().getText().split(":")).get(1));
//    }
    private void acceptOrder() {
        deleteMessage();
        EditStatusOrderInDB.changeStateConfirmOrderOnTrue(Arrays.asList(update.getCallbackQuery().getMessage().getText().split(":")).get(1), String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        mapWaiter.get(update.getCallbackQuery().getMessage().getChatId()).setOrdersWaiterAccepted(Arrays.asList(update.getCallbackQuery().getMessage().getText().split(":")).get(1));
    }

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
