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


        MenuService menuService = new MenuService(userService,orderService,restaurantService,productService,reviewRestaurantService,paymentService);
        menuService.startApp();
    }
}
