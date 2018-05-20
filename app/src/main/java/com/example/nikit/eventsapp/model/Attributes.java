package com.example.nikit.eventsapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by harsimar on 20/05/18.
 */

public class Attributes {
    @SerializedName("identifier")
    private String identifier;
    @SerializedName("name")
    private String name;
    @SerializedName("organizer-description")
    private String organizerDescription;

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public String getOrganizerDescription() {
        return organizerDescription;
    }
}
