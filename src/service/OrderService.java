package service;
import model.Product;
import model.User;
import model.Client;
import model.Order;
import java.util.*;
import repository.OrderRepository;

public class OrderService {
    OrderRepository orderRepo = OrderRepository.getInstance();

    public void placeOrder(User user,Order order){
        orderRepo.save(order);
        order.orderDetails();
    }

    public void showOrdersPerUser(User user){
        List<Order> orders=orderRepo.findAllByUser(user);
        if(orders==null||orders.isEmpty()){
            System.out.println("No order has been placed yet");
            return;
        }

        System.out.println("Your orders:\n");
        for(Order o:orders){
            o.orderDetails();
        }
    }

}
