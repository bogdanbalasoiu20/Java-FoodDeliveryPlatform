package model;

public class Admin extends User{
    private String userType;

    public Admin(String name, String email, String password, String phoneNumber, String country, String city, String address){
        super(name, email, password, phoneNumber, country, city, address);
    }
}
