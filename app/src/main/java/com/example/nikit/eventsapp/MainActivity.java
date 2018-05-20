package com.example.nikit.eventsapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.nikit.eventsapp.model.EventList;
import com.example.nikit.eventsapp.rest.ApiClient;
import com.example.nikit.eventsapp.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final static String API_KEY = "7e8f60e325cd06e164799af1e317d7a7";
    private String TOKEN=null;
    private static final String app="application/vnd.api+json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();
        TOKEN = i.getStringExtra("TOKEN");
        TOKEN="JWT "+TOKEN;
        Log.d("harsimarSingh",TOKEN);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<EventList> call = apiService.getEvents2(app,TOKEN);
        call.enqueue(new Callback<EventList>() {
            @Override
            public void onResponse(Call<EventList> call, Response<EventList> response) {
                if(response.isSuccessful()){
                    Log.d("harsimarSingh","Response Success "+response.body().toString());
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
}
