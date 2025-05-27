package service;
import model.User;
import model.Restaurant;
import model.Product;
import model.Admin;
import repository.ProductRepository;

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
            productRepo.delete(product.getName(), restaurant.getId());
            auditService.logAction("Product "+product.getName()+" REMOVED successfully from restaurant '" + restaurant.getName() + "'!");
        }
    }

}
