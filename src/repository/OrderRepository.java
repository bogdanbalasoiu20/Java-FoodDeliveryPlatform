package repository;
import model.*;
import java.util.*;
import java.time.LocalDateTime;
import java.sql.*;

public class OrderRepository extends GenericRepository<Order>{
    private static OrderRepository instance;

    private OrderRepository(){}

    public static synchronized OrderRepository getInstance(){
        if(instance==null){
            instance = new OrderRepository();
        }
        return instance;
    }

    @Override
    public void save(Order order){
        if (order.getClient() == null || order.getRestaurant() == null) {
            System.out.println("Invalid order: client or restaurant is null");
            return;
        }

        if (!(order.getClient() instanceof Client)) {
            System.out.println("Only clients can place orders.");
            return;
        }

        if (order.getProducts() == null || order.getProducts().isEmpty()) {
            System.out.println("Invalid order: no products to save");
            return;
        }


        String sqlOrder = "INSERT INTO food_order(client_id,restaurant_id,total_price,order_date,status) VALUES (?,?,?,?,?)";
        String sqlOrderProduct = "INSERT INTO order_product(order_id, product_id, quantity) VALUES (?, ?, ?)";

        try(Connection conn = DBConnection.getConnection()){
            conn.setAutoCommit(false);  //nu vreau commit automat pentru a fi sigur ca toate operatiile se executa cu succes sau nu se executa niciuna

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
                conn.commit();  //salvez in bd definitiv toate oparatiile executate pana acum
                System.out.println("Order saved successfully with ID: " + order.getId());

            }catch(SQLException e){
                conn.rollback();  //daca apare o eroare anulez toate modificarile fcaute in bd
                throw e;
            }

        }catch(SQLException e){
            System.out.println("Error in saving order: " + e.getMessage());
        }

    }


    public List<Order> findAllByUser(User user) {
        String sql = "SELECT * FROM food_order WHERE client_id=?";
        List<Order> orders = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();

            ProductRepository productRepo = ProductRepository.getInstance();
            RestaurantRepository restaurantRepo = RestaurantRepository.getInstance();

            while (rs.next()) {
                int orderId = rs.getInt("id");
                int restaurantId = rs.getInt("restaurant_id");
                String status = rs.getString("status");
                double totalPrice = rs.getDouble("total_price");
                LocalDateTime orderDate = rs.getTimestamp("order_date").toLocalDateTime();

                Restaurant restaurant = restaurantRepo.findById(restaurantId);
                List<Product> products = findProductsForOrder(orderId);

                Order order = new Order(orderId, user, restaurant, products, totalPrice, orderDate, status);
                orders.add(order);
            }

        } catch (SQLException e) {
            System.out.println("Error in getting all food orders: " + e.getMessage());
        }

        return orders;
    }



    private List<Product> findProductsForOrder(int orderId) {
        String sql = """
        SELECT p.*, op.quantity 
        FROM order_product op 
        JOIN product p ON op.product_id = p.id 
        WHERE op.order_id = ?
    """;

        List<Product> products = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int orderedQuantity = rs.getInt("quantity");
                Product product = new Product(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        orderedQuantity,
                        rs.getString("product_type"),
                        rs.getBoolean("available")
                );
                product.setId(rs.getInt("id"));
                products.add(product);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving order products: " + e.getMessage());
        }

        return products;
    }


}
