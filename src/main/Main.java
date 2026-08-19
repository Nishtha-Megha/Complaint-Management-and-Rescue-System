package main;

import java.sql.Connection;
import java.util.*;
import db.*;
import ds.*;
import models.*;
import services.*;
import services.ComplaintService;

public class Main {
    public static Scanner sc=new Scanner(System.in);
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    public static AuthService authService=new AuthService();
    public static User currentUser=null;
    public static void main(String[] args) throws Exception {
        Connection con=UserTable.getuserconnection();
        if(con==null){
            return;
        }
        int choice;
        try {

            do{

            System.out.println("🚔 Welcome to Police & Emergency Service Provider System 🚔");
            System.out.println("==================================================");
            System.out.println("1.®️Registration \n2.👤Login \n3.👨‍💼Admin \n4.📝Complaint \n5.⬅️Exit");

                System.out.print("🔢 Enter your choice :");
                choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        registration();
                        showLoginMenu();
                        break;
                    case 2:
                        login();
                        if (currentUser != null) {
                            showLoginMenu();
                        }
                        break;
                    case 3: admin();
                        break;

                    case 4:
                        registerComplaint();
                        break;
                    case 5:
                        System.out.println("Thank you for visiting..");
                        break;
                    default:
                        System.out.println("⚠️Enter valid choice");
                }

            } while(choice != 5);
        }
        catch (InputMismatchException e){
            System.out.println("⚠️ Invalid input");
        }

    }
    static void registration() throws Exception {
        try{
            UserTable.createUser();
        } catch (Exception e) {
            System.out.println("⚠️ Error occur while creating table");
            return;
        }
        System.out.println("Welcome to Registration");
        sc.nextLine();
        System.out.print("Enter your name :");
        String name=sc.nextLine();
        System.out.println("Enter your email : ");
        String email=sc.nextLine();
        System.out.println("Enter your password : ");
        String password=sc.next();

        User u=new User(name,email,password);

        boolean b=authService.register(u);
        if(b){
            System.out.println("✅ Registered successfully");
        }
        else {
            System.out.println("⚠️ Email already exists");
        }
    }

    static void login(){
        System.out.println("Welcome to login");
        sc.nextLine();

        System.out.print("Enter Your Email : ");
        String email=sc.nextLine();
        System.out.print("Enter Your PassWord : ");
        String password=sc.next();
        try {
            currentUser = authService.login(email, password);
            if (currentUser != null) {
                System.out.println("✅ Welcome, " + currentUser.getName());
            } else {
                System.out.println("❌ Login failed. Incorrect email or password.");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Error during login: " + e.getMessage());
        }
    }

    public static void showLoginMenu() throws Exception {
        System.out.println("1.🆘Emergency \n2.✏️Update your profile \n3.🔚Logout \n4.⬅️Exit  ");
        System.out.println("🔢 Enter your choice : ");
        int choice;
        try {
            do{
                choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                    case 1:
                        boolean b=EmergencyService.emergency();
                        if(b){
                            showLoginMenu();
                        }
                        break;
                    case 2:
                        AuthService.update();
                        break;
                    case 3:
                        boolean c=AuthService.logout(currentUser.getEmail());
                        if(c){
                            System.out.println("🔚 Logged out successfully!");
                        }
                        else {
                            System.out.println("⚠️ Logout failed. User may not exist.");
                        }
                        currentUser=null;
                        return;
                    case 4:
                        System.out.println("⬅️ Exiting system.");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("⚠️ Enter valid choice");
                        System.out.println("Enter valid choice");
                }
            }while(choice!=4);
        } catch (InputMismatchException e) {
            System.out.println("Please enter valid choice");
        }

    }

    private static void registerComplaint() throws Exception {
        try{
            ComplainTable.createTable();
        }catch (Exception e){
            System.out.println("️️⚠️ Error occur while creating table");
            return;
        }
        System.out.println("1.Register a complaint");
        System.out.println("2.Get complaint details");
        System.out.print("Enter your choice :");
        int choice=sc.nextInt();
        sc.nextLine();
        if(choice==1){

            System.out.print("🆔 Enter your name: ");
            String name=sc.next();
            sc.nextLine();

            System.out.print("📧 Enter your email :");
            String email=sc.nextLine();

            String number;
            while (true) {
                System.out.print("📞 Enter your 10-digit mobile number: ");
                number = sc.nextLine();
                if (number.length() == 10) {
                    break;
                } else {
                    System.out.println("❌ Invalid number. Please enter a valid 10-digit mobile number.");
                }
            }

            System.out.print("🔍Enter your complaint type(Amc/Police) :");
            String type=sc.nextLine();

            System.out.print("📝 Enter your complaint: ");
            String complaint = sc.nextLine();

            System.out.println("📩 Submitting complaint...");
            Thread.sleep(1000);

            Complaint c=new Complaint(name,email,number,type,complaint);
            ds.ComplaintService.addComplaint(c);
            ComplainTable.addComplaint(c);

            System.out.println("✅ Your complaint has been registered.");
            System.out.println("📞 Authorities will take action shortly.");
        }
        else if(choice==2) {
            System.out.print("📧 Enter your email : ");
            String email= sc.nextLine();

            List<Complaint> c=ComplainTable.getInfo(email);
            if(c.isEmpty()){
                System.out.println("⚠️No complaint find for this Email");
            }
            else{
                for(Complaint x:c){
                    System.out.println("👤 Name: " + x.getName());
                    System.out.println("📧 Email: " + x.getUserEmail());
                    System.out.println("📞 Phone: " + x.getPhoneno());
                    System.out.println("📂 Type: " + x.getCtype());
                    System.out.println("📄 Complaint: " + x.getComplaintText());
                    System.out.println("📃 Status: " +x.getStatus());
                    System.out.println("--------------------------");

                }
            }

        }
        else{
            System.out.println("❌ Invalid option");
        }
    }

    public static void admin() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("==============================");
        System.out.println("👑 ==== ADMIN LOGIN ==== 👑");
        System.out.println("==============================");
        try{

        System.out.print("👤 Username: ");
        String username = scanner.nextLine();
        System.out.print("🔒 Password: ");
        String password = scanner.nextLine();

            if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
                System.out.println("✅ Login successful! Connecting to database...\n");
                Connection con = UserTable.getuserconnection();
                if (con != null) {
                    boolean running = true;
                    Scanner menuScanner = new Scanner(System.in);
                    while (running) {
                        System.out.println("\n==============================");
                        System.out.println("📋 ==== ADMIN DASHBOARD ==== 📋");
                        System.out.println("==============================");
                        System.out.println("1. 💼 Worker Management");
                        System.out.println("2. 📣 Complaint Management");
                        System.out.println("3. ⬇️ Download Complaint List");
                        System.out.println("4. 🚪 Logout");
                        System.out.print("🔢 Select an option: ");
                        String choice = menuScanner.nextLine();
                        switch (choice) {
                            case "1":
                                System.out.println("💼 Opening Worker Management...");
                                WorkerService.menu(con);
                                break;
                            case "2":
                                ComplaintService.menu(con);
                                System.out.println("📣 Opening Complaint Management...");
                                break;
                            case "3":
                                System.out.println("⬇️ Downloading Complaint List...");
                                ExportService.exportMenu(con);
                                break;
                            case "4":
                                System.out.println("🚪 Logging out... Goodbye!\n");
                                running = false;
                                break;
                            default:
                                System.out.println("❗ Invalid option. Please try again.");
                        }
                    }
                    menuScanner.close();
                } else {
                    System.out.println("❌ Database connection failed. Exiting.");
                }
            } else {
                System.out.println("❌ Invalid credentials. Exiting.");
            }
            scanner.close();
        }catch (Exception e){
            System.out.println("⚠️ Please enter valid Name or Password");
        }
    }
}