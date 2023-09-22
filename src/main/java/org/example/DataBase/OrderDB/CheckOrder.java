package org.example.DataBase.OrderDB;


import org.example.DataBase.DataBase;
import org.example.DataBase.WaiterDB.WaiterStatusManagement;
import org.example.Telegram.OutputOrder.MessageOrderSend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CheckOrder extends DataBase {
    static CheckOrder checkOrder = new CheckOrder();

    public ResultSet takeNewOrders() throws SQLException { // Берет новые заказы из бд
        Statement stmt = databaseConn.createStatement();
        return stmt.executeQuery("select tableclient, disheid, idclient, orderid,dateconfirmorder from orderclients where confirmorder = 'false'");
    }

    public void addNewOrder(ResultSet newOrders) throws SQLException { // Добавляем новые заказы в MAP заказов из класса DbLibrary
        while (newOrders.next()) {
            DbLibrary.orders.put(Long.valueOf(newOrders.getString("orderid")), new Order(newOrders.getString("orderid"),
                    newOrders.getString("idclient"),
                    newOrders.getString("tableclient"),
                    newOrders.getString("disheid"),
                    newOrders.getString("dateconfirmorder")));
        }
    }


    public void checkNewOrdersId() { // Выкидывает из MAP заказов те, которые уже выводились
        DbLibrary.orders.entrySet()
                .removeIf(entry -> DbLibrary.oldOrdersID.contains(entry.getKey()));
    }

    public static void getOrdersWithDB() {
        try {
            WaiterStatusManagement.recordWaiterOnShift();
            ResultSet newOrders = checkOrder.takeNewOrders();
            if (!checkOrder.checkNewOrdersForNull(newOrders))
                checkOrder.addNewOrder(newOrders);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void ordersOutPut() { // выводит заказы


        // cтарт цикла метода
        // беру новые заказы и помещаю их в MAP

        getOrdersWithDB();

        checkOrder.checkNewOrdersId(); // проверка c предыдущими заказами, нужна чтобы из бд остались только не выведенные заказы

        if (DbLibrary.checkOrdersForEmptiness()) return; // проверка наслучий если список MAP стал пустым

        // вывод заказов в бота
        MessageOrderSend.sendMessageOrder();

        // записываю ID выведенных заказов
        for (Map.Entry<Long, Order> dish : DbLibrary.orders.entrySet())
            DbLibrary.oldOrdersID.add(dish.getValue().getOrderId());

        // чищу MAP чтоб потом записать в него новые заказы из бд
        DbLibrary.orders.clear();
        DbLibrary.orders = new HashMap<>();
    }

    public boolean checkNewOrdersForNull(ResultSet newOrders) {
        return newOrders == null;
    }

    public static void continuousOutPut() {
        Thread run = new Thread(() -> {
            while (true) {
                try {
                    CheckOrder.ordersOutPut();
                    Thread.sleep(1000); //1000 - 1 сек
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        run.start(); // заводим

    }
}
