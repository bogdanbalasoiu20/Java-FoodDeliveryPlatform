package repository;

import model.Restaurant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantRepository extends GenericRepository<Restaurant>{
    private static RestaurantRepository instance;

    private RestaurantRepository(){}

    public static synchronized RestaurantRepository getInstance(){
        if(instance==null){
            instance = new RestaurantRepository();
        }
        return instance;
    }

    public void save(Restaurant restaurant){
        String sql = "INSERT INTO restaurant(name, city, address, phone_number) VALUES (?, ?, ?, ?)";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){

            ps.setString(1,restaurant.getName());
            ps.setString(2,restaurant.getCity());
            ps.setString(3,restaurant.getAddress());
            ps.setString(4,restaurant.getPhoneNumber());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                int generatedId = rs.getInt(1);
                restaurant.setId(generatedId);
            }
        }catch(SQLException e){
            System.out.println("Error inserting restaurant: "+e.getMessage());
        }
    }

    public void delete(int restaurantId){
        String sql = "DELETE FROM restaurant WHERE id = ?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps =conn.prepareStatement(sql)){

            ps.setInt(1,restaurantId);

            int rows=ps.executeUpdate();
            if(rows>0){
                System.out.println("Restaurant "+restaurantId+" has been deleted successfully");
            }else{
                System.out.println("Restaurant not found");
            }

        }catch(SQLException e){
            System.out.println("Error deleting restaurant: "+e.getMessage());
        }
    }

    public List<Restaurant> findAll(){
        List<Restaurant> restaurants = new ArrayList<>();
        String sql="SELECT * FROM restaurant";

        try(Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){

            while(rs.next()){
                Restaurant r = new Restaurant(rs.getInt("id"),rs.getString("name"),rs.getString("city"),rs.getString("address"),rs.getString("phone_number"));
                restaurants.add(r);
            }

        }catch(SQLException e){
            System.out.println("Error reading restaurant: "+e.getMessage());
        }

        return restaurants;
    }

    public boolean existsByName(String name){
        String sql="SELECT 1 FROM restaurant WHERE name = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1,name);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        }catch(SQLException e){
            System.out.println("Error checking restaurant name: "+e.getMessage());
            return false;
        }
    }

    public int getIdByName(String name){
        String sql = "SELECT id FROM restaurant WHERE name = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1,name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("id");
            }

        }catch(SQLException e){
            System.out.println("Error retrieving restaurant id: "+e.getMessage());
        }
        return -1;
    }


    public Restaurant findById(int restaurantId){
        String sql ="SELECT * FROM restaurant WHERE id=?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1,restaurantId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Restaurant restaurant = new Restaurant(
                    restaurantId,
                    rs.getString("name"),
                    rs.getString("city"),
                    rs.getString("address"),
                    rs.getString("phone_number")
                );
                return restaurant;
            }
        }catch(SQLException e){
            System.out.println("Error  retrieving restaurant id: "+e.getMessage());
        }
        return null;
    }
}
