package service;
import model.User;
import model.Client;
import model.Admin;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private List<User> users=new ArrayList<>();  //a list with all users
    private User current_user; //the user who is logged in

    //app register method
    public void registerUser(String name, String email, String password, String phoneNumber, String country, String city, String address){

        if(checkEmail(email) && checkPassword(password)){
            //creates the new user
            Client client=new Client(name,email,password,phoneNumber,country,city,address);
            users.add(client);
            System.out.println("Account successfully created\n");
        }
    }

    public boolean checkEmail(String email){
        for(User user:users){
            if(user.getEmail().equalsIgnoreCase(email)){
                System.out.println("The email is already in use");
                return false;
            }
        }
        return true;
    }

    public boolean checkPassword(String password){
        if(!User.isValidPassword(password)){
            System.out.println("The password must contain: -at least 8 characters\n" +
                    "                                      -at least one uppercase\n" +
                    "                                      -at least one lowercase\n" +
                    "                                      -at least one special character");

            return false;
        }
        return true;
    }

    //login method
    public User login(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                if (user.getPassword().equals(password)) {
                    System.out.println("Hello, "+user.getName()+"! You have successfully logged in");
                    current_user=user;
                    return user;
                } else {
                    System.out.println("Incorrect password! Please try again!");
                    return null;
                }
            }
        }
        System.out.println("Invalid email address! Please try again!");
        return null;
    }

    public void logout(){
        if(current_user!=null){
            System.out.println("You have successfully logged out! See you next time, "+current_user.getName()+"!");
            current_user=null;
        }
        else{
            System.out.println("No user logged in");
        }

    }

    //see users list
    public List<User> getUsers(){
        return users;
    }

    public User getCurrentUser(){
        return current_user;
    }

    public boolean isLoggedIn(){
        return current_user!=null;
    }

}
