package repository;
import java.sql.*;
import java.util.*;
import model.ReviewRestaurant;
import model.Admin;
import model.Client;
import model.Restaurant;
import model.User;

public class ReviewRestaurantRepository extends GenericRepository<ReviewRestaurant>{
    private static ReviewRestaurantRepository instance;

    private ReviewRestaurantRepository() {}

    public static synchronized ReviewRestaurantRepository getInstance(){
        if(instance==null){
            instance=new ReviewRestaurantRepository();
        }
        return instance;
    }

    @Override
    public void save(ReviewRestaurant review){
        String sql = "INSERT INTO review_restaurant(client_id,admin_id,restaurant_id,review_text,rating_user) VALUES (?,?,?,?,?)";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){

            if(review.getUser() instanceof Client){
                ps.setInt(1,review.getUser().getId());
                ps.setNull(2,java.sql.Types.INTEGER);
            }else if(review.getUser() instanceof Admin){
                ps.setNull(1,java.sql.Types.INTEGER);
                ps.setInt(2,review.getUser().getId());
            }else{
                throw new IllegalArgumentException("User must be Client or Admin");
            }

            ps.setInt(3,review.getRestaurant().getId());
            ps.setString(4,review.getReviewText());
            ps.setInt(5,review.getRatingUser());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                int id = rs.getInt(1);
                review.setId(id);
            }

        }catch(SQLException e){
            System.out.println("Error saving review: "+e.getMessage());
        }
    }


    public List<ReviewRestaurant> findAllByRestaurant(Restaurant restaurant){
        String sql="SELECT * FROM review_restaurant WHERE restaurant_id=?";
        List <ReviewRestaurant> reviews =new ArrayList<>();

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1,restaurant.getId());

            ResultSet rs = ps.executeQuery();
            UserRepository userRepo = UserRepository.getInstance();
            while(rs.next()){
                int clientId = rs.getInt("client_id");
                boolean isClientNull = rs.wasNull();

                int adminId = rs.getInt("admin_id");
                boolean isAdminNull = rs.wasNull();

                User user = null;
                if (!isClientNull) {
                    user = userRepo.findClientById(clientId);
                } else if (!isAdminNull) {
                    user = userRepo.findAdminById(adminId);
                }

                if (user != null) {
                    ReviewRestaurant review = new ReviewRestaurant(
                            user,
                            restaurant,
                            rs.getString("review_text"),
                            rs.getInt("rating_user")
                    );
                    reviews.add(review);
                }
            }


        }catch(SQLException e){
            System.out.println("Error loading review: "+e.getMessage());
        }
        return reviews;
    }
}
