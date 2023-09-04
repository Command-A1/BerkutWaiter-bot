package org.example.WaiterDataBase;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        // Создем MAP имен заказов
        DbLibrary.setDishesName(DbLibrary.getListCategoriesNameFromDb());

        // метод который постоянно вызывает метод вывода заказов
        Thread run = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Order.ordersOutPut();
                        Thread.sleep(1000); //1000 - 1 сек
                    } catch (InterruptedException ex) {
                    }
                }
            }
        });

        run.start(); // заводим
    }
}

//DELETE FROM orderclients WHERE orderid = '2343';
