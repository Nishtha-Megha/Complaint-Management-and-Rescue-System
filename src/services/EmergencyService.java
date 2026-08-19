package services;

import java.util.HashMap;
import java.util.Scanner;


public class EmergencyService {
    static Scanner sc=new Scanner(System.in);
    public static HashMap<String,String> rescuemap= new HashMap<>();
    public static boolean emergency() throws Exception {
        System.out.println("Enter Your location : ");
        String location=sc.next();
        rescuemap.put(location,"");
        sc.nextLine();

        while (true) {
            System.out.println("\n🚨 --- Emergency Services Menu --- 🚨");
            System.out.println("1. Natural Disaster");
            System.out.println("2. Fire");
            System.out.println("3. Other Medical Issue");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        NaturalDisaster(location);
                        break;
                    case 2:
                        System.out.println(getLocation(location));
                        System.out.println("🚒 Fire Alert Received...");
                        Thread.sleep(1000);
                        System.out.println("📡 Notifying nearest Fire Station at "+location);
                        Thread.sleep(1500);
                        System.out.println("🚨 Fire Brigade dispatched. Estimated arrival in 10–15 mins.");
                        System.out.println("➡️ Please exit the building calmly.");
                        System.out.println("❌ Do NOT use elevators.");
                        System.out.println("🧯 Use a fire extinguisher only if safe to do so.");
                        break;
                    case 3:
                        System.out.println("🚑 Medical Emergency Alert...");
                        Thread.sleep(1000);
                        System.out.println("📡 Contacting nearest Ambulance service...");
                        Thread.sleep(1500);
                        System.out.println("🚨 Ambulance dispatched. Stay calm.");
                        System.out.println("🩺 Provide basic first aid if possible.");
                        System.out.println("📞 Call 108 if the condition worsens.");
                        break;
                    case 4:
                        System.out.println("✅ Thank you. Stay safe!");
                        return true;
                    default:
                        System.out.println("❌ Invalid choice. Please try again.");
                }

            } catch (Exception e) {
                System.out.println("⚠️ Invalid input. Please enter a valid number.");
                sc.nextLine(); // clear the buffer
            }
        }
    }

    private static void NaturalDisaster(String location) throws InterruptedException {
        System.out.println("\n🌐 Select the type of natural disaster:");
        System.out.println("1.🌊 Flooding");
        System.out.println("2.🔥 Wildfire");
        System.out.println("3.🌏 Earthquake");
        System.out.println("4.⛰️ Landslide");
        System.out.print("Enter your choice: ");

        int c = sc.nextInt();
        sc.nextLine();

        switch (c) {
            case 1:
                System.out.println(getLocation(location));
                System.out.println("🌊 Flood Alert Received...");
                Thread.sleep(1000);
                System.out.println("📡 Dispatching Rescue Team...");
                Thread.sleep(1500);
                System.out.println("🕒 Arrival in 15–20 mins.");
                Thread.sleep(1000);
                System.out.println("✅ Stay calm.");
                System.out.println("⬆️ Move to higher ground.");
                System.out.println("⚡ Stay away from electric wires and poles.");
                break;
            case 2:
                System.out.println(getLocation(location));
                System.out.println("🔥 Wildfire Alert Received...");
                Thread.sleep(1000);
                System.out.println("📡 Notifying Rescue & Medical Teams...");
                Thread.sleep(1500);
                System.out.println("🚑🧑‍🚒 Teams arriving in 15–20 mins.");
                System.out.println("✅ Shut down gas and electricity.");
                System.out.println("😷 Cover nose/mouth with a wet cloth.");
                System.out.println("💡 Keep lights ON for visibility.");
                System.out.println("🚪 Evacuate early if advised.");
                break;
            case 3:
                System.out.println(getLocation(location));
                System.out.println("🌍 Earthquake Alert...");
                Thread.sleep(1000);
                System.out.println("📡 Rescue teams en route...");
                Thread.sleep(1500);
                System.out.println("🧑‍🚒 Teams arriving in 15–20 mins.");
                System.out.println("🔻 Drop, Cover, and Hold On:");
                System.out.println("➡️ Drop to your hands and knees.");
                System.out.println("➡️ Cover your head under sturdy furniture.");
                System.out.println("➡️ Hold on until shaking stops.");
                System.out.println("🚫 Stay away from windows and heavy objects.");
                System.out.println("🚷 Don’t use elevators.");
                System.out.println("🏞️ If outdoors, move to an open area.");
                break;
            case 4:
                System.out.println(location);
                System.out.println("🏔️ Landslide Alert Received...");
                Thread.sleep(1000);
                System.out.println("📡 Notifying Geological and Rescue Authorities...");
                Thread.sleep(1500);
                System.out.println("🚑🧑‍🚒 Teams arriving in 15–25 mins.");
                System.out.println("⚠️ Stay away from steep slopes and unstable ground.");
                System.out.println("🚷 Avoid river valleys and low-lying areas.");
                System.out.println("🧳 Pack emergency essentials if evacuation is advised.");
                System.out.println("📻 Stay tuned to local alerts and instructions.");
                break;
            default:
                System.out.println("❌ Invalid disaster option.");
        }
    }
        public static String getLocation(String location){
            if (location.equalsIgnoreCase("Navrangpura")) {
                rescuemap.put("Navrangpura", "Navrangpura disaster unit");
                return rescuemap.get("Navrangpura");
            } else if (location.equalsIgnoreCase("Sanand")) {
                rescuemap.put("Sanand", "Sanand disaster unit");
                return rescuemap.get("Sanand");
            } else if (location.equalsIgnoreCase("Maninagar")) {
                rescuemap.put("Maninagar", "Maninagar disaster unit");
                return rescuemap.get("Maninagar");
            } else {
                rescuemap.put(location, "Nearest disaster unit will arrive");
                return rescuemap.get(location);
            }
        }
}


