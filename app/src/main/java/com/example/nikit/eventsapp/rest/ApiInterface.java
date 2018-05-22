package com.example.nikit.eventsapp.rest;

import com.example.nikit.eventsapp.model.EventList;
import com.example.nikit.eventsapp.model.Login;
import com.example.nikit.eventsapp.model.LoginResponse;
import com.example.nikit.eventsapp.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface ApiInterface {


    @Headers("Content-Type: application/json")
    @POST("auth/session")
    Call<LoginResponse> login(@Body Login login);

//    @Headers({"Accept: application/vnd.api+json","Authorization: JWT "+ })
//    @GET("/v1/events")
//    Call<List<Event>> getEvents();

    @GET("/v1/events")
    Call<EventList> getEvents2(@Header("Accept") String app,
                               @Header("Authorization") String auth);
    @GET("/v1/users/{id}")
    Call<User> getProfile(@Header("Accept") String app,
                          @Header("Authorization") String auth , @Path("id") long id);


}
