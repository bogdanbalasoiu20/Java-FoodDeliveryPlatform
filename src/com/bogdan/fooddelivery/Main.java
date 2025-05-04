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


        userService.registerAdmin("Bogdan","aa","aaAA11$$","0182912018","aa","aa","aaa");
        User user1=userService.getAdmin("aa","aaAA11$$");

        Restaurant restaurant1= new Restaurant("Pizzeria Roma", "Braila", "aaaa" , "093782991");
        Restaurant restaurant2= new Restaurant("All Saints", "Braila", "aaaa" , "093782991");
        Restaurant restaurant3= new Restaurant("Beraria H", "Braila", "aaaa" , "093782991");

        restaurantService.addRestaurant(user1,restaurant1);
        restaurantService.addRestaurant(user1,restaurant2);
        restaurantService.addRestaurant(user1,restaurant3);

        Product product1=new MainCourse("pizza","asxasx",20.0,1);
        Product product2=new MainCourse("paste","dxa",30.0,1);
        Product product3=new MainCourse("supa","xacqec",15.0,2);
        Product product4=new MainCourse("ciorba","xacqec",15.0,2);
        Product product5=new MainCourse("chiftele","xacqec",15.0,2);

        Product product6=new Desert("tiramisu","acfaef",40.0,1);

        Product product7=new Drink("cola","dwce",5.0,1);


        productService.addProductInMenu(user1,restaurant1,product1);
        productService.addProductInMenu(user1,restaurant1,product2);
        productService.addProductInMenu(user1,restaurant1,product3);
        productService.addProductInMenu(user1,restaurant2,product3);
        productService.addProductInMenu(user1,restaurant2,product4);
        productService.addProductInMenu(user1,restaurant3,product5);
        productService.addProductInMenu(user1,restaurant1,product6);
        productService.addProductInMenu(user1,restaurant1,product7);


        MenuService menuService = new MenuService(userService,orderService,restaurantService,productService,reviewRestaurantService);
        menuService.startApp();
    }
}
