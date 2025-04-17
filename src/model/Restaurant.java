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

    public void showDetails(){
        System.out.println(name);
        System.out.println(city);
        System.out.println(address);
        System.out.println(phoneNumber);
    }

    public void showMenu(){
        if(products.isEmpty()){
            System.out.println("No products available");
        }else{
            for(Product p:products){
                System.out.println(p.getName()+" - "+p.getPrice()+" lei");
                System.out.println(p.getDescription());
            }
        }
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
