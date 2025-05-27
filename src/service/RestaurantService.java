package service;
import model.Restaurant;
import model.User;
import model.Admin;
import repository.RestaurantRepository;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public class RestaurantService{
    private final RestaurantRepository restaurantRepo = RestaurantRepository.getInstance();
    private final AuditService auditService = AuditService.getInstance();

    public void showRestaurants(){
        int index=1;
        List<Restaurant> restaurants = restaurantRepo.findAll();
        auditService.logAction("Show Restaurants");
        System.out.println("\nAll restaurants:\n");
        for(Restaurant r:restaurants){
            System.out.println("----------------");
            r.showDetails(index++);
        }
        System.out.println("----------------");
    }

    public void addRestaurant(User user, Restaurant restaurant){
        if(user instanceof Admin && restaurant!=null){
            if(!restaurantRepo.existsByName(restaurant.getName())){
                restaurantRepo.save(restaurant);
                System.out.println("Restaurant '"+restaurant.getName()+"' has been added successfully");
                auditService.logAction("Restaurant '"+restaurant.getName()+"' has been ADDED SUCCESSFULLY");
            }else{
                int id=restaurantRepo.getIdByName(restaurant.getName());
                restaurant.setId(id);
                System.out.println("Restaurant '"+restaurant.getName()+"' ALREADY EXISTS");
            }
        }
    }

    public void removeRestaurant(User user, Restaurant restaurant){
        if(user instanceof Admin){
            if(restaurant!=null){
                restaurantRepo.delete(restaurant.getId());
                auditService.logAction("Restaurant '"+restaurant.getName()+"' REMOVED successfully");
            }
        }
    }

    public Set<Restaurant> getRestaurants(){
        return new HashSet<>(restaurantRepo.findAll());
    }



    public List<Restaurant> getRestaurantsSortedByName() {
        List<Restaurant> list = restaurantRepo.findAll();
        list.sort((r1, r2) -> r1.getName().compareToIgnoreCase(r2.getName()));
        return list;
    }

    public List<Restaurant> getRestaurantsSortedByRating(ReviewRestaurantService reviewService) {
        List<Restaurant> list = restaurantRepo.findAll();
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
