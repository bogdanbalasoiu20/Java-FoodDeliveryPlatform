package repository;
import model.*;
import java.util.*;
import java.time.LocalDateTime;
import java.sql.*;

public class OrderRepository {

    public void save(Order order){
        if (order.getClient() == null || order.getRestaurant() == null) {
            System.out.println("Invalid order: client or restaurant is null");
            return;
        }

        if (order.getProducts() == null || order.getProducts().isEmpty()) {
            System.out.println("Invalid order: no products to save");
            return;
        }


        String sqlOrder = "INSERT INTO food_order(client_id,restaurant_id,total_price,order_date,status) VALUES (?,?,?,?,?)";
        String sqlOrderProduct = "INSERT INTO order_product(order_id, product_id, quantity) VALUES (?, ?, ?)";

        try(Connection conn = DBConnection.getConnection()){
            conn.setAutoCommit(false);

            try(PreparedStatement psOrder = conn.prepareStatement(sqlOrder,Statement.RETURN_GENERATED_KEYS)){
                psOrder.setInt(1,order.getClient().getId());
                psOrder.setInt(2,order.getRestaurant().getId());
                psOrder.setDouble(3,order.sumToPay());
                psOrder.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                psOrder.setString(5,order.getStatus());

                psOrder.executeUpdate();
                ResultSet rs = psOrder.getGeneratedKeys();
                if(rs.next()){
                    int orderId = rs.getInt(1);
                    order.setId(orderId);

                    try(PreparedStatement psItem = conn.prepareStatement(sqlOrderProduct)){
                        for(Product product: order.getProducts()){
                            psItem.setInt(1,orderId);
                            psItem.setInt(2,product.getId());
                            psItem.setInt(3,product.getQuantity());
                            psItem.addBatch();
                        }
                        psItem.executeBatch();
                    }
                }
                conn.commit();
                System.out.println("Order saved successfully with ID: " + order.getId());

            }catch(SQLException e){
                conn.rollback();
                throw e;
            }

        }catch(SQLException e){
            System.out.println("Error in saving order: " + e.getMessage());
        }

    }


    public List<Order> findAllByUser(User user){
        String sql = "SELECT * FROM food_order WHERE client_id=?";
        List <Order> orders = new ArrayList<>();

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1,user.getId());
            ResultSet rs = ps.executeQuery();

            ProductRepository productRepo = new ProductRepository();
            RestaurantRepository restaurantRepo = new RestaurantRepository();

            while(rs.next()){
                int orderId = rs.getInt("id");
                int restaurantId = rs.getInt("restaurant_id");
                String status = rs.getString("status");

                Restaurant restaurant = restaurantRepo.findById(restaurantId);
                List<Product> products = findProductsForOrder(conn,orderId);

                Order order = new Order(user,restaurant,products);
                order.setId(orderId);
                order.setStatus(status);

                orders.add(order);
            }

        }catch(SQLException e){
            System.out.println("Error in getting all food orders: " + e.getMessage());
        }

        return orders;
    }

    private List<Product> findProductsForOrder(Connection conn, int orderId) throws SQLException {
        String sql = "SELECT p.*, op.quantity FROM order_product op JOIN product p ON op.product_id = p.id WHERE op.order_id = ?";
        List<Product> products = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int orderedQuantity = rs.getInt("quantity");
                Product product = new Product(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        orderedQuantity,
                        rs.getString("product_type")
                );
                product.setId(rs.getInt("id"));
                products.add(product);
            }
        }

        return products;
    }

}
