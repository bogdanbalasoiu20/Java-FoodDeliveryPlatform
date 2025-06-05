package repository;

import model.Payment;

import java.sql.*;

public class PaymentRepository extends GenericRepository<Payment>{
    private static PaymentRepository instance;

    private PaymentRepository(){}

    public static synchronized PaymentRepository getInstance(){  //synchronized permite acceseul pe rand doar unui fir de executie(nu e necesar in aplicatia mea pt ca folosesc doar un thread).De exemplu, daca doua threaduri acceseaza metoda in acelasi timp vor vedea ca instance==null si ambele vor creea o instanta, ceea ce incalca principiul Singleton
        if(instance==null){
            instance = new PaymentRepository();
        }
        return instance;
    }

    @Override
    public void save(Payment payment) {
        String sql = "INSERT INTO payment(order_id, payment_date, total_price, payment_method) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, payment.getOrder().getId());
            ps.setTimestamp(2, Timestamp.valueOf(payment.getPaymentDate()));
            ps.setDouble(3, payment.getTotalPrice());
            ps.setString(4, payment.getPaymentMethod().name());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                payment.setId(id);
            }

            System.out.println("Payment saved successfully.");

        } catch (SQLException e) {
            System.out.println("Error saving payment: " + e.getMessage());
        }
    }
}
