package com.example.nikit.eventsapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by harsimar on 20/05/18.
 */

public class EventList {

    @SerializedName("event")
    private List<Event> eventList;

}
