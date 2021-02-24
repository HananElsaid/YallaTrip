package iti.intake41.myapplication.modules.signup.model;

public class UserData {
    String userID;
    String userEmail;
    String usrPassword;
    String userConPassword;
    //String userName;

    public UserData() {
    }

    public UserData(String userID, String userEmail, String usrPassword, String userConPassword) {
        this.userID = userID;
        this.userEmail = userEmail;
        this.usrPassword = usrPassword;
        this.userConPassword = userConPassword;
        //this.userName = userName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
