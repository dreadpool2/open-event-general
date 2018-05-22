package com.example.nikit.eventsapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.nikit.eventsapp.model.Event;
import com.example.nikit.eventsapp.model.EventList;
import com.example.nikit.eventsapp.rest.ApiClient;
import com.example.nikit.eventsapp.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EventsFragment extends Fragment {

    private static final String app = "application/vnd.api+json";
    private List<Event> eventList;
    private RecyclerView recyclerView;
    private EventsRecyclerAdapter eventsRecyclerAdapter;
    private ProgressBar progressBar;
    private String TOKEN = null;

    public EventsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            TOKEN = getArguments().getString("TOKEN");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        progressBar = view.findViewById(R.id.progressHeader);
        progressBar.setIndeterminate(true);

        eventList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.events_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        eventsRecyclerAdapter = new EventsRecyclerAdapter();
        recyclerView.setAdapter(eventsRecyclerAdapter);
        recyclerView.setNestedScrollingEnabled(false);


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<EventList> call = apiService.getEvents2(app, TOKEN);
        call.enqueue(new Callback<EventList>() {
            @Override
            public void onResponse(Call<EventList> call, Response<EventList> response) {
                if (response.isSuccessful()) {
                    Log.d("harsimarSingh", "Response Success ");
                    eventList.addAll(response.body().getEventList());
                    eventsRecyclerAdapter.addAll(eventList);

                    progressBar.setIndeterminate(false);
                    progressBar.setVisibility(View.GONE);

                    eventsRecyclerAdapter.notifyDataSetChanged();
                    Log.d("harsimarSingh", "Fetched Events " + eventList.size());
                } else {
                    Log.d("harsimarSingh", "Not Successful " + response.code());
                }
            }

            @Override
            public void onFailure(Call<EventList> call, Throwable t) {
                Log.d("harsimarSingh", "Failure " + t.toString());
            }
        });
        return view;
    }
}

