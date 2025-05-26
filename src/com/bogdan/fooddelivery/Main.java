package com.bogdan.fooddelivery;
import model.*;
import service.*;

import java.util.List;
import java.util.ArrayList;
import app.MenuService;


public class Main {
    public static void main(String[] args) {
        UserService userService=new UserService();
        OrderService orderService=new OrderService();
        RestaurantService restaurantService=new RestaurantService();
        ReviewRestaurantService reviewRestaurantService=new ReviewRestaurantService();
        ProductService productService=new ProductService();
        PaymentService paymentService=new PaymentService();


        userService.registerAdmin("Bogdan","aa","aaAA11$$","0182912018","aa","aa","aaa");
        User user1=userService.getAdmin("aa","aaAA11$$");

        Restaurant restaurant1= new Restaurant("Pizzeria Roma", "Braila", "aaaa" , "093782991");
        Restaurant restaurant2= new Restaurant("All Saints", "Braila", "aaaa" , "093782991");
        Restaurant restaurant3= new Restaurant("Beraria H", "Braila", "aaaa" , "093782991");

        restaurantService.addRestaurant(user1,restaurant1);
        restaurantService.addRestaurant(user1,restaurant2);
        restaurantService.addRestaurant(user1,restaurant3);

        Product product1=new Product("pizza","asxasx",20.0,1,"main_course");
        Product product2=new Product("paste","dxa",30.0,1,"main_course");
        Product product3=new Product("supa","xacqec",15.0,2,"main_course");
        Product product4=new Product("ciorba","xacqec",15.0,2,"main_course");
        Product product5=new Product("chiftele","xacqec",15.0,2,"main_course");

        Product product6=new Product("tiramisu","acfaef",40.0,1,"desert");

        Product product7=new Product("cola","dwce",5.0,1,"drink");


        productService.addProductInMenu(user1,restaurant1,product1);
        productService.addProductInMenu(user1,restaurant1,product2);
        productService.addProductInMenu(user1,restaurant1,product3);
        productService.addProductInMenu(user1,restaurant2,product3);
        productService.addProductInMenu(user1,restaurant2,product4);
        productService.addProductInMenu(user1,restaurant3,product5);
        productService.addProductInMenu(user1,restaurant1,product6);
        productService.addProductInMenu(user1,restaurant1,product7);


        MenuService menuService = new MenuService(userService,orderService,restaurantService,productService,reviewRestaurantService,paymentService);
        menuService.startApp();
    }
}
