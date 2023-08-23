package org.example.WaiterDataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

abstract public class DataBase {
    final static private String key = "org.postgresql.Driver";
    public static void driverConnections(){
        try {
            Class.forName(key);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static Connection databaseConn;


    static {
        try {
            databaseConn = DriverManager.getConnection("jdbc:postgresql://containers-us-west-117.railway.app:7441/railway",
                    "postgres", "29US5H0SPDjZ67I3C6Sp");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
