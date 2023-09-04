package org.example.WaiterDataBase;

//import lombok.Getter;
//import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Order extends DataBase {

    // Поля заказа
    private int tableClient;
    private String clientId;
    private long orderId;
    private ArrayList<String> dishes = new ArrayList<>();

    public Order(){};

    public Order(String orderId, String idClient, String clientTable, String dishesId ) { // Конструктор заказ который заполняет все поля
        setOrderId(Long.parseLong(orderId));
        setClientId(idClient);
        setTableClient(Integer.parseInt(clientTable));
        setDishes(dishesId);
    }

    public ResultSet takeNewOrders() throws SQLException{ // Берет новые заказы из бд
        Statement stmt = databaseConn.createStatement();
        return stmt.executeQuery("select tableclient, disheid, idclient, orderid from orderclients where confirmorder = 'false'");
    }

    public void addNewOrder(ResultSet newOrders) throws SQLException{ // Добавляем новые заказы в MAP заказов из класса DbLibrary
        while (newOrders.next()) {
            Order order = new Order(newOrders.getString("orderid"),
                    newOrders.getString("idclient"),
                    newOrders.getString("tableclient"),
                    newOrders.getString("disheid"));
            DbLibrary.orders.put(order.orderId, order);
        }
    }

    public void setDishes(String stringDishesId){ // Заполинт поля имен блюд
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

    public void checkNewOrdersId(){ // Выкидывает из MAP заказов те, которые уже выводились
        for (Map.Entry<Long, Order> dish: DbLibrary.orders.entrySet()){
            Order order1 = dish.getValue();
            Stream<Long> stream = DbLibrary.oldOrdersID.stream();

            stream.filter(x -> x == order1.orderId).forEach(x -> DbLibrary.orders.remove(x));
        }
    }
    public static void ordersOutPut(){ // выводит заказы

        // лямбды нужные для вывода через консоль
        Consumer<Integer> dishOutput1 = System.out::println;
        Consumer<Long> dishOutput2 = System.out::println;
        Consumer<String> dishOutput3 = System.out::println;
        Consumer<ArrayList<String>> nameDishOutPut = x -> {
            for(String name: x) System.out.println(name);
        };

        Order order = new Order();

        // cтарт цикла метода
        // беру новые заказы и помещаю их в MAP
        try {
            ResultSet newOrders = order.takeNewOrders();
            if (order.checkNewOrdersForNull(newOrders)) return; // если из бд нет заказов то завершаем итерацию метода
            else order.addNewOrder(newOrders);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

//        for (Map.Entry<Long,Order> dish: DbLibrary.orders.entrySet()) {
//            Order localOrder = dish.getValue();
//            System.out.println(localOrder.getOrderId());
//        }
        order.checkNewOrdersId(); // проверка c предыдущими заказами, нужна чтобы из бд остались только не выведенные заказы

        if(DbLibrary.checkOrdersForEmptiness()) return; // проверка наслучий если список MAP стал пустым

        // вывод заказов в консоль
        for (Map.Entry<Long,Order> dish: DbLibrary.orders.entrySet()){
            Order order1 = dish.getValue();
            dishOutput2.accept(order1.getOrderId());
            dishOutput3.accept(order1.getClientId());
            dishOutput1.accept(order1.getTableClient());
            nameDishOutPut.accept(order1.getDishes());
            System.out.println();
        }

        // записываю ID выведенных заказов
        for (Map.Entry<Long,Order> dish: DbLibrary.orders.entrySet())
            DbLibrary.oldOrdersID.add(dish.getValue().getOrderId());

        // чищу MAP чтоб потом записать в него новые заказы из бд
        DbLibrary.orders.clear();
        DbLibrary.orders = new HashMap<>();
    }
    public boolean checkNewOrdersForNull(ResultSet newOrders){
        return newOrders == null;
    }
    public int getTableClient() {
        return tableClient;
    }

    public void setTableClient(int tableClient) {
        this.tableClient = tableClient;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public ArrayList<String> getDishes() {
        return dishes;
    }
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}

