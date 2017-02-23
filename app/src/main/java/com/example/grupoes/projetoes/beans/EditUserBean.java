package com.example.grupoes.projetoes.beans;

/**
 * Created by Wesley on 23/02/2017.
 */

public class EditUserBean {
    private String user;
    private String newPassword;

    public EditUserBean() {

    }

    public EditUserBean(String user, String newPassword) {
        this.user = user;
        this.newPassword = newPassword;
    }

    public String getUser() {
        return user;
    }

    public void setUsername(String username) {
        this.user = username;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String password) {
        this.newPassword = password;
    }
}
