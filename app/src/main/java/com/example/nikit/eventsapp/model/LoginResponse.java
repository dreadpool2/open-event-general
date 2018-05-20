package com.example.nikit.eventsapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nikit on 11/5/18.
 */

public class LoginResponse {
    @SerializedName("access_token")
    private String accessToken;
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


}
