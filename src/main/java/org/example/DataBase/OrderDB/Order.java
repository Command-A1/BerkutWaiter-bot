package org.example.DataBase.OrderDB;

import lombok.*;

import java.util.ArrayList;

@Data
public class Order {
    private int tableClient;
    private String clientId;
    private long orderId;
    private ArrayList<String> dishes = new ArrayList<>();
    public Order(String orderId, String idClient, String clientTable, String dishesId ) { // Конструктор заказ который заполняет все поля
        setOrderId(Long.parseLong(orderId));
        setClientId(idClient);
        setTableClient(Integer.parseInt(clientTable));
        setDishesName(dishesId);
    }
    public void setDishesName(String stringDishesId){ // Заполинт поля имен блюд
        ArrayList<Integer> dishesId = transformDishesIdToDishesName(stringDishesId.split(","));
        for(int id: dishesId)
            this.dishes.add(DbLibrary.dishesName.get(id) + " - " + checkIdForDuplicates(dishesId,id) + "шт." );
    }
    public ArrayList<Integer> transformDishesIdToDishesName(String[] stringDishesId){ // Переводит id заказов из string в int
        ArrayList<Integer> intDishesId = new ArrayList<>();
        for (String id: stringDishesId) intDishesId.add(Integer.parseInt(id));
        return intDishesId;
    }
    public int checkIdForDuplicates(ArrayList<Integer> dishesId, int id){ // Выдеат количество блюд по одному ID
        int result = 0;
        for(int dishId: dishesId) if (dishId == id) result++;
        return result;
    }
}
