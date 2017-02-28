package com.example.grupoes.projetoes.beans;

/**
 * Created by Wesley on 23/02/2017.
 */

public class EditUserBean {
    private String user;
    private String newPassword;
    private String oldPassword;

    public EditUserBean() {

    }

    public EditUserBean(String user, String newPassword, String oldPassword) {
        this.user = user;
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
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

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
