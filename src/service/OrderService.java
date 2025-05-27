package service;
import model.Product;
import model.User;
import model.Client;
import model.Order;
import java.util.*;
import repository.OrderRepository;

public class OrderService {
    private final OrderRepository orderRepo = OrderRepository.getInstance();
    private final AuditService auditService = AuditService.getInstance();

    public void placeOrder(User user,Order order){
        orderRepo.save(order);
        auditService.logAction("Order Placed successfully for user '"+user.getName()+"'");
        order.orderDetails();
    }

    public void showOrdersPerUser(User user){
        List<Order> orders=orderRepo.findAllByUser(user);
        if(orders==null||orders.isEmpty()){
            System.out.println("No order has been placed yet");
            auditService.logAction("No order has been placed yet for user '"+user.getName()+"'");
            return;
        }

        System.out.println("Your orders:\n");
        for(Order o:orders){
            o.orderDetails();
        }
    }

}
