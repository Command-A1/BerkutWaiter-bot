package org.example.DataBase.WaiterDB;


import lombok.*;
import org.example.DataBase.DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class WaiterStatusManagement extends DataBase {

    @Getter
    private static ArrayList<String> mapWaiterOnShift =new ArrayList<>();
    private static void changeStateShift(String state, String waiterId) { //Общий метод для смены состояния смены офицанта
        try {
            Statement stmt = databaseConn.createStatement();
            stmt.executeUpdate("update waiterlist set onshift = '" + state + "' where idwaiter = '" + waiterId + "'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getFullNameWaiter(String waiterId) throws SQLException {
        Statement stmt = databaseConn.createStatement();
        ResultSet e = stmt.executeQuery("select firstname,secondname from waiterlist where idwaiter = '" + waiterId + "'");
        e.next();
        return e.getString("firstname") + e.getString("secondname");
    }public static String getFirstNameWaiter(String waiterId) throws SQLException {
        Statement stmt = databaseConn.createStatement();
        ResultSet e = stmt.executeQuery("select firstname from waiterlist where idwaiter = '" + waiterId + "'");
        e.next();
        return e.getString("firstname");
    }

    public static boolean checkWaiterStatusShift(String waiterId) throws SQLException {
        Statement stmt = databaseConn.createStatement();
        ResultSet e = stmt.executeQuery("select onshift from waiterlist where idwaiter = '" + waiterId + "'");
        e.next();
        return e.getBoolean("onshift");
    }
    public static void recordWaiterOnShift() throws SQLException{
        Statement stmt = databaseConn.createStatement();
        ResultSet e = stmt.executeQuery("select idwaiter from waiterlist where onshift = 'true'");
        while (e.next()){
            if(!mapWaiterOnShift.contains(e.getString("idwaiter")))
                mapWaiterOnShift.add(e.getString("idwaiter"));
        }
    }
    public static void changeStateShiftToOn(String waiterId) { //Меняет состояние офицанта на "вышел на смену"
        changeStateShift("true", waiterId);
    }

    public static void changeStateShiftToOff(String waiterId) { //Меняет состояние офицанта на "ушел со смены"
        changeStateShift("false", waiterId);
    }

}

