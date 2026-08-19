package models;

public class Complaint1 {


        private int id;
        private String citizenName;
        private String issue;
        private String department;
        private String status;
        private String date;

        public Complaint1(int id, String citizenName, String issue, String department, String status, String date) {
            this.id = id;
            this.citizenName = citizenName;
            this.issue = issue;
            this.department = department;
            this.status = status;
            this.date = date;
        }

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getCitizenName() { return citizenName; }
        public void setCitizenName(String citizenName) { this.citizenName = citizenName; }
        public String getIssue() { return issue; }
        public void setIssue(String issue) { this.issue = issue; }
        public String getDepartment() { return department; }
        public void setDepartment(String department) { this.department = department; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }

        @Override
        public String toString() {
            System.out.print("ID: " + id + "\n");
            System.out.print("Name: " + citizenName + "\n");
            System.out.print("Issue: " + issue + "\n");
            System.out.print("Department: " + department + "\n");
            System.out.print("Status: " + status + "\n");
            System.out.print("Date: " + date + "\n");
            System.out.print("-------------------------------\n");
            return "";
        }

}
