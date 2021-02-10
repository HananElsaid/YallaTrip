package iti.intake41.myapplication.signup.model;

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
}
