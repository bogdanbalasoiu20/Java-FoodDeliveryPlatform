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

    public void delete(String productName, int restaurantId){
        String sql = "DELETE FROM product WHERE name = ? and restaurant_id = ?";


        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1,productName);
            ps.setInt(2,restaurantId);
            int rows = ps.executeUpdate();  //intoarce cate randuri au fost afectate
            if(rows>0){
                System.out.println("Product removed successfully from database!");
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
                        rs.getString("product_type")
                );
                p.setId(rs.getInt("id"));  //setez id-ul generat in bd
                products.add(p);
            }

        }catch(SQLException e){
            System.out.println("Error checking product existance: " + e.getMessage());
        }
        return products;
    }

}
