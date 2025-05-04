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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return name.equals(product.name) &&
                description.equals(product.description) &&
                price == product.price &&
                quantity == product.quantity;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + Double.hashCode(price);
        result = 31 * result + Integer.hashCode(quantity);
        return result;
    }

}
