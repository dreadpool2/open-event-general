package com.example.nikit.eventsapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by harsimar on 20/05/18.
 */

public class EventList {

    @SerializedName("data")
    private List<Event> eventList;

    public List<Event> getEventList() {
        return eventList;
    }
}
