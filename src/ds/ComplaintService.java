package ds;

import models.Complaint;
import java.util.*;

public class ComplaintService {
     static ArrayList<Complaint> complaints=new ArrayList<>();

        public static void addComplaint(Complaint complaint) {
            complaints.add(complaint);
        }

        public static List<Complaint> getComplaints(String email) {
            List<Complaint> result=new ArrayList<>();
            for(Complaint c:complaints){
                if(c.getUserEmail().equals(email)){
                    result.add(c);
                }
            }
            return result;
        }
}


