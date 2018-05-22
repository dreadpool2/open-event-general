package com.example.nikit.eventsapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by harsimar on 20/05/18.
 */

public class AttributesUser {

    @SerializedName("last-name")
    private String lastName;
    @SerializedName("first-name")
    private String firstName;
    @SerializedName("email")
    private String email;
    @SerializedName("contact")
    private String contact;
    @SerializedName("avatar-url")
    private String avatarUrl;

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}
