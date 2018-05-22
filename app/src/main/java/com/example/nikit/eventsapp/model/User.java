package com.example.nikit.eventsapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by harsimar on 20/05/18.
 */

public class User {
    @SerializedName("data")
    private UserData user;

    public UserData getUser() {
        return user;
    }
}