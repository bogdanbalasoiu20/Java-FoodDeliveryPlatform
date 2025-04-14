package model;

import java.time.LocalDateTime;

public class Payment {
    private int id;
    private static int counter=0;
    private Order order;
    private LocalDateTime paymentDate;
    private double totalPrice;
    private PaymentMethod paymentMethod;

    public Payment(Order order,PaymentMethod paymentMethod){
        this.id=++counter;
        this.order=order;
        this.paymentDate=LocalDateTime.now();
        this.paymentMethod=paymentMethod;
        this.totalPrice=order.sumToPay();
    }

    public int getId() {
        return id;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public Order getOrder() {
        return order;
    }

    public void paymentDetails(){
        System.out.println("Payment to "+order.getRestaurant().getName());
        System.out.println("Total to pay: "+totalPrice);
        System.out.println("Order number: "+order.getId());
        String[] date_time=paymentDate.toString().split("T");
        String[] hour=date_time[1].split("\\.");
        System.out.println("payment date: "+date_time[0]+" "+hour[0]);
    }
}
