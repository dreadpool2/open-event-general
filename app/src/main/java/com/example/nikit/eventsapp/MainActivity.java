package com.example.nikit.eventsapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.nikit.eventsapp.model.Event;
import com.example.nikit.eventsapp.model.EventList;
import com.example.nikit.eventsapp.rest.ApiClient;
import com.example.nikit.eventsapp.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final static String API_KEY = "7e8f60e325cd06e164799af1e317d7a7";
    private String TOKEN=null;
    private static final String app="application/vnd.api+json";
    private List<Event> eventList;
    private RecyclerView recyclerView;
    private EventsRecyclerAdapter eventsRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();
        TOKEN = i.getStringExtra("TOKEN");
        TOKEN="JWT "+TOKEN;
        Log.d("harsimarSingh",TOKEN);

        getSupportActionBar().setTitle("Events");

        eventList = new ArrayList<>();
        recyclerView = findViewById(R.id.events_recycler);
        setUpRecycler();
        eventsRecyclerAdapter = new EventsRecyclerAdapter();
        recyclerView.setAdapter(eventsRecyclerAdapter);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<EventList> call = apiService.getEvents2(app,TOKEN);
        call.enqueue(new Callback<EventList>() {
            @Override
            public void onResponse(Call<EventList> call, Response<EventList> response) {
                if(response.isSuccessful()){
                    Log.d("harsimarSingh","Response Success ");
                    eventList.addAll(response.body().getEventList());
                    eventsRecyclerAdapter.addAll(eventList);
                    eventsRecyclerAdapter.notifyDataSetChanged();
                    Log.d("harsimarSingh","Fetched Events "+eventList.size());
                }else {
                    Log.d("harsimarSingh","Not Successful "+response.code());
                }
            }

            @Override
            public void onFailure(Call<EventList> call, Throwable t) {
                Log.d("harsimarSingh","Failure "+t.toString());
            }
        });



    }

    private void setUpRecycler() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(llm);
    }
}
