package service;
import java.util.*;
import java.util.List;
import java.util.ArrayList;
import model.Restaurant;
import model.User;
import model.ReviewRestaurant;
public class ReviewRestaurantService {
    private Map<Restaurant, List<ReviewRestaurant>> reviewPerRestaurant= new HashMap<>();

    public void addReview(ReviewRestaurant reviewRestaurant){
        Restaurant restaurant = reviewRestaurant.getRestaurant();
        reviewPerRestaurant.putIfAbsent(restaurant, new ArrayList<>());
        reviewPerRestaurant.get(restaurant).add(reviewRestaurant);
        System.out.println("Review posted successfully");
    }

    public void showAllReviews(Restaurant restaurant){
        List <ReviewRestaurant> reviews =reviewPerRestaurant.get(restaurant);

        if(reviews==null||reviews.isEmpty()){
            System.out.println("\nNo reviews found");
            return;
        }

        System.out.println("\n"+restaurant.getName()+" - Rating "+meanRating(restaurant)+"/5");
        System.out.println("\n*************");
        for(ReviewRestaurant r:reviews){
            r.showReview();
            System.out.println("\n*************");
        }
    }

    public double meanRating(Restaurant restaurant){
        List <ReviewRestaurant> reviews = reviewPerRestaurant.get(restaurant);
        double rating=0;
        if(!reviews.isEmpty()){
            for(ReviewRestaurant r:reviews){
                rating+=r.getRatingUser();
            }
        return rating/reviews.size();
        }
        return 0;
    }
}
