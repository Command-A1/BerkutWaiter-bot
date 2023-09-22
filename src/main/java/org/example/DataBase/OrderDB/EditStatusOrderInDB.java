package org.example.DataBase.OrderDB;

import lombok.SneakyThrows;
import org.example.DataBase.DataBase;
import org.example.DataBase.WaiterDB.WaiterStatusManagement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EditStatusOrderInDB extends DataBase {
    @SneakyThrows
    private static ResultSet takeNewOrders(String orderId) { // Берет новые заказы из бд
        Statement stmt = databaseConn.createStatement();
        return stmt.executeQuery("select tableclient, disheid, idclient, orderid,dateconfirmorder from orderclients where orderid like '" + orderId + "'");
    }

    @SneakyThrows
    public static Order getRequiredOrder(String orderId) {
        try (ResultSet newOrders = takeNewOrders(orderId)) {
            newOrders.next();
            return new Order(newOrders.getString("orderid"),
                    newOrders.getString("idclient"),
                    newOrders.getString("tableclient"),
                    newOrders.getString("disheid"),
                    newOrders.getString("dateconfirmorder"));
        }
    }
    private static void changeStateOrder(boolean confirmOrder, String orderId, String nameWaiter, boolean completeOrder) { //Общий метод для смены состояния смены офицанта
        try {
            Statement stmt = databaseConn.createStatement();
            stmt.executeUpdate("update orderclients set confirmorder = '" + confirmOrder + "' where orderid = '" + orderId + "'");
            stmt.executeUpdate("update orderclients set waitername = '" + nameWaiter + "' where orderid = '" + orderId + "'");
            stmt.executeUpdate("update orderclients set completedorder = '" + completeOrder + "' where orderid = '" + orderId + "'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @SneakyThrows
    public static void changeStateConfirmOrderOnTrue(String orderId, String chatId){
        changeStateOrder(true,orderId, WaiterStatusManagement.getFirstNameWaiter(chatId),false);
    }
//    public static void changeStateConfirmOrderOnFalse(String orderId){
//        changeStateOrder("false",orderId);
//    }
}
