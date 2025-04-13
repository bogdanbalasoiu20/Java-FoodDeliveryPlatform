package model;
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
}
