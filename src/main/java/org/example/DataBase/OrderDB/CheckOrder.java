package org.example.DataBase.OrderDB;


import org.example.DataBase.DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CheckOrder extends DataBase {
    public ResultSet takeNewOrders() throws SQLException { // Берет новые заказы из бд
        Statement stmt = databaseConn.createStatement();
        return stmt.executeQuery("select tableclient, disheid, idclient, orderid from orderclients where confirmorder = 'false'");
    }

    public void addNewOrder(ResultSet newOrders) throws SQLException { // Добавляем новые заказы в MAP заказов из класса DbLibrary
        while (newOrders.next()) {
            Order Order = new Order(newOrders.getString("orderid"),
                    newOrders.getString("idclient"),
                    newOrders.getString("tableclient"),
                    newOrders.getString("disheid"));
            DbLibrary.orders.put(Order.getOrderId(), Order);
        }
    }


    public int checkIdForDuplicates(ArrayList<Integer> dishesId, int id) { // Выдеат количество блюд по одному ID
        int result = 0;
        for (int dishId : dishesId) if (dishId == id) result++;
        return result;
    }

    public void checkNewOrdersId() { // Выкидывает из MAP заказов те, которые уже выводились
        DbLibrary.orders.entrySet()
                .removeIf(entry -> DbLibrary.oldOrdersID.contains(entry.getKey()));
    }

    private static void ordersOutPut() { // выводит заказы

        // лямбды нужные для вывода через консоль
        Consumer<Integer> dishOutput1 = System.out::println;
        Consumer<Long> dishOutput2 = System.out::println;
        Consumer<String> dishOutput3 = System.out::println;
        Consumer<ArrayList<String>> nameDishOutPut = x -> {
            for (String name : x) System.out.println(name);
        };

        CheckOrder checkOrder = new CheckOrder();

        // cтарт цикла метода
        // беру новые заказы и помещаю их в MAP
        try {
            ResultSet newOrders = checkOrder.takeNewOrders();
            if (checkOrder.checkNewOrdersForNull(newOrders))
                return; // если из бд нет заказов то завершаем итерацию метода
            else checkOrder.addNewOrder(newOrders);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        checkOrder.checkNewOrdersId(); // проверка c предыдущими заказами, нужна чтобы из бд остались только не выведенные заказы

        if (DbLibrary.checkOrdersForEmptiness()) return; // проверка наслучий если список MAP стал пустым

        // вывод заказов в консоль
        for (Map.Entry<Long, Order> dish : DbLibrary.orders.entrySet()) {
            Order checkOrder1 = dish.getValue();
            dishOutput2.accept(checkOrder1.getOrderId());
            dishOutput3.accept(checkOrder1.getClientId());
            dishOutput1.accept(checkOrder1.getTableClient());
            nameDishOutPut.accept(checkOrder1.getDishes());
            System.out.println();
        }

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
        Thread run = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    CheckOrder.ordersOutPut();
                    Thread.sleep(1000); //1000 - 1 сек
                } catch (InterruptedException ex) {
                    System.out.println(ex);
                }
            }
        });

        run.start();
    }


}

