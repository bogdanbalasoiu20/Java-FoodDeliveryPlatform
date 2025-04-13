package model;

import java.sql.SQLOutput;

public abstract class User {
    private int id;
    private static int counter=0;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String country;
    private String city;
    private String address;

    public User(String name, String email, String password, String phoneNumber, String country, String city, String address) {
        this.id=++counter;
        this.name=name;
        this.email=email;
        this.password=password;
        this.phoneNumber=phoneNumber;
        this.country=country;
        this.city=city;
        this.address=address;
    }

    public User(){}

    public int getId(){ return id;}

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

    abstract public String getUserType();

    public void showProfile(){
        System.out.println("\n********* User Profile *********");
        System.out.println("Name: "+name);
        System.out.println("Email: "+email);
        System.out.println("Phone Number: "+phoneNumber);
        System.out.println("Country: "+country);
        System.out.println("City: "+city);
        System.out.println("Address: "+address);
        System.out.println("********************************\n");
    }

    //check if a password is valid. it is used in UserService
    public static boolean isValidPassword(String password){
        if(password.length()<8)
            return false;

        boolean hasUppercase=false;
        boolean hasDigit=false;
        boolean hasSpecialCharacter=false;
        boolean hasLowercase=false;

        for(char c:password.toCharArray()){
            if(Character.isUpperCase(c))
                hasUppercase=true;
            else if(Character.isDigit(c))
                hasDigit=true;
            else if(Character.isLowerCase(c))
                hasLowercase=true;
            else hasSpecialCharacter=true;
        }
        return hasUppercase&&hasDigit&&hasLowercase&&hasSpecialCharacter;
    }

    public String toString(){
        return name+"("+email+","+password+")";
    }

    @Override
    public boolean equals(Object o){
        if(this==o) return true;
        if(!(o instanceof User)) return false;
        User u=(User) o;
        return email.equalsIgnoreCase(u.email);
    }

    @Override
    public int hashCode(){
        return email.toLowerCase().hashCode();
    }

}
