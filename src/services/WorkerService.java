package services;

import java.sql.*;
import java.util.Scanner;

public class WorkerService {
    public static void menu(Connection con) {
        Scanner scanner = new Scanner(System.in);
        boolean back = false;
        while (!back) {
            System.out.println("\n==============================");
            System.out.println("💼 -- Worker Management -- 💼");
            System.out.println("==============================");
            System.out.println("1. ➕ Add Worker");
            System.out.println("2. 👀 View All Workers");
            System.out.println("3. ✏️ Update Worker");
            System.out.println("4. ❌ Delete Worker");
            System.out.println("5. 🔙 Back to Dashboard");
            System.out.print("🔢 Select an option: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    addWorker(con, scanner);
                    break;
                case "2":
                    viewAllWorkers(con);
                    break;
                case "3":
                    updateWorker(con, scanner);
                    break;
                case "4":
                    deleteWorker(con, scanner);
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

    private static boolean isAllDigits(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static void addWorker(Connection con, Scanner scanner) {
        try {
            System.out.println("\n➕ Add Worker");
            System.out.print("👤 Enter name: ");
            String name = scanner.nextLine();
            System.out.print("🏢 Enter department: ");
            String department = scanner.nextLine();
            System.out.print("📋 Enter status: ");
            String status = scanner.nextLine();
            String contact;
            while (true) {
                System.out.print("📞 Enter contact: ");
                contact = scanner.nextLine();
                if (contact.length() == 10 && isAllDigits(contact)) {
                    break;
                } else {
                    System.out.println("❗ Contact number must be exactly 10 digits.");
                }
            }
            String sql = "INSERT INTO workers (name, department, status, contact) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, department);
            pst.setString(3, status);
            pst.setString(4, contact);
            int rows = pst.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Worker added successfully.");
            } else {
                System.out.println("❌ Failed to add worker.");
            }
            pst.close();
        } catch (Exception e) {
            System.out.println("❌ Error adding worker: " + e.getMessage());
        }
    }

    private static void viewAllWorkers(Connection con) {
        try {
            System.out.println("\n👀 All Workers:");
            String sql = "SELECT * FROM workers";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            boolean found = false;
            while (rs.next()) {
                found = true;
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String department = rs.getString("department");
                String status = rs.getString("status");
                String contact = rs.getString("contact");
                models.Worker worker = new models.Worker(id, name, department, status, contact);
                worker.toString(); // prints details
            }
            if (!found) {
                System.out.println("⚠️ No workers found.");
            }
            rs.close();
            pst.close();
        } catch (Exception e) {
            System.out.println("❌ Error viewing workers: " + e.getMessage());
        }
    }

    private static void updateWorker(Connection con, Scanner scanner) {
        try {
            System.out.println("\n✏️ Update Worker");
            System.out.print("🔢 Enter Worker ID to update: ");
            int id = Integer.parseInt(scanner.nextLine());
            String selectSql = "SELECT * FROM workers WHERE id = ?";
            PreparedStatement selectPst = con.prepareStatement(selectSql);
            selectPst.setInt(1, id);
            ResultSet rs = selectPst.executeQuery();
            if (!rs.next()) {
                System.out.println("⚠️ Worker not found.");
                rs.close();
                selectPst.close();
                return;
            }
            String currentName = rs.getString("name");
            String currentDepartment = rs.getString("department");
            String currentStatus = rs.getString("status");
            String currentContact = rs.getString("contact");
            rs.close();
            selectPst.close();

            System.out.println("Leave blank to keep current value.");
            System.out.print("👤 New name (current: " + currentName + "): ");
            String name = scanner.nextLine();
            if (name.isEmpty()) name = currentName;
            System.out.print("🏢 New department (current: " + currentDepartment + "): ");
            String department = scanner.nextLine();
            if (department.isEmpty()) department = currentDepartment;
            System.out.print("📋 New status (current: " + currentStatus + "): ");
            String status = scanner.nextLine();
            if (status.isEmpty()) status = currentStatus;
            String contact;
            while (true) {
                System.out.print("📞 New contact (current: " + currentContact + "): ");
                contact = scanner.nextLine();
                if (contact.isEmpty()) {
                    contact = currentContact;
                    break;
                } else if (contact.length() == 10 && isAllDigits(contact)) {
                    break;
                } else {
                    System.out.println("❗ Contact number must be exactly 10 digits.");
                }
            }
            String updateSql = "UPDATE workers SET name = ?, department = ?, status = ?, contact = ? WHERE id = ?";
            PreparedStatement updatePst = con.prepareStatement(updateSql);
            updatePst.setString(1, name);
            updatePst.setString(2, department);
            updatePst.setString(3, status);
            updatePst.setString(4, contact);
            updatePst.setInt(5, id);
            int rows = updatePst.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Worker updated successfully.");
            } else {
                System.out.println("❌ Failed to update worker.");
            }
            updatePst.close();
        } catch (Exception e) {
            System.out.println("❌ Error updating worker: " + e.getMessage());
        }
    }

    private static void deleteWorker(Connection con, Scanner scanner) {
        try {
            System.out.println("\n❌ Delete Worker");
            System.out.print("🔢 Enter Worker ID to delete: ");
            int id = Integer.parseInt(scanner.nextLine());
            String selectSql = "SELECT * FROM workers WHERE id = ?";
            PreparedStatement selectPst = con.prepareStatement(selectSql);
            selectPst.setInt(1, id);
            ResultSet rs = selectPst.executeQuery();
            if (!rs.next()) {
                System.out.println("⚠️ Worker not found.");
                rs.close();
                selectPst.close();
                return;
            }
            String name = rs.getString("name");
            rs.close();
            selectPst.close();
            System.out.print("❓ Are you sure you want to delete worker '" + name + "'? (y/n): ");
            String confirm = scanner.nextLine();
            if (!confirm.equalsIgnoreCase("y")) {
                System.out.println("❌ Deletion cancelled.");
                return;
            }
            String deleteSql = "DELETE FROM workers WHERE id = ?";
            PreparedStatement deletePst = con.prepareStatement(deleteSql);
            deletePst.setInt(1, id);
            int rows = deletePst.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Worker deleted successfully.");
            } else {
                System.out.println("❌ Failed to delete worker.");
            }
            deletePst.close();
        } catch (Exception e) {
            System.out.println("❌ Error deleting worker: " + e.getMessage());
        }
    }
}
