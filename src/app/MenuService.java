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
    private List<Restaurant> sortedRestaurants;


    public MenuService(UserService userservice, OrderService orderservice, RestaurantService restaurantservice, ProductService productservice, ReviewRestaurantService reviewRestaurantService) {
        this.userService=userservice;
        this.orderService=orderservice;
        this.restaurantService=restaurantservice;
        this.productService=productservice;
        this.reviewRestaurantService=reviewRestaurantService;
        this.scanner=new Scanner(System.in);
        this.sortedRestaurants=null;
    }


    public void startApp() {
        showInitialMenu();
    }

    private int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }


    private void showInitialMenu() {
        boolean inInitialMenu = true;

        while (inInitialMenu) {
            System.out.println("\n----Food delivery----" +
                    "\n1. Register" +
                    "\n2. Login" +
                    "\n0. Exit");

            int choice = readInt("Choose an option: ");

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
            showMainMenu();
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
            showMainMenu();
        }
    }

    private void showMainMenu() {
        boolean inMainMenu = true;

        while (inMainMenu) {
            User currentUser = userService.getCurrentUser();
            boolean isAdmin = currentUser.getUserType().equalsIgnoreCase("Admin");

            System.out.println("\n----Main Menu----" +
                    "\n1. Logout" +
                    "\n2. Show user profile" +
                    "\n3. Show restaurants" +
                    "\n4. Orders History" +
                    (isAdmin ?
                            "\n5. Add restaurant" +
                                    "\n6. Add product" +
                                    "\n7. Remove product" +
                                    "\n8. Remove restaurant" : "") +
                    "\n0. Back");

            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    userService.logout();
                    inMainMenu = false;
                    showInitialMenu();
                    break;

                case 2:
                    currentUser.showProfile();
                    break;

                case 3:
                    restaurantService.showRestaurants();
                    restaurantMenu();
                    break;

                case 4:
                    orderService.showOrdersPerUser(currentUser);
                    break;

                case 5:
                    if (!isAdmin) {
                        System.out.println("Invalid option.");
                        break;
                    }

                    System.out.println("\n---- Add a new restaurant ----");
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("City: ");
                    String city = scanner.nextLine();
                    System.out.print("Address: ");
                    String address = scanner.nextLine();
                    System.out.print("Phone Number: ");
                    String phone = scanner.nextLine();

                    restaurantService.addRestaurant(currentUser, new Restaurant(name, city, address, phone));
                    System.out.println("Restaurant '" + name + "' added successfully!");
                    break;

                case 6:
                    if (!isAdmin) {
                        System.out.println("Invalid option.");
                        break;
                    }

                    System.out.println("\n---- Add a new product ----");
                    System.out.print("Name: ");
                    String productName = scanner.nextLine();
                    System.out.print("Description: ");
                    String description = scanner.nextLine();
                    double price = readDouble("Price: ");
                    int quantity = readInt("Quantity: ");
                    int category = readInt("Category: \n1.Main Course\n2.Desert\n3.Drink\nChoose category:");

                    restaurantService.showRestaurants();
                    Restaurant restaurant = chooseRestaurant();
                    if (restaurant == null) {
                        System.out.println("Invalid restaurant selected.");
                        break;
                    }

                    Product productToAdd;
                    if(category == 1){
                        productToAdd =new Product(productName, description, price, quantity,"main_course");
                    }
                    else if(category == 2){
                        productToAdd =new Product(productName, description, price, quantity,"desert");
                    }
                    else {
                        productToAdd =new Product(productName, description, price, quantity,"drink");
                    }

                    productService.addProductInMenu(currentUser, restaurant,productToAdd);
                    break;

                case 7:
                    if (!isAdmin) {
                        System.out.println("Invalid option.");
                        break;
                    }

                    System.out.println("\n---- Remove a product ----");
                    restaurantService.showRestaurants();
                    Restaurant restaurant2 = chooseRestaurant();
                    if (restaurant2 == null) {
                        System.out.println("Invalid restaurant selected.");
                        break;
                    }

                    List<Product> products = restaurant2.showMenu();
                    if (products.isEmpty()) {
                        System.out.println("No products to remove.");
                        break;
                    }

                    restaurantService.showMenu(restaurant2);
                    int productIndex = readInt("Select a product to remove: ");
                    if (productIndex < 1 || productIndex > products.size()) {
                        System.out.println("Invalid product selection.");
                        break;
                    }

                    Product product = products.get(productIndex - 1);
                    productService.removeProductFromMenu(currentUser, restaurant2, product);
                    break;
                case 8:
                    if (!isAdmin) {
                        System.out.println("Invalid option.");
                        break;
                    }

                    System.out.println("\n---- Remove a restaurant ----");
                    restaurantService.showRestaurants();
                    Restaurant restaurant3 = chooseRestaurant();
                    if (restaurant3 == null) {
                        System.out.println("Invalid restaurant selected.");
                        break;
                    }

                    restaurantService.removeRestaurant(currentUser, restaurant3);
                    break;
                case 0:
                    inMainMenu = false;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }


    private Restaurant chooseRestaurant() {
        List<Restaurant> restaurants = (sortedRestaurants != null)
                ? sortedRestaurants
                : new ArrayList<>(restaurantService.getRestaurants());

        if (restaurants.isEmpty()) {
            System.out.println("No restaurants available.");
            return null;
        }


        int choice;
        do {
            choice = readInt("Choose restaurant (0 to cancel): ");
        } while (choice < 0 || choice > restaurants.size());

        return (choice == 0) ? null : restaurants.get(choice - 1);
    }



    private void restaurantMenu() {
        while (true) {
            System.out.println("\n----Restaurants----" +
                    "\n0. Back" +
                    "\n1. Choose a restaurant" +
                    "\n2. Sort restaurants by name" +
                    "\n3. Sort by rating");

            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 0:
                    return;
                case 1:
                    if (sortedRestaurants == null) {
                        restaurantService.showRestaurants();
                    }

                    Restaurant restaurant = chooseRestaurant();
                    if (restaurant != null) {
                        System.out.println("\n---" + restaurant.getName() + " Menu---");
                        restaurant.showMenu();
                        productMenu(restaurant);
                        sortedRestaurants = null;
                    }
                    break;

                case 2:
                    sortedRestaurants = restaurantService.getRestaurantsSortedByName();
                    System.out.println("\nRestaurants sorted by name.");
                    for (int i = 0; i < sortedRestaurants.size(); i++) {
                        System.out.println("----------------");
                        sortedRestaurants.get(i).showDetails(i + 1);
                    }
                    System.out.println("----------------");
                    break;

                case 3:
                    sortedRestaurants = restaurantService.getRestaurantsSortedByRating(reviewRestaurantService);
                    System.out.println("\nRestaurants sorted by rating.");
                    for (int i = 0; i < sortedRestaurants.size(); i++) {
                        Restaurant r = sortedRestaurants.get(i);
                        System.out.println("----------------");
                        System.out.println((i + 1) + ". " + r.getName() + " - Rating: " + reviewRestaurantService.meanRating(r) + "/5");
                    }
                    System.out.println("----------------");
                    break;

                default:
                    System.out.println("\nInvalid option.");
            }
        }
    }


    private void productMenu(Restaurant restaurant) {
        if (restaurant.getProducts().isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        while (true) {
            System.out.println("\n0. Back" +
                    "\n1. Choose a product" +
                    "\n2. Reviews");

            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 0:
                    return;
                case 1:
                    placeOrderFlow(restaurant);
                    break;
                case 2:
                    handleReviews(restaurant);
                    break;
                default:
                    System.out.println("\nInvalid option.");
            }
        }
    }


    private void handleReviews(Restaurant restaurant) {
        while (true) {
            System.out.println("\n--- " + restaurant.getName() + " Reviews ---");
            System.out.println("\n0. Back" +
                    "\n1. Show Reviews" +
                    "\n2. Add Review");

            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 0:
                    return;
                case 1:
                    reviewRestaurantService.showAllReviews(restaurant);
                    break;
                case 2:
                    int rating = readInt("\nRating (1-5): ");
                    while (rating < 1 || rating > 5) {
                        System.out.println("Rating must be between 1 and 5.");
                        rating = readInt("Rating (1-5): ");
                    }

                    System.out.println("\nWrite your review:");
                    String reviewText = scanner.nextLine();

                    ReviewRestaurant review = new ReviewRestaurant(
                            userService.getCurrentUser(),
                            restaurant,
                            reviewText,
                            rating
                    );

                    reviewRestaurantService.addReview(review);
                    break;
                default:
                    System.out.println("\nInvalid option.");
            }
        }
    }


    private List<Product> buildOrderProducts(Restaurant restaurant) {
        List<Product> produseComanda = new ArrayList<>();
        List<Product> meniu = restaurant.getProducts();

        boolean adding = true;
        while (adding) {
            System.out.println("\n--- " + restaurant.getName() + " Menu ---");
            restaurant.showMenu();

            if (produseComanda.size() > 0) {
                System.out.println("0. Place order");
            } else {
                System.out.println("0. Back");
            }

            int choice = readInt("Choose product: ");

            if (choice == 0) {
                if (produseComanda.isEmpty()) {
                    return null;
                } else {
                    adding = false;
                }
            } else if (choice >= 1 && choice <= meniu.size()) {
                Product original = meniu.get(choice - 1);
                int quantity = readInt("Quantity: ");

                Product produsComandat = new Product(
                        original.getName(),
                        original.getDescription(),
                        original.getPrice(),
                        quantity,
                        original.getProductType()
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
            productMenu(restaurant);
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
            int input = readInt("Your choice: ");

            switch (input) {
                case 1:
                    metodaPlata = PaymentMethod.CARD;
                    break;
                case 2:
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
