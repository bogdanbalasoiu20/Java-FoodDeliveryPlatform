package service;
import model.User;
import model.Restaurant;
import model.Product;
import model.Admin;

public class ProductService {
    public void addProductInMenu(User user, Restaurant restaurant, Product product){
        if(user instanceof Admin && restaurant != null && product != null){
            restaurant.getProducts().add(product);
            System.out.println("Product added successfully");
        }
    }

    public void removeProductFromMenu(User user, Restaurant restaurant, Product product){
        if(user instanceof Admin && restaurant != null && product != null){
            if(restaurant.getProducts().contains(product)){
                restaurant.getProducts().remove(product);
                System.out.println("Product removed successfully");
            }
            else{
                System.out.println("Product not found");
            }
        }
    }

}
