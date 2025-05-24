package service;
import model.User;
import model.Restaurant;
import model.Product;
import model.Admin;
import repository.ProductRepository;

public class ProductService {
    private ProductRepository productRepo = new ProductRepository();

    public void addProductInMenu(User user, Restaurant restaurant, Product product){
        if(user instanceof Admin && restaurant != null && product != null){
            productRepo.save(product, restaurant.getId());
            System.out.println("Product '" + product.getName() + "' added successfully to '" + restaurant.getName() + "'!");
        }
    }

    public void removeProductFromMenu(User user, Restaurant restaurant, Product product){
        if(user instanceof Admin && restaurant != null && product != null){
            productRepo.delete(product.getName(), restaurant.getId());
        }
    }

}
