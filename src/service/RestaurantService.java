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

    public void removeRestaurant(User user, Restaurant restaurant){
        if(user instanceof Admin){
            if(restaurant!=null && restaurants.contains(restaurant)){
                restaurants.remove(restaurant);
                System.out.println("Restaurant '" + restaurant.getName() + "' removed successfully!");
            }
        }
    }

    public Set<Restaurant> getRestaurants(){
        return restaurants;
    }



    public List<Restaurant> getRestaurantsSortedByName() {
        List<Restaurant> list = new ArrayList<>(restaurants);
        list.sort((r1, r2) -> r1.getName().compareToIgnoreCase(r2.getName()));
        return list;
    }

    public List<Restaurant> getRestaurantsSortedByRating(ReviewRestaurantService reviewService) {
        List<Restaurant> list = new ArrayList<>(restaurants);
        list.sort((r1, r2) -> {
            double rating1 = reviewService.meanRating(r1);
            double rating2 = reviewService.meanRating(r2);
            return Double.compare(rating2, rating1);
        });
        return list;
    }




    public void showMenu(Restaurant restaurant){
        restaurant.showMenu();
    }


}
