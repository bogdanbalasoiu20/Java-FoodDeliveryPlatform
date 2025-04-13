package model;

public class Product {
    private String name;
    private String description;
    private double price;
    private int quantity;
    private int id;
    private static int counter=0;

    public Product(String name, String description, double price, int quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.id=++counter;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String toString(){
        return name+"-"+price+" lei (x"+quantity+")";
    }
}
