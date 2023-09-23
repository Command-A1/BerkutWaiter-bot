package org.example.DataBase.OrderDB;

import org.example.DataBase.DataBase;
import org.example.DataBase.WaiterDB.WaiterStatusManagement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DbLibrary extends DataBase {
    public static ArrayList<Long> oldOrdersID = new ArrayList<>();
    public static HashMap<Long, Order> orders = new HashMap<>();
    public static HashMap<Integer, String> dishesName = new HashMap<>();
    public static ArrayList<String> listCategoriesNameFromDb = new ArrayList<>(Arrays.asList(
            "offerfromthechef",
            "coldappetizers",
            "georgiancuisine"
//            "salads",
//            "soups",
//            "steaks",
//            "hotappetizers",
//            "hotdishes",
//            "fishdishes",
//            "barbecue",
//            "dishesonsaj",
//            "tandoor",
//            "garnish",
//            "dessert",
//            "childrensmenu",
//            "winemap"
    ));


    public static void setDishesName() {

        try {

            for (String categoriesName: listCategoriesNameFromDb) {
                Statement stmt = databaseConn.createStatement();
                ResultSet dishesNameFromOneTable = stmt.executeQuery("select id, name from " + categoriesName);
                while (dishesNameFromOneTable.next())
                    dishesName.put(Integer.parseInt(dishesNameFromOneTable.getString("id")), dishesNameFromOneTable.getString("name"));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public static boolean checkOrdersForEmptiness(){
        return DbLibrary.orders.isEmpty();
    }
}


