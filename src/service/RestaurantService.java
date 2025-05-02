package service;
import model.Restaurant;
import model.User;
import model.Admin;

import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public class RestaurantService{
    private Set<Restaurant> restaurants =new HashSet<>();

    public void showRestaurants(){
        int index=1;
        System.out.println("\nAll restaurants:\n");
        for(Restaurant r:restaurants){
            System.out.println("----------------");
            r.showDetails(index);
            index++;
        }
        System.out.println("----------------");
    }

    public void addRestaurant(User user, Restaurant restaurant){
        if(user instanceof Admin){
            if(restaurant!=null){
                restaurants.add(restaurant);
            }
        }
    }

    public Set<Restaurant> getRestaurants(){
        return restaurants;
    }

    public void sortRestaurantsByName(){
        if(restaurants.isEmpty()){
            System.out.println("No restaurants available");
            return;
        }

        List <Restaurant> restaurantList = new ArrayList<>(restaurants);

        restaurantList.sort((r1, r2) -> r1.getName().compareToIgnoreCase(r2.getName()));

        System.out.println("Restaurants sorted by name");
        int index=1;
        for(Restaurant r:restaurantList){
            System.out.println("----------------");
            r.showDetails(index);
            index++;
        }
        System.out.println("----------------");



    }

    public void sortRestaurantsByRating(ReviewRestaurantService reviewService){
        if(restaurants.isEmpty()){
            System.out.println("No restaurants available");
            return;
        }

        List<Restaurant> restaurantList = new ArrayList<>(restaurants);

        restaurantList.sort((r1, r2) -> {
            double rating1 = reviewService.meanRating(r1);
            double rating2 = reviewService.meanRating(r2);
            return Double.compare(rating2, rating1);
        });

        System.out.println("Restaurants sorted by rating");
        int index = 1;
        for(Restaurant r : restaurantList){
            System.out.println("----------------");
            System.out.println(index + ". " + r.getName() + " - Rating: " + reviewService.meanRating(r) + "/5");
            index++;
        }
        System.out.println("----------------");
    }



    public void showMenu(Restaurant restaurant){
        restaurant.showMenu();
    }


}
