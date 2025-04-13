package com.bogdan.fooddelivery;
import model.User;
import model.Client;
import model.Admin;
import service.UserService;


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
        userService.logout();
        userService.logout();
    }
}
