package com.example.nikit.eventsapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nikit on 11/5/18.
 */


public class Login {
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
