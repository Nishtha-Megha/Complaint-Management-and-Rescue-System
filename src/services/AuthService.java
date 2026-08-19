package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import main.*;
import db.UserTable;
import models.User;


public class AuthService {

    public static ArrayList<User> a=new ArrayList<>();
    public static Scanner sc=new Scanner(System.in);
    public boolean register(User user) {
        try {
            return UserTable.insertUser(user);
        } catch (Exception e) {
            System.out.println("⚠️ Error during registration: " + e.getMessage());
            return false;
        }
    }
    public User login(String email,String password) throws Exception{
        try{

            Connection con=UserTable.getuserconnection();
            String sql="select * from user1 where U_email=? and Password=?";
            PreparedStatement pst= con.prepareStatement(sql);
            pst.setString(1,email);
            pst.setString(2,password);
            ResultSet rs=pst.executeQuery();
            if(rs.next()){
                String name=rs.getString("Name");
                return new User(name,email,password);
            }
            else{
                return null;
            }
        } catch (Exception e) {
            System.out.println("⚠️Error during login ");
            return null;
        }
    }
    public static boolean logout(String email) throws Exception {
        Connection con = UserTable.getuserconnection();
        String sql = "DELETE FROM user1 WHERE U_email=?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, email);

        int r = pst.executeUpdate();
        if(r>0)
        return true;
        else
            return false;
    }
    public static void update() throws Exception{
        Connection con=UserTable.getuserconnection();
        int choice;
        int r;
        String cemail=Main.currentUser.getEmail();
        try{

            do{

                System.out.println(" 🛠️ What do you want to update ??");
                System.out.println("1.👤 Name");
                System.out.println("2.📧 Email");
                System.out.println("3.🔑 PassWord");
                System.out.println("4.⬅️ Exit");
                System.out.println("Enter your choice : ");
                choice=sc.nextInt();
                sc.nextLine();
                switch(choice){
                    case 1:
                        System.out.print("Enter your updated name : ");
                        String name=sc.nextLine();
                        String n="update user1 set Name=? where U_email=? ";
                        PreparedStatement pst=con.prepareStatement(n);
                        pst.setString(1,name);
                        pst.setString(2,cemail);
                        r=pst.executeUpdate();
                        if(r>0){
                            System.out.println("✅ Name updated successfully");
                            Main.currentUser.setName(n);
                        }
                        break;
                    case 2:
                        System.out.print("📧 Enter your new email: ");
                        String e = sc.nextLine();
                        pst = con.prepareStatement("update user1 SET U_email=? where U_email=?");
                        pst.setString(1, e);
                        pst.setString(2, cemail);
                        r = pst.executeUpdate();
                        if (r > 0) {
                            System.out.println("✅ Email updated successfully.");
                            Main.currentUser.setEmail(e);
                            cemail = e;
                        } else {
                            System.out.println("❌ Failed to update email.");
                        }
                        break;

                    case 3:
                        System.out.print("🔒 Enter your new password: ");
                        String p = sc.nextLine();
                        pst = con.prepareStatement("update user1 SET Password=? where U_email=?");
                        pst.setString(1, p);
                        pst.setString(2, cemail);
                        r = pst.executeUpdate();
                        if(r > 0) {
                            System.out.println( "✅ Password updated successfully.");
                            Main.currentUser.setPassword(p);
                        }
                        else{
                            System.out.println("❌ Failed to update password.");
                        }
                        break;

                    case 4:
                        System.out.println("⬅️ Returning to previous menu...");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("⚠️ Invalid choice. Please try again.");
                }
            }while (choice!=4);
        }catch (Exception e){
            System.out.println("Enter valid value");
        }
    }
}
