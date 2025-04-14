package com.bogdan.fooddelivery;
import model.*;
import service.OrderService;
import service.UserService;
import java.util.List;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        User client1=new Client("Bogdan","balasoiu.bogdan@gmail.com","123456","0987388292","romania","bucuresti","iuliu maniu 244j");
        client1.showProfile();

        UserService userService=new UserService();
        userService.registerUser("Bogdan","balasoiu.bogdan@gmail.com","Aa$123Aa","0987388292","romania","bucuresti","iuliu maniu 244j");
        System.out.println(userService.getUsers());
        userService.login("balasoiu.bogdan@gmail.com","Aa$123Aa");
        System.out.println(userService.getCurrentUser());
        userService.getCurrentUser().showProfile();

        User current_user=userService.getCurrentUser();
        OrderService orderService=new OrderService();

        List<Product> products=new ArrayList<>();
        products.add(new Product("pizza","adcsdcs",35,2));
        products.add(new Product("paste","sadcs",23,1));

        Order order1 = new Order(current_user,new Restaurant("Restaurant1","Bucuresti","dsqsfce","08799199"),products);
        Order order2 = new Order(current_user,new Restaurant("Restaurant2","Bucuresti","dsqsfce","08799199"),products);
        orderService.placeOrder(current_user,order1);
        orderService.placeOrder(current_user,order2);

        orderService.showOrdersPerUser(current_user);

        Payment p=new Payment(order1,PaymentMethod.CARD);
        p.paymentDetails();
    }
}
