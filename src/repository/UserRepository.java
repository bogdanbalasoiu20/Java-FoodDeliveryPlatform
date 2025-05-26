package repository;
import java.sql.*;
import java.util.*;
import model.User;
import model.Admin;
import model.Client;


public class UserRepository extends GenericRepository<User>{
    private static UserRepository instance;

    private UserRepository(){}

    public static synchronized UserRepository getInstance(){
        if(instance==null){
            instance=new UserRepository();
        }
        return instance;
    }

    public void saveClient(Client client){
        String sql="INSERT INTO client(name,email,password,phone_number,country,city,address) VALUES (?,?,?,?,?,?,?)";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps=conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);){

            ps.setString(1,client.getName());
            ps.setString(2,client.getEmail());
            ps.setString(3,client.getPassword());
            ps.setString(4,client.getPhoneNumber());
            ps.setString(5,client.getCountry());
            ps.setString(6,client.getCity());
            ps.setString(7,client.getAddress());

            ps.executeUpdate();

            ResultSet rs=ps.getGeneratedKeys();
            if(rs.next()){
                int id=rs.getInt(1);
                client.setId(id);
            }

        }catch(SQLException e){
            System.out.println("Error in saving client: "+e.getMessage());
        }
    }


    public void saveAdmin(Admin admin){
        String sql="INSERT INTO admin(name,email,password,phone_number,country,city,address) VALUES (?,?,?,?,?,?,?)";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps=conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);){

            ps.setString(1,admin.getName());
            ps.setString(2,admin.getEmail());
            ps.setString(3,admin.getPassword());
            ps.setString(4,admin.getPhoneNumber());
            ps.setString(5,admin.getCountry());
            ps.setString(6,admin.getCity());
            ps.setString(7,admin.getAddress());

            ps.executeUpdate();

            ResultSet rs=ps.getGeneratedKeys();
            if(rs.next()){
                int id=rs.getInt(1);
                admin.setId(id);
            }

        }catch(SQLException e){
            System.out.println("Error in saving admin: "+e.getMessage());
        }
    }


    public boolean existsByEmail(String email){
        String sql = "SELECT 1 FROM admin WHERE email=?" +
                "     UNION" +
                "     SELECT 1 FROM client WHERE email=? ";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1,email);
            ps.setString(2,email);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        }catch(SQLException e){
            System.out.println("Error checking email existance: "+e.getMessage());
            return false;
        }
    }


    public User findByEmailAndPassword(String email, String password) {
        String sqlAdmin = "SELECT * FROM admin WHERE email = ? AND password = ?";
        String sqlClient = "SELECT * FROM client WHERE email = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection()) {

            try (PreparedStatement ps = conn.prepareStatement(sqlAdmin)) {
                ps.setString(1, email);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    Admin admin = new Admin(
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("phone_number"),
                            rs.getString("country"),
                            rs.getString("city"),
                            rs.getString("address")
                    );
                    admin.setId(rs.getInt("id"));
                    return admin;
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(sqlClient)) {
                ps.setString(1, email);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    Client client = new Client(
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("phone_number"),
                            rs.getString("country"),
                            rs.getString("city"),
                            rs.getString("address")
                    );
                    client.setId(rs.getInt("id"));
                    return client;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error finding user: " + e.getMessage());
        }

        return null;
    }

    public int getIdByEmail(String email){
        String sql="SELECT id FROM admin WHERE email=?" +
                "UNION" +
                "SELECT id FROM client WHERE email=? ";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1,email);
            ps.setString(2,email);

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int id=rs.getInt(1);
                return id;
            }

        }catch(SQLException e){
            System.out.println("Error retriving user id: "+e.getMessage());
        }
        return -1;
    }


    public Client findClientById(int id){
        String sql="SELECT * FROM client WHERE id=?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Client client = new Client(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone_number"),
                        rs.getString("country"),
                        rs.getString("city"),
                        rs.getString("address")
                );
                client.setId(id);
                return client;
            }

        }catch(SQLException e){
            System.out.println("Error retriving user id: "+e.getMessage());
        }
        return null;
    }


    public Admin findAdminById(int id) {
        String sql = "SELECT * FROM admin WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Admin admin = new Admin(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone_number"),
                        rs.getString("country"),
                        rs.getString("city"),
                        rs.getString("address")
                );
                admin.setId(id);
                return admin;
            }

        } catch (SQLException e) {
            System.out.println("Error finding admin by ID: " + e.getMessage());
        }

        return null;
    }



}
