package models;

public class Complaint {
    private String name;
    private String userEmail;
    private String complaintText;
    private String phoneno;
    private String ctype;
    private String status;


    public Complaint(String name, String userEmail, String phoneno, String ctype , String complaintText) {
        this.userEmail = userEmail;
        this.name = name;
        this.complaintText = complaintText;
        this.phoneno = phoneno;
        this.ctype = ctype;
        this.status = "Pending..";
    }

    public String getName() {
        return name;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getComplaintText() {
        return complaintText;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public String getCtype() {
        return ctype;
    }
    public String getStatus(){
        return status;
    }
}
