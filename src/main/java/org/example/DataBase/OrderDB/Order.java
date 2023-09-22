package org.example.DataBase.OrderDB;

import lombok.*;

import java.util.ArrayList;

@Data
public class Order {
    private String dateConfirmOrder;
    private int tableClient;
    private String clientId;
    private long orderId;
    private ArrayList<String> dishes = new ArrayList<>();

    public Order(String orderId, String idClient, String clientTable, String dishesId, String dateConfirmOrder) { // Конструктор заказ который заполняет все поля
        setOrderId(Long.parseLong(orderId));
        setClientId(idClient);
        setTableClient(Integer.parseInt(clientTable));
        setDishesName(dishesId);
        setDateConfirmOrder(dateConfirmOrder);
    }

    public void setDishesName(String stringDishesId) { // Заполинт поля имен блюд
        ArrayList<Integer> dishesId = transformDishesIdToDishesName(stringDishesId.split(","));
        int i = 1;
        for (int id : dishesId) {
            this.dishes.add(i + ". " + DbLibrary.dishesName.get(id) + " - " + checkIdForDuplicates(dishesId, id) + "шт.\n");
            i++;
        }
    }

    public ArrayList<Integer> transformDishesIdToDishesName(String[] stringDishesId) { // Переводит id заказов из string в int
        ArrayList<Integer> intDishesId = new ArrayList<>();
        for (String id : stringDishesId) intDishesId.add(Integer.parseInt(id));
        return intDishesId;
    }

    public int checkIdForDuplicates(ArrayList<Integer> dishesId, int id) { // Выдеат количество блюд по одному ID
        int result = 0;
        for (int dishId : dishesId) if (dishId == id) result++;
        return result;
    }

    private String getListDishes() {
        StringBuilder s = new StringBuilder();
        for (String dish : dishes) {
            s.append(dish);
        }
        return String.valueOf(s);
    }

    @Override
    public String toString() {
        return "Номер заказа – " + ":"+getOrderId() +":"+
                "\n\n" +
                "Время заказа – " + getDateConfirmOrder() +
                "\n\n" +
                "Столик – " + getTableClient() +
                "\n\n" +
                "Заказ:\n\n" + getListDishes();
    }


}

