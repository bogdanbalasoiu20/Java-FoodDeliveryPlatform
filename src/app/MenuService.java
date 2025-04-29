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


    public void startApp() {
        showInitialMenu();
    }

    private void showInitialMenu() {
        boolean inInitialMenu = true;

        while (inInitialMenu) {
            System.out.println("\n----Food delivery----" +
                    "\n1. Register" +
                    "\n2. Login" +
                    "\n0. Exit");

            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    handleRegister();
                    break;
                case 2:
                    handleLogin();
                    break;
                case 0:
                    System.out.println("See you next time!");
                    inInitialMenu = false;
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    private void handleRegister() {
        System.out.println("----Register-----\n");
        System.out.println("Name:");
        String name = scanner.nextLine();

        String email;
        boolean emailValid;
        do {
            System.out.println("Email:");
            email = scanner.nextLine();
            emailValid = userService.checkEmail(email);
            if (!emailValid) {
                System.out.println("Try again!");
            }
        } while (!emailValid);

        String password;
        boolean passwordValid;
        do {
            System.out.println("Password:");
            password = scanner.nextLine();
            passwordValid = userService.checkPassword(password);
            if (!passwordValid) {
                System.out.println("Try again!");
            }
        } while (!passwordValid);

        System.out.println("Phone number:");
        String phoneNumber = scanner.nextLine();
        System.out.println("Country:");
        String country = scanner.nextLine();
        System.out.println("City:");
        String city = scanner.nextLine();
        System.out.println("Address:");
        String address = scanner.nextLine();

        userService.registerUser(name, email, password, phoneNumber, country, city, address);

        System.out.println("Do you want to login now? (y/n)");
        String loginChoice = scanner.nextLine();

        if (loginChoice.equalsIgnoreCase("y")) {
            while (!userService.isLoggedIn()) {
                System.out.println("----Login----");
                System.out.println("Email:");
                String loginEmail = scanner.nextLine();
                System.out.println("Password:");
                String loginPassword = scanner.nextLine();

                userService.login(loginEmail, loginPassword);
            }

        } else if (loginChoice.equalsIgnoreCase("n")) {
            System.out.println("See you next time!");
        }
        if (userService.isLoggedIn()) {
            showMainMenu();  // 🔁 ne întoarcem aici
        }
    }

    private void handleLogin() {
        while (!userService.isLoggedIn()) {
            System.out.println("Email:");
            String email = scanner.nextLine();
            System.out.println("Password:");
            String password = scanner.nextLine();

            userService.login(email, password);
        }

        if (userService.isLoggedIn()) {
            showMainMenu();  // 🔁 ne întoarcem aici
        }
    }

    private void showMainMenu() {
        boolean inMainMenu = true;

        while (inMainMenu) {
            System.out.println("\n----Main Menu----" +
                    "\n1. Logout" +
                    "\n2. Show user profile" +
                    "\n3. Show restaurants" +
                    "\n0. Back");

            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    userService.logout();
                    inMainMenu = false;  // ieșim din acest meniu și ne întoarcem la meniul inițial
                    break;
                case 2:
                    userService.getCurrentUser().showProfile();
                    break;
                case 3:
                    restaurantService.showRestaurants();
                    break;
                case 0:
                    inMainMenu = false;  // închidem doar acest meniu
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }


}
