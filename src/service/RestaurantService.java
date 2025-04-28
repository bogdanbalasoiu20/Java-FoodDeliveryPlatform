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
        System.out.println("All restaurants:\n");
        for(Restaurant r:restaurants){
            System.out.println("----------------");
            r.showDetails();
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

    public void sortRestaurants(){
        if(restaurants.isEmpty()){
            System.out.println("No restaurants available");
            return;
        }

        List <Restaurant> restaurantList = new ArrayList<>(restaurants);

        restaurantList.sort((r1, r2) -> r1.getName().compareToIgnoreCase(r2.getName()));

        System.out.println("Restaurants sorted by name");
        for(Restaurant r:restaurantList){
            System.out.println("----------------");
            r.showDetails();
        }
        System.out.println("----------------");



    }

    public void showMenu(Restaurant restaurant){
        restaurant.showMenu();
    }


}
