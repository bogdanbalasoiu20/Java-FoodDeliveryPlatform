package model;

public abstract class User {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String country;
    private String city;
    private String address;

    public User(String name, String email, String password, String phoneNumber, String country, String city, String address) {
        this.name=name;
        this.email=email;
        this.password=password;
        this.phoneNumber=phoneNumber;
        this.country=country;
        this.city=city;
        this.address=address;
    }

    public User(){}

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
