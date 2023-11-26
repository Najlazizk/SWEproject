package com.example.saudispot;

import java.io.Serializable;

public class UsersModel implements Serializable {

    private int UserId;
    private String UserName;
    private String UserEmail;
    private String UserPassword;
    public UsersModel() {
    }
//nn
    public UsersModel(String userName, String userEmail, String userPassword) {
//        UserId = userId;
        UserName = userName;
        UserEmail = userEmail;
        UserPassword = userPassword;
    }
    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }
}
