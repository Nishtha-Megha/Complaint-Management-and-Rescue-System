package services;
import java.sql.*;
import java.util.Scanner;
import java.io.*;

public class ExportService {
    public static void exportMenu(Connection con) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n==============================");
        System.out.println("⬇️ -- Download Complaint List -- ⬇️");
        System.out.println("==============================");
        System.out.println("1. 🏢 Export by Department");
        System.out.println("2. 📅 Export by Date Range");
        System.out.print("🔢 Select an option: ");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                exportByDepartment(con, scanner);
                break;
            case "2":
                exportByDateRange(con, scanner);
                break;
            default:
                System.out.println("❗ Invalid option.");
        }
    }

    private static void exportByDepartment(Connection con, Scanner scanner) {
        try {
            System.out.print("🏢 Enter department (e.g., Police, Fire, Medical): ");
            String department = scanner.nextLine();
            String sql = "SELECT * FROM complaints WHERE department = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, department);
            ResultSet rs = pst.executeQuery();
            String fileName = "ComplaintReport_" + department + ".txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write("Complaint Report: " + department + "\n");
            writer.write("-------------------------------\n");
            boolean found = false;
            while (rs.next()) {
                found = true;
                writer.write("ID: " + rs.getInt("id") + "\n");
                writer.write("Name: " + rs.getString("citizen_name") + "\n");
                writer.write("Issue: " + rs.getString("issue") + "\n");
                writer.write("Status: " + rs.getString("status") + "\n");
                writer.write("Date: " + rs.getString("date") + "\n");
                writer.write("-------------------------------\n");
            }
            if (!found) {
                writer.write("⚠️ No complaints found for department: " + department + "\n");
            }
            writer.close();
            rs.close();
            pst.close();
            System.out.println("✅ Exported to " + fileName);
        } catch (Exception e) {
            System.out.println("❌ Error exporting complaints: " + e.getMessage());
        }
    }

    private static void exportByDateRange(Connection con, Scanner scanner) {
        try {
            System.out.print("📅 Enter start date (YYYY-MM-DD): ");
            String startDate = scanner.nextLine();
            System.out.print("📅 Enter end date (YYYY-MM-DD): ");
            String endDate = scanner.nextLine();
            String sql = "SELECT * FROM complaints WHERE date BETWEEN ? AND ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, startDate);
            pst.setString(2, endDate);
            ResultSet rs = pst.executeQuery();
            String fileName = "ComplaintReport_" + startDate + "_to_" + endDate + ".txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write("Complaint Report: " + startDate + " to " + endDate + "\n");
            writer.write("-------------------------------\n");
            boolean found = false;
            while (rs.next()) {
                found = true;
                writer.write("ID: " + rs.getInt("id") + "\n");
                writer.write("Name: " + rs.getString("citizen_name") + "\n");
                writer.write("Issue: " + rs.getString("issue") + "\n");
                writer.write("Department: " + rs.getString("department") + "\n");
                writer.write("Status: " + rs.getString("status") + "\n");
                writer.write("Date: " + rs.getString("date") + "\n");
                writer.write("-------------------------------\n");
            }
            if (!found) {
                writer.write("⚠️ No complaints found for this date range.\n");
            }
            writer.close();
            rs.close();
            pst.close();
            System.out.println("✅ Exported to " + fileName);
        } catch (Exception e) {
            System.out.println("❌ Error exporting complaints: " + e.getMessage());
        }
    }
}
