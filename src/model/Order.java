package model;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private static int counter=0;
    private User client;
    private Restaurant restaurant;
    private List<Product> products;
    private double totalPrice;
    private LocalDateTime orderDate;
    private String status;

    public Order(User client,Restaurant restaurant, List<Product> products){
        this.id=++counter;
        this.client = client;
        this.restaurant = restaurant;
        this.products = products;
        this.orderDate = LocalDateTime.now();
        this.totalPrice=sumToPay();
        this.status="Pending";
    }

    public Order(int id, User client, Restaurant restaurant, List<Product> products, double totalPrice, LocalDateTime orderDate, String status) {
        this.id = id;
        this.client = client;
        this.restaurant = restaurant;
        this.products = products;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.status = status;
    }

    public double sumToPay(){
        double total=0;
        for(Product p:products){
            total+=p.getPrice()*p.getQuantity();
        }
        return total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id=id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status){
        this.status=status;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public User getClient(){
        return this.client;
    }

    public List<Product> getProducts(){
        return this.products;
    }


    public void orderDetails(){
        System.out.println("Order #"+id+" - "+restaurant.getName()+" - "+status);
        for(Product p:products){
            System.out.println(p);
        }
        System.out.println("Total Price: "+totalPrice+"\n");
    }
}
