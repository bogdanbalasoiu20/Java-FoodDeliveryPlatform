package model;

public class ReviewRestaurant {
    private int id;
    private User user;
    private Restaurant restaurant;
    private String reviewText;
    private int ratingUser;

    public ReviewRestaurant(User user, Restaurant restaurant, String reviewText, int ratingUser){
        this.user=user;
        this.restaurant=restaurant;
        this.reviewText=reviewText;
        this.ratingUser=ratingUser;
    }

    public void setId(int id){
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public String getReviewText() {
        return reviewText;
    }

    public int getRatingUser() {
        return ratingUser;
    }


    public void showReview(){
        System.out.println("\nUser: "+user.getName()+" - Rating: "+ratingUser+"/5");
        System.out.println("\n'"+reviewText+"'");
    }
}
