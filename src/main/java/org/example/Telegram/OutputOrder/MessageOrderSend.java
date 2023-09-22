package org.example.Telegram.OutputOrder;


import org.example.DataBase.OrderDB.DbLibrary;
import org.example.DataBase.OrderDB.Order;
import org.example.DataBase.WaiterDB.WaiterStatusManagement;
import org.example.Main;
import org.example.Telegram.KeyBoard.InLine.InLineOutputOrder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Map;

public class MessageOrderSend {
    static InLineOutputOrder inLineOutputOrder = new InLineOutputOrder();
    public static void sendMessageOrder() {
        for (Map.Entry<Long, Order> dish : DbLibrary.orders.entrySet()) {
            for (String id : WaiterStatusManagement.getMapWaiterOnShift()) {
                SendMessage s = new SendMessage();
                s.setText(dish.getValue().toString());
                s.setChatId(id);
                s.setReplyMarkup(inLineOutputOrder.sendMessageWithOrder());
                Main.e.executeMessage(s);
            }
        }
    }


}
