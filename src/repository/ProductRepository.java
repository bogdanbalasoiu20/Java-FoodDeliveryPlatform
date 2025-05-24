package repository;

import model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProductRepository {
    public void save(Product product,int restaurantId){
        String sql = "INSERT INTO product(name, description, price, quantity, product_type, restaurant_id) VALUES (?, ?, ?, ?, ?, ?)";

        try(Connection conn = DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement(sql)){

            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3,product.getPrice());
            ps.setInt(4,product.getQuantity());
            ps.setString(5,product.getProductType());
            ps.setInt(6,restaurantId);

            ps.executeUpdate();

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
            int rows = ps.executeUpdate();
            if(rows>0){
                System.out.println("Product removed successfully from database!");
            }else{
                System.out.println("Product not found in database!");
            }

        }catch(SQLException e){
            System.out.println("Product Deletion Failed: " + e.getMessage());
        }
    }

}
