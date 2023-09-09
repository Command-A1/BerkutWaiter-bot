package org.example.DataBase.WaiterDB;


import org.example.DataBase.DataBase;
import org.example.Telegram.Models.Waiter;

import java.sql.SQLException;

public class RegistrationWaiter extends DataBase {
    public void recordInDBWaiter(Waiter waiter) {
        try {
            databaseConn.createStatement().executeUpdate("insert into waiterlist  (idwaiter, firstname, secondname) " +
                    "values('" + waiter.getSendMessage().getChatId() + "','" + waiter.getFirstName() + "','"  + waiter.getSecondName() +"')");

        } catch (SQLException e) {
            System.out.println(e.getMessage());        }
    }
}
