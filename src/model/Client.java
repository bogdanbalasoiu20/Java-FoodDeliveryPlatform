package model;

public class Client extends User{

    public Client(String name, String email, String password, String phoneNumber, String country, String city, String address){
        super(name, email, password, phoneNumber, country, city, address);

    }

    @Override
    public String getUserType(){
        return "Client";
    }
}
