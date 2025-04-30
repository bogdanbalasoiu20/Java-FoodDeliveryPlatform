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

        Restaurant restaurant1= new Restaurant("Restaurant1", "Braila", "aaaa" , "093782991");
        Restaurant restaurant2= new Restaurant("Restaurant2", "Braila", "aaaa" , "093782991");
        Restaurant restaurant3= new Restaurant("Restaurant3", "Braila", "aaaa" , "093782991");

        restaurantService.addRestaurant(user1,restaurant1);
        restaurantService.addRestaurant(user1,restaurant2);
        restaurantService.addRestaurant(user1,restaurant3);

        productService.addProductInMenu(user1,restaurant1,new Product("pizza","asxasx",20,1));
        productService.addProductInMenu(user1,restaurant1,new Product("paste","dxa",30,1));
        productService.addProductInMenu(user1,restaurant1,new Product("supa","xacqec",15,2));


        MenuService menuService = new MenuService(userService,orderService,restaurantService,productService,reviewRestaurantService);
        menuService.startApp();
    }
}
