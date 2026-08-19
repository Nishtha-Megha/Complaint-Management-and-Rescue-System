package services;

import java.sql.*;
import java.util.Scanner;

public class ComplaintService {
    public static void menu(Connection con) {
        Scanner scanner = new Scanner(System.in);
        boolean back = false;
        while (!back) {
            System.out.println("\n==============================");
            System.out.println("📣 -- Complaint Management -- 📣");
            System.out.println("==============================");
            System.out.println("1. 👀 View All Complaints");
            System.out.println("2. 🏢 View by Department");
            System.out.println("3. 📋 View by Status");
            System.out.println("4. 🔎 Search by Date or ID");
            System.out.println("5. 🔙 Back to Dashboard");
            System.out.print("🔢 Select an option: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    viewAllComplaints(con);
                    break;
                case "2":
                    viewByDepartment(con, scanner);
                    break;
                case "3":
                    viewByStatus(con, scanner);
                    break;
                case "4":
                    searchByDateOrId(con, scanner);
                    break;
                case "5":
                    back = true;
                    break;
                default:
                    System.out.println("❗ Invalid option. Please try again.");
            }
        }
        // scanner.close(); // Do not close shared scanner
    }

    private static void viewAllComplaints(Connection con) {
        try {
            System.out.println("\n👀 All Complaints:");
            String sql = "SELECT * FROM complaints";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            boolean found = false;
            while (rs.next()) {
                found = true;
                int id = rs.getInt("id");
                String citizenName = rs.getString("citizen_name");
                String issue = rs.getString("issue");
                String department = rs.getString("department");
                String status = rs.getString("status");
                String date = rs.getString("date");
                models.Complaint1 complaint = new models.Complaint1(id, citizenName, issue, department, status, date);
                complaint.toString();
            }
            if (!found) {
                System.out.println("⚠️ No complaints found.");
            }
            rs.close();
            pst.close();
        } catch (Exception e) {
            System.out.println("❌ Error viewing complaints: " + e.getMessage());
        }
    }

    private static void viewByDepartment(Connection con, Scanner scanner) {
        try {
            System.out.print("🏢 Enter department (e.g., Police, Fire, Medical): ");
            String department = scanner.nextLine();
            String sql = "SELECT * FROM complaints WHERE department = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, department);
            ResultSet rs = pst.executeQuery();
            boolean found = false;
            while (rs.next()) {
                found = true;
                int id = rs.getInt("id");
                String citizenName = rs.getString("citizen_name");
                String issue = rs.getString("issue");
                String status = rs.getString("status");
                String date = rs.getString("date");
                models.Complaint1 complaint = new models.Complaint1(id, citizenName, issue, department, status, date);
                complaint.toString();
            }
            if (!found) {
                System.out.println("⚠️ No complaints found for department: " + department);
            }
            rs.close();
            pst.close();
        } catch (Exception e) {
            System.out.println("❌ Error viewing complaints by department: " + e.getMessage());
        }
    }

    private static void viewByStatus(Connection con, Scanner scanner) {
        try {
            System.out.print("📋 Enter status (Pending, In Progress, Resolved): ");
            String status = scanner.nextLine();
            String sql = "SELECT * FROM complaints WHERE status = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, status);
            ResultSet rs = pst.executeQuery();
            boolean found = false;
            while (rs.next()) {
                found = true;
                int id = rs.getInt("id");
                String citizenName = rs.getString("citizen_name");
                String issue = rs.getString("issue");
                String department = rs.getString("department");
                String date = rs.getString("date");
                models.Complaint1 complaint = new models.Complaint1(id, citizenName, issue, department, status, date);
                complaint.toString();
            }
            if (!found) {
                System.out.println("⚠️ No complaints found with status: " + status);
            }
            rs.close();
            pst.close();
        } catch (Exception e) {
            System.out.println("❌ Error viewing complaints by status: " + e.getMessage());
        }
    }

    private static void searchByDateOrId(Connection con, Scanner scanner) {
        try {
            System.out.print("🔎 Search by (1) Date or (2) ID? Enter 1 or 2: ");
            String option = scanner.nextLine();
            if (option.equals("1")) {
                System.out.print("📅 Enter date (YYYY-MM-DD): ");
                String date = scanner.nextLine();
                String sql = "SELECT * FROM complaints WHERE date = ?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, date);
                ResultSet rs = pst.executeQuery();
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    int id = rs.getInt("id");
                    String citizenName = rs.getString("citizen_name");
                    String issue = rs.getString("issue");
                    String department = rs.getString("department");
                    String status = rs.getString("status");
                    models.Complaint1 complaint = new models.Complaint1(id, citizenName, issue, department, status, date);
                    complaint.toString();
                }
                if (!found) {
                    System.out.println("⚠️ No complaints found for date: " + date);
                }
                rs.close();
                pst.close();
            } else if (option.equals("2")) {
                System.out.print("🔢 Enter complaint ID: ");
                int id = Integer.parseInt(scanner.nextLine());
                String sql = "SELECT * FROM complaints WHERE id = ?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setInt(1, id);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    String citizenName = rs.getString("citizen_name");
                    String issue = rs.getString("issue");
                    String department = rs.getString("department");
                    String status = rs.getString("status");
                    String date = rs.getString("date");
                    models.Complaint1 complaint = new models.Complaint1(id, citizenName, issue, department, status, date);
                    complaint.toString();
                } else {
                    System.out.println("⚠️ No complaint found with ID: " + id);
                }
                rs.close();
                pst.close();
            } else {
                System.out.println("❗ Invalid option.");
            }
        } catch (Exception e) {
            System.out.println("❌ Error searching complaints: " + e.getMessage());
        }
    }
}