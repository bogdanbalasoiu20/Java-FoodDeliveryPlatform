package model;
import service.ReviewRestaurantService;

import java.util.ArrayList;
import java.util.List;


public class Restaurant {
    private int id;
    private static int counter=0;
    private String name;
    private String city;
    private String address;
    private String phoneNumber;
    private List<Product> products;

    public Restaurant(String name, String city, String address, String phoneNumber) {
        this.id=++counter;
        this.name = name;
        this.city = city;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.products = new ArrayList<>();
    }

    public int getId(){
        return this.id;
    }

    public String getName() {
        return name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String toString(){
        return name;
    }

    public void showDetails(int index){
        System.out.println(index+". "+name);
        System.out.println(city);
        System.out.println(address);
        System.out.println(phoneNumber);
    }


    public List<Product> showMenu() {
        List<Product> displayedProducts = new ArrayList<>();

        if(products.isEmpty()){
            System.out.println("No products available");
        } else {
            int index = 0;

            System.out.println("\n---- Main Course ----");
            for(Product p : products){
                if(p instanceof MainCourse){
                    System.out.println(++index + ". " + p.getName() + " - " + p.getPrice() + " lei");
                    System.out.println(p.getDescription());
                    displayedProducts.add(p);
                }
            }

            System.out.println("\n---- Deserts ----");
            for(Product p : products){
                if(p instanceof Desert){
                    System.out.println(++index + ". " + p.getName() + " - " + p.getPrice() + " lei");
                    System.out.println(p.getDescription());
                    displayedProducts.add(p);
                }
            }

            System.out.println("\n---- Drinks ----");
            for(Product p : products){
                if(p instanceof Drink){
                    System.out.println(++index + ". " + p.getName() + " - " + p.getPrice() + " lei");
                    System.out.println(p.getDescription());
                    displayedProducts.add(p);
                }
            }
        }

        return displayedProducts;
    }


    @Override
    public boolean equals(Object o){
        if(o==this) return true;
        if(!(o instanceof Restaurant)) return false;
        Restaurant r=(Restaurant)o;
        return r.getName().equals(this.name);
    }

    @Override
    public int hashCode(){
        return this.name.hashCode();
    }
}
