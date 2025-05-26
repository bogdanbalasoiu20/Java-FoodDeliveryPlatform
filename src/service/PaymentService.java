package service;
import repository.PaymentRepository;
import model.Payment;

public class PaymentService {
    private PaymentRepository paymentRepo = PaymentRepository.getInstance();

    public void processPayment(Payment payment){
        paymentRepo.save(payment);
        System.out.println("Payment processed");
    }
}
