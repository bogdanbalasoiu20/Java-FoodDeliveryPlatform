package service;
import model.User;
import model.Client;
import model.Admin;
import java.util.ArrayList;
import java.util.List;
import repository.UserRepository;

public class UserService {
    private final UserRepository userRepo = new UserRepository();
    private User current_user; //the user who is logged in

    //app register method
    public void registerUser(String name, String email, String password, String phoneNumber, String country, String city, String address){

        if(checkEmail(email) && checkPassword(password)){
            //creates the new user
            Client client=new Client(name,email,password,phoneNumber,country,city,address);
            userRepo.saveClient(client);
            System.out.println("Account successfully created\n");
        }
    }

    public void registerAdmin(String name, String email, String password, String phoneNumber, String country, String city, String address){
        if(checkEmail(email) && checkPassword(password)){
            Admin admin=new Admin(name, email,password,phoneNumber,country,city,address);
            userRepo.saveAdmin(admin);
            System.out.println("Admin successfully created\n");
        }
    }

    public boolean checkEmail(String email){
        if(userRepo.existsByEmail(email)){
            System.out.println("The email is already in use\n");
            return false;
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
        User user = userRepo.findByEmailAndPassword(email,password);
        if(user!=null){
            System.out.println("Hello, " + user.getName() + "! You have successfully logged in");
            current_user=user;
        }else{
            System.out.println("Invalid email or password");
        }
        return current_user;
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


    public User getCurrentUser(){
        return current_user;
    }

    public boolean isLoggedIn(){
        return current_user!=null;
    }

    public User getAdmin(String email, String password){
        User user = userRepo.findByEmailAndPassword(email,password);
        if(user instanceof Admin){
            return user;
        }
        return null;
    }

}
