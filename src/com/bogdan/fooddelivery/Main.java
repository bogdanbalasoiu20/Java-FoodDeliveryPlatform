package com.bogdan.fooddelivery;
import model.User;
import model.Client;
import model.Admin;


public class Main {
    public static void main(String[] args) {
        User client1=new Client("Bogdan","balasoiu.bogdan@gmail.com","123456","0987388292","romania","bucuresti","iuliu maniu 244j");
        client1.showProfile();

    }
}
