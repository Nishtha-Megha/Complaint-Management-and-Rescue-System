package db;

import models.Complaint;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComplainTable {
    public static Connection getConnection() throws Exception {
        String dburl = "jdbc:mysql://localhost:3306/a.i.r";
        String dbuser = "root";
        String dbpass = "";
        String driver = "com.mysql.cj.jdbc.Driver";

        Class.forName(driver);
        return DriverManager.getConnection(dburl, dbuser, dbpass);

    }
    public static void createTable() throws Exception {
        Connection con= getConnection();
        String sql = "create table if not exists Complaint(C_id int primary key,U_email varchar(255),Phone_no varchar(10),C_type varchar(10)," +
                "complaintText varchar(30),status varchar(20),dateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
        Statement st = con.createStatement();
        st.executeUpdate(sql);
    }

    public static void addComplaint(Complaint c) throws Exception{
        Connection con= getConnection();

        String insert="insert into Complaint (C_id,U_email,Phone_no,C_type,complaintText,status) values(?,?,?,?,?,?)";
        PreparedStatement pst=con.prepareStatement(insert);

        pst.setString(1,c.getName());
        pst.setString(2,c.getUserEmail());
        pst.setString(3,c.getPhoneno());
        String s=c.getCtype();
        if(s.equalsIgnoreCase("police")){
            pst.setString(4,s.toUpperCase());
        }
        else if(s.equalsIgnoreCase("amc")){
            pst.setString(4,s.toUpperCase());
        }
        pst.setString(5,c.getComplaintText());
        pst.setString(6,"Pending..");
        pst.executeUpdate();
    }

    public static List<Complaint> getInfo(String email) throws Exception{
        Connection con= getConnection();
        email = email.trim();  // Clean input

        String fetch="Select * from Complaint where U_email=?";
        PreparedStatement pst=con.prepareStatement(fetch);
        pst.setString(1,email);
        ResultSet rs=pst.executeQuery();

        List<Complaint> list=new ArrayList<>();
        while(rs.next()){
            Complaint c=new Complaint(rs.getString("Name"),
                    rs.getString("U_email"),
                    rs.getString("Phone_no"),
                    rs.getString("C_type"),
                    rs.getString("complaintText"));
            list.add(c);
        }
        return list;
    }

}

