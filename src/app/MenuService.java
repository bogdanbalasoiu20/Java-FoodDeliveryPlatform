package app;
import service.*;
import java.util.Scanner;


public class MenuService {
    private UserService userService;
    private OrderService orderService;
    private RestaurantService restaurantService;
    private ProductService productService;
    private ReviewRestaurantService reviewRestaurantService;
    private Scanner scanner;

    public MenuService(UserService userservice, OrderService orderservice, RestaurantService restaurantservice, ProductService productservice, ReviewRestaurantService reviewRestaurantService) {
        this.userService=userservice;
        this.orderService=orderservice;
        this.restaurantService=restaurantservice;
        this.productService=productservice;
        this.reviewRestaurantService=reviewRestaurantService;
        this.scanner=new Scanner(System.in);
    }

    public void startApp(){
        boolean running=true;

        while(running){
            System.out.println("\n----Food delivery----" +
                    "\n1.Register"+
                    "\n2.Login"+
                    "\n0.Exit");
        }
    }

}
