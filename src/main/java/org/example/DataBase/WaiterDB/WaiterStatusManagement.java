package org.example.DataBase.WaiterDB;

import org.example.DataBase.DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WaiterStatusManagement extends DataBase {
    private static void changeStateShift(String state, String waiterId) { //Общий метод для смены состояния смены офицанта
        try {
            Statement stmt = databaseConn.createStatement();
            stmt.executeUpdate("update waiterlist set onshift = '" + state + "' where idwaiter = '" + waiterId + "'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getNameWaiter(String waiterId) throws SQLException {
        Statement stmt = databaseConn.createStatement();
        ResultSet e = stmt.executeQuery("select firstname,secondname from waiterlist where idwaiter = '" + waiterId + "'");
        e.next();
        return  e.getString("firstname") + e.getString("secondname");
    }

    public static void changeStateShiftToOn(String waiterId) { //Меняет состояние офицанта на "вышел на смену"
        changeStateShift("true", waiterId);
    }

    public static void changeStateShiftToOff(String waiterId) { //Меняет состояние офицанта на "ушел со смены"
        changeStateShift("false", waiterId);
    }

}

