package org.example;

import lombok.Data;
import org.example.Telegram.TelegramBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
@Data
public class Main {
    public static TelegramBot e;
    public static void main(String[] args) {

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            e=new TelegramBot();
            botsApi.registerBot(e);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}