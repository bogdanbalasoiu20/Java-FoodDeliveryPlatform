package service;
import java.util.*;
import java.util.List;
import java.util.ArrayList;
import model.Restaurant;
import model.User;
import model.ReviewRestaurant;
import repository.ReviewRestaurantRepository;

public class ReviewRestaurantService {
    ReviewRestaurantRepository reviewRestRepo = new ReviewRestaurantRepository();

    public void addReview(ReviewRestaurant reviewRestaurant){
        reviewRestRepo.save(reviewRestaurant);
        System.out.println("Review posted successfully");
    }

    public void showAllReviews(Restaurant restaurant){
        List <ReviewRestaurant> reviews = reviewRestRepo.findAllByRestaurant(restaurant);

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
        List<ReviewRestaurant> reviews = reviewRestRepo.findAllByRestaurant(restaurant);
        if(reviews == null || reviews.isEmpty()) {
            return 0;
        }

        double rating = 0;
        for(ReviewRestaurant r : reviews){
            rating += r.getRatingUser();
        }
        return rating / reviews.size();
    }

}
