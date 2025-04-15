package com.bogdan.fooddelivery;
import model.*;
import service.OrderService;
import service.UserService;
import service.ReviewRestaurantService;
import service.RestaurantService;
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

        Restaurant restaurant1 = new Restaurant("Restaurant1","Bucuresti","dsqsfce","08799199");
        Restaurant restaurant2 = new Restaurant("Restaurant2","Bucuresti","dsqsfce","08799199");
        Restaurant restaurant3 = new Restaurant("Pizzerie","Bucuresti","dsqsfce","08799199");

        ReviewRestaurant review1= new ReviewRestaurant(current_user,restaurant1,"foarte bun",5);
        ReviewRestaurant review2= new ReviewRestaurant(current_user,restaurant1,"bun",3);
        ReviewRestaurant review3= new ReviewRestaurant(current_user,restaurant2,"slab",2);
        review1.showReview();

        ReviewRestaurantService reviewRestaurantService=new ReviewRestaurantService();
        reviewRestaurantService.addReview(review1);
        reviewRestaurantService.addReview(review2);
        reviewRestaurantService.showAllReviews(restaurant1);
        System.out.println(reviewRestaurantService.meanRating(restaurant1));

        RestaurantService restaurantService=new RestaurantService();
        restaurantService.addRestaurant(current_user,restaurant1);
        restaurantService.showRestaurants();
        System.out.println(restaurantService.getRestaurants());

        User user2= new Admin("andrei","andrei@gmail.com","F34Ra##2","0878292023","Romania","Braila","zambileor4");
        restaurantService.addRestaurant(user2,restaurant2);
        restaurantService.addRestaurant(user2,restaurant3);
        restaurantService.addRestaurant(user2,restaurant1);

        restaurantService.showRestaurants();
    }
}
