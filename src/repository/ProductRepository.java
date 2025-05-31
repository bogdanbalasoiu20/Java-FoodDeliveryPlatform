package repository;

import model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProductRepository extends GenericRepository<Product>{
    private static ProductRepository instance;

    private ProductRepository(){}

    public static synchronized ProductRepository getInstance(){
        if(instance == null){
            instance = new ProductRepository();
        }
        return instance;
    }


    public void save(Product product,int restaurantId){
        String sql = "INSERT INTO product(name, description, price, quantity, product_type, restaurant_id) VALUES (?, ?, ?, ?, ?, ?)";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps=conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){  //Statement.RETURN_GENERATED_KEYS spune id-ul generat in bd
                                                                                            //PrepareStatement evita SQL injection si e folosit pentru a inlocui ? cu valori reale
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3,product.getPrice());
            ps.setInt(4,product.getQuantity());
            ps.setString(5,product.getProductType());
            ps.setInt(6,restaurantId);

            ps.executeUpdate();  //insereaza randul(folosit pentr INSERT,UPDATE,DELETE; in cazul meu INSERT)

            //ResultSet intaorce rezultatul interogarii sql
            ResultSet rs=ps.getGeneratedKeys();  //ofera acces la randul(sau randurile) de chei generate automat de bd
            if(rs.next()){   //verific daca am macar un rand generat
                int id=rs.getInt(1);   //extrag valoarea din coloana 1(id-ul)
                product.setId(id);   //atribui id-ul obiectului meu
            }

        }catch(SQLException e){
            System.out.println("Product Insertion Failed: " + e.getMessage());
        }


    }

    public void disableProduct(String productName, int restaurantId){
        String sql = "UPDATE product SET available = false WHERE name = ? and restaurant_id = ?";


        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1,productName);
            ps.setInt(2,restaurantId);
            int rows = ps.executeUpdate();  //intoarce cate randuri au fost afectate
            if(rows>0){
                System.out.println("Product marked as unavailable!");
            }else{
                System.out.println("Product not found in database!");
            }

        }catch(SQLException e){
            System.out.println("Product Deletion Failed: " + e.getMessage());
        }
    }


    public boolean existsByNameAndRestaurantId(String name, int restaurantId){
        String sql = "SELECT * FROM product WHERE name = ? and restaurant_id = ?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1,name);
            ps.setInt(2,restaurantId);
            ResultSet rs=ps.executeQuery();  //folosit pentru READ
            return rs.next();
        }catch(SQLException e){
            System.out.println("Error checking product existance: " + e.getMessage());
            return false;
        }
    }


    public List<Product> findAllByRestaurantId(int restaurantId){
        String sql = "SELECT * FROM product WHERE restaurant_id=?";
        List <Product> products = new ArrayList<>();

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1,restaurantId);

            ResultSet rs=ps.executeQuery(); //rezultatul interogarii(cursor)
            while(rs.next()){   //parcurg fiecare linie din cursor
                Product p = new Product(  //creez obiectul pe baza datelor din fiecare coloana a liniei
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("product_type"),
                        rs.getBoolean("available")
                );
                p.setId(rs.getInt("id"));  //setez id-ul generat in bd
                products.add(p);
            }

        }catch(SQLException e){
            System.out.println("Error checking product existance: " + e.getMessage());
        }
        return products;
    }

    public List<Product> findUnavailableByRestaurantId(int restaurantId) {
        String sql = "SELECT * FROM product WHERE restaurant_id=? AND available=false";
        List<Product> products = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, restaurantId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product p = new Product(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("product_type"),
                        rs.getBoolean("available")
                );
                p.setId(rs.getInt("id"));
                products.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error checking product availability: " + e.getMessage());
        }

        return products;
    }


    public void markAsAvailable(String productName, int restaurantId) {
        String sql = "UPDATE product SET available = true WHERE name = ? AND restaurant_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, productName);
            ps.setInt(2, restaurantId);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Product marked as available!");
            } else {
                System.out.println("Product not found in database!");
            }

        } catch (SQLException e) {
            System.out.println("Error marking product as available: " + e.getMessage());
        }
    }


    public String findBestSellingProductWithRestaurant() {
        String sql = """
        SELECT p.name AS product_name,
               p.description,
               p.price,
               p.product_type,
               r.name AS restaurant_name,
               r.city,
               r.address,
               SUM(op.quantity) AS total_sold
        FROM order_product op
        JOIN product p ON op.product_id = p.id
        JOIN restaurant r ON p.restaurant_id = r.id
        GROUP BY p.id, r.id
        ORDER BY total_sold DESC
        LIMIT 1
    """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String productName = rs.getString("product_name");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                String type = rs.getString("product_type");
                String restaurantName = rs.getString("restaurant_name");
                String city = rs.getString("city");
                String address = rs.getString("address");
                int totalSold = rs.getInt("total_sold");

                return "\nBest selling product: " + productName +
                        " - " + price + " lei (" + type + ")\n" +
                        "Restaurant:           " + restaurantName + " (" + city + ", " + address + ")\n" +
                        "Units sold:           " + totalSold;
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving best selling product: " + e.getMessage());
        }

        return "\nNo sales data available.";
    }





}
