package app;
import model.*;
import service.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

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
                    "\n4. Orders History"+
                    "\n0. Back");

            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    userService.logout();
                    inMainMenu = false;  // ieșim din acest meniu și ne întoarcem la meniul inițial
                    showInitialMenu();
                    break;
                case 2:
                    userService.getCurrentUser().showProfile();
                    break;
                case 3:
                    restaurantMenu();
                    break;
                case 4:
                    orderService.showOrdersPerUser(userService.getCurrentUser());
                    break;
                case 0:
                    inMainMenu = false;  // închidem doar acest meniu
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private Restaurant chooseRestaurant(){
        List<Restaurant> restaurants=new ArrayList<>(restaurantService.getRestaurants());

        System.out.println("Choose Restaurant:");
        int choise=Integer.parseInt(scanner.nextLine());

        return restaurants.get(choise-1);

    }

    private void restaurantMenu(){
        restaurantService.showRestaurants();
        System.out.println("\n----Restaurants----" +
                "\n0.Back"+
                "\n1.Choose a restaurant");
        System.out.println("\nChoose an option: ");

        int  choice = Integer.parseInt(scanner.nextLine());
        if(choice==1){
            Restaurant restaurant=chooseRestaurant();
            System.out.println("\n---"+restaurant.getName()+" Menu---");
            restaurant.showMenu();
            productMenu(restaurant);
        }
        else if(choice==0){
            showMainMenu();
        }

    }

    private void productMenu(Restaurant restaurant){
        if(restaurant.getProducts().size()>0){
            System.out.println("\n0.Back"+
                    "\n1.Choose a product");
            int choice=Integer.parseInt(scanner.nextLine());

            switch(choice){
                case 0:
                    restaurantMenu();
                    break;

                case 1:
                    placeOrderFlow(restaurant);
                    break;

            }
        }
    }

    private List<Product> buildOrderProducts(Restaurant restaurant) {
        List<Product> produseComanda = new ArrayList<>();
        List<Product> meniu = restaurant.getProducts();

        boolean adding = true;
        while (adding) {
            System.out.println("\n--- " + restaurant.getName() + " Menu ---");
            for (int i = 0; i < meniu.size(); i++) {
                Product p = meniu.get(i);
                System.out.println((i + 1) + ". " + p.getName() + " | " + p.getPrice() + " lei");
            }

            if (produseComanda.size() > 0) {
                System.out.println("0. Place order");
            } else {
                System.out.println("0. Back");
            }

            System.out.print("Choose product: ");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 0) {
                if (produseComanda.isEmpty()) {
                    return null;  // 🔁 revenim în meniul anterior
                } else {
                    adding = false;
                }
            } else if (choice >= 1 && choice <= meniu.size()) {
                Product original = meniu.get(choice - 1);
                System.out.print("Quantity: ");
                int quantity = Integer.parseInt(scanner.nextLine());

                Product produsComandat = new Product(
                        original.getName(),
                        original.getDescription(),
                        original.getPrice(),
                        quantity
                );
                produseComanda.add(produsComandat);
                System.out.println("Added " + original.getName() + " x" + quantity);
            } else {
                System.out.println("Invalid choice.");
            }
        }

        return produseComanda;
    }



    private void placeOrderFlow(Restaurant restaurant) {
        List<Product> produseComanda = buildOrderProducts(restaurant);

        if (produseComanda == null) {
            productMenu(restaurant); // 🔁 revenim dacă s-a apăsat Back
            return;
        }

        if (produseComanda.isEmpty()) {
            System.out.println("No products selected.");
            return;
        }

        Order comanda = new Order(userService.getCurrentUser(), restaurant, produseComanda);
        orderService.placeOrder(userService.getCurrentUser(), comanda);

        // Plata
        PaymentMethod metodaPlata = null;
        while (metodaPlata == null) {
            System.out.println("Choose payment method: ");
            System.out.println("1. Card");
            System.out.println("2. Cash");
            System.out.print("Your choice: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    metodaPlata = PaymentMethod.CARD;
                    break;
                case "2":
                    metodaPlata = PaymentMethod.CASH;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }

        Payment payment = new Payment(comanda, metodaPlata);
        System.out.println("\nPayment successful!");
        payment.paymentDetails();
        comanda.setStatus("Paid");

        System.out.println("\nOrder has been placed successfully!");
    }





}
