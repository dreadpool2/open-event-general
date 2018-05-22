package com.example.nikit.eventsapp;

/**
 * Created by harsimar on 20/05/18.
 */

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nikit.eventsapp.model.Attributes;
import com.example.nikit.eventsapp.model.Event;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventsRecyclerAdapter extends RecyclerView.Adapter<EventsRecyclerAdapter.EventViewHolder> {

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        ImageView eventImage;
        TextView eventNameTv,desciption,startsAtDay,startsAtYear,startsAtMonth;

        EventViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.all_events_card);
            eventNameTv = (TextView)itemView.findViewById(R.id.all_events_card_event_name);
            startsAtDay = (TextView)itemView.findViewById(R.id.date);
            startsAtYear = (TextView)itemView.findViewById(R.id.year);
            startsAtMonth = (TextView)itemView.findViewById(R.id.month);
            eventImage = (ImageView)itemView.findViewById(R.id.all_events_card_image);
            desciption = (TextView)itemView.findViewById(R.id.description);
        }
    }
    List<Event> events;

    EventsRecyclerAdapter (){
        events = new ArrayList<>();
    }
    public void addAll(List<Event> eventList){
        this.events.addAll(eventList);
    }

    @NonNull
    @Override
    public EventsRecyclerAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_events, parent, false);
        EventViewHolder eventViewHolder = new EventViewHolder(v);
        return eventViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventsRecyclerAdapter.EventViewHolder holder, int position) {

        Attributes attributes = events.get(position).getAttributes();
        holder.eventNameTv.setText(attributes.getName());
        holder.desciption.setText(attributes.getDesciption());

        String[] splitDay = dateFormat(attributes).split(" ");
        holder.startsAtDay.setText(splitDay[0]);
        holder.startsAtMonth.setText(splitDay[1].toUpperCase());
        holder.startsAtYear.setText(splitDay[2]);


        //Picasso
        if(attributes.getOriginalImageUrl() != null) {
            Picasso.with(holder.eventImage.getContext())
                    .load(Uri.parse(attributes.getOriginalImageUrl()))
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.eventImage);
        }

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public String dateFormat(Attributes attributes){
        String returnString = "";
        String string = attributes.getStartsAt().substring(0,9);
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(string);
            SimpleDateFormat dt1 = new SimpleDateFormat("dd MMM yyyy");
            returnString = dt1.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}