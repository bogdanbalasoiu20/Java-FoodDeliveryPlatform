package service;
import model.User;
import model.Restaurant;
import model.Product;
import model.Admin;
import repository.ProductRepository;

import java.util.*;

public class ProductService {
    private ProductRepository productRepo = ProductRepository.getInstance();
    private AuditService auditService = AuditService.getInstance();

    public void addProductInMenu(User user, Restaurant restaurant, Product product){
        if(user instanceof Admin && restaurant != null && product != null){
            if(!productRepo.existsByNameAndRestaurantId(product.getName(), restaurant.getId())){
                productRepo.save(product, restaurant.getId());
                System.out.println("Product '" + product.getName() + "' ADDED successfully to '" + restaurant.getName() + "'!");
                auditService.logAction("Product '" + product.getName() + "' ADDED successfully to '" + restaurant.getName() + "'!");
            }else{
                System.out.println("Product '" + product.getName() + "' ALREADY EXISTS in restaurant '" + restaurant.getName() + "'!");
            }
        }
    }

    public void removeProductFromMenu(User user, Restaurant restaurant, Product product){
        if(user instanceof Admin && restaurant != null && product != null){
            productRepo.disableProduct(product.getName(), restaurant.getId());
            auditService.logAction("Product "+product.getName()+" REMOVED successfully from restaurant '" + restaurant.getName() + "'!");
        }
    }

    public void showUnavailebleProducts(Restaurant restaurant){
        List <Product> products = productRepo.findUnavailableByRestaurantId(restaurant.getId());

        if(products.isEmpty()){
            System.out.println("\nNo product unavailable");
            return;
        }

        System.out.println("\nUnavailable products:");
        int index=1;
        for(Product p:products){
            System.out.println("\n"+index+". "+p.getName()+" -  "+p.getPrice()+" - "+p.getProductType());
        }
    }


    public void makeProductAvailable(User user, Restaurant restaurant, Product product) {
        if (user instanceof Admin && restaurant != null && product != null) {
            productRepo.markAsAvailable(product.getName(), restaurant.getId());
            System.out.println("Product '" + product.getName() + "' is now available!");
            auditService.logAction("Product '" + product.getName() + "' made available");
        }
    }

    public List<Product> unavailableProduct(User user, Restaurant restaurant) {
        List<Product> products = new ArrayList<>();
        if (user instanceof Admin && restaurant != null) {
            products = productRepo.findUnavailableByRestaurantId(restaurant.getId());
        }
        return products;
    }


    public void showBestSellingProductWithRestaurant() {
        String result = productRepo.findBestSellingProductWithRestaurant();
        System.out.println(result);
        auditService.logAction("Viewed best selling product with restaurant info");
    }





}
