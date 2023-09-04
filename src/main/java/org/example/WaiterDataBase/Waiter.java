package org.example;

import org.example.WaiterDataBase.DataBase;

import java.sql.SQLException;
import java.sql.Statement;

public class Waiter extends DataBase {
    private void changeStateShift(String state, String waiterId){ //Общий метод для смены состояния смены офицанта
        try {
            Statement stmt = databaseConn.createStatement();
            stmt.executeUpdate("update waiterlist set onshift = '" + state + "' where idwaiter = '" + waiterId + "'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void changeStateShiftToOn(String waiterId){ //Меняет состояние офицанта на "вышел на смену"
        changeStateShift("true", waiterId);
    }
    public void changeStateShiftToOff(String waiterId){ //Меняет состояние офицанта на "ушел со смены"
        changeStateShift("false", waiterId);
    }
}

