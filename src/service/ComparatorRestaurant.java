package service;
import java.util.Comparator;
import model.Restaurant;

public class ComparatorRestaurant implements Comparator<Restaurant> {
    @Override
    public int compare(Restaurant r1, Restaurant r2){
        return r1.getName().compareToIgnoreCase(r2.getName());
    }
}
