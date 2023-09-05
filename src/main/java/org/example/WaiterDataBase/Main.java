package org.example.WaiterDataBase;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        // Создем MAP имен заказов
        DbLibrary.setDishesName(DbLibrary.getListCategoriesNameFromDb());

        // метод который постоянно вызывает метод вывода заказов

        Order.continuousOutPut();
    }
}

