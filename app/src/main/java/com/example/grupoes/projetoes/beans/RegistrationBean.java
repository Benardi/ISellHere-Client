package com.example.grupoes.projetoes.beans;

/**
 * Created by Wesley on 22/02/2017.
 */

public class RegistrationBean {
    private String username;
    private String password;
    private String email;
    private String token;

    public RegistrationBean() {

    }

    public RegistrationBean(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
