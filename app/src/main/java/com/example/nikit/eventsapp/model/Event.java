package com.example.nikit.eventsapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by harsimar on 20/05/18.
 */

public class Event {
    @SerializedName("attributes")
    private Attributes attributes;
    @SerializedName("id")
    private String id;
    @SerializedName("type")
    private String type;

    public Attributes getAttributes() {
        return attributes;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}