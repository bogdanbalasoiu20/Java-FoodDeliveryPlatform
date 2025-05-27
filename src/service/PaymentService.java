package service;
import repository.PaymentRepository;
import model.Payment;

public class PaymentService {
    private final PaymentRepository paymentRepo = PaymentRepository.getInstance();
    private final AuditService auditService = AuditService.getInstance();

    public void processPayment(Payment payment){
        paymentRepo.save(payment);
        System.out.println("Payment processed");
        auditService.logAction("Payment PROCESSED successfully for order "+payment.getOrder().getId());
    }
}
