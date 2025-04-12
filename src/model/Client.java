package model;

public class Client extends User{
    private String userType;

    public Client(String name, String email, String password, String phoneNumber, String country, String city, String address){
        super(name, email, password, phoneNumber, country, city, address);
        this.userType="client";
    }
}
