package service;
import model.Product;
import model.User;
import model.Client;
import model.Order;
import java.util.*;

public class OrderService {
    private Map<User, List<Order>> ordersPerUser = new HashMap<>();

    public void placeOrder(User user,Order order){
        ordersPerUser.putIfAbsent(user, new ArrayList<>());
        ordersPerUser.get(user).add(order);
        order.orderDetails();
    }

    public void showOrdersPerUser(User user){
        List<Order> orders=ordersPerUser.get(user);
        if(orders==null||orders.isEmpty()){
            System.out.println("No order has been placed yet");
            return;
        }

        System.out.println("Your orders:\n");
        for(Order o:orders){
            o.orderDetails();
        }
    }

    public List<Order> getOrdersPerUser(User user){
        return ordersPerUser.getOrDefault(user, new ArrayList<>());
    }
}
