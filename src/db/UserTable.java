package db;

import java.sql.*;
import java.util.*;
import models.User;
import services.AuthService;

public class UserTable {
    public static Connection getuserconnection() throws Exception {
        String dburl = "jdbc:mysql://localhost:3306/a.i.r";
        String dbuser = "root";
        String dbpass = "";
        String driver = "com.mysql.cj.jdbc.Driver";

        Class.forName(driver);
        return DriverManager.getConnection(dburl, dbuser, dbpass);

    }
    public static void createUser() throws Exception{

        Connection con=getuserconnection();
        String sql = "create table if not exists user1" +
                "(id int auto_increment primary key ," +
                "Name varchar(10)," +
                "U_email varchar(30) unique," +
                "Password varchar(10))";
        Statement st = con.createStatement();
        st.executeUpdate(sql);
        System.out.println("Successful");
    }
    public static boolean insertUser(User u) throws Exception{
        Connection con=getuserconnection();
        if (isEmailExist(u.getEmail())) {
            return false;
        }
        String insert="insert into user1(Name,U_email,Password) values(?,?,?)";
        PreparedStatement pst=con.prepareStatement(insert);
        pst.setString(1,u.getName());
        pst.setString(2,u.getEmail().trim());
        pst.setString(3,u.getPassword());
        pst.executeUpdate();
        return true;
    }

    public static void getAllUsers() throws Exception{
        Connection con=getuserconnection();
        String sql="select * from user1";
        PreparedStatement pst=con.prepareStatement(sql);
        ResultSet rs= pst.executeQuery();
        if(rs.next()){
            System.out.println("Name : "+rs.getString(1));
            System.out.println("Email : "+rs.getString(2));
            System.out.println("Password : "+rs.getString(3));
        }

    }

    public static boolean isEmailExist(String email) throws Exception {
        Connection con = getuserconnection();
        String sql = "select * from user1 where U_email   = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, email.trim());
        ResultSet rs = pst.executeQuery();
        return rs.next();
    }


}
