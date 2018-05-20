package com.example.nikit.eventsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nikit.eventsapp.model.Login;
import com.example.nikit.eventsapp.model.LoginResponse;
import com.example.nikit.eventsapp.rest.ApiClient;
import com.example.nikit.eventsapp.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    ApiInterface apiService;
    EditText ei,e2;
    Button b;
    public static String TOKEN=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         apiService =
                ApiClient.getClient().create(ApiInterface.class);
         ei= findViewById(R.id.a);
        e2=findViewById(R.id.aa);
        b=findViewById(R.id.ss);
        checkToken();

b.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        //sendPost(ei.getText().toString(),e2.getText().toString());
        sendPost("hey@hey.hey","heyheyhey");
    }
});
        /*ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<LoginResponse> call = apiService.login(API_KEY);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                int statusCode = response.code();
                List<Movie> movies = response.body().getResults();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("se", t.toString());
            }
        });*/

    }
    public void checkToken(){
        if(TOKEN != null){
            Intent i = new Intent(LoginActivity.this,MainActivity.class);
            i.putExtra("TOKEN",TOKEN);
            startActivity(i);
            this.finish();
        }
    }
    public void sendPost(String title, String body) {
Login login=new Login(title.trim(),body.trim()) ;
//        apiService.login(login).enqueue(new Callback<LoginResponse>() {
//            @Override
//            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//
//                if(response.isSuccessful()) {
//                    Toast.makeText(getApplicationContext(),"post submitted to API." ,Toast.LENGTH_LONG).show();
//                    Log.i("sss", "post submitted to API." + response.body().toString());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LoginResponse> call, Throwable t) {
//                Log.e("sss", "Unable to submit post to API.");
//                Toast.makeText(getApplicationContext(),"Unable to submit post to API." ,Toast.LENGTH_LONG).show();
//
//            }
//        });
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<LoginResponse> call = apiService.login(login);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse>call, Response<LoginResponse> response) {
               if(response.isSuccessful()) {
                   String movies = response.body().getAccessToken();
                   // Log.d(TAG, "Number of movies received: " + movies.size());

                   Toast.makeText(getApplicationContext(), "post submitted to API. " + movies, Toast.LENGTH_LONG).show();
                   TOKEN = movies;
               }
               else{
                   Toast.makeText(getApplicationContext(), "post submitted to API with error. "+response.code()+"    " +response.errorBody(), Toast.LENGTH_LONG).show();

               }
               checkToken();
            }

            @Override
            public void onFailure(Call<LoginResponse>call, Throwable t) {
                // Log error here since request failed
               // Log.e(TAG, t.toString());
                       Toast.makeText(getApplicationContext(),"Unable to submit post to API." ,Toast.LENGTH_LONG).show();
                       TOKEN =null;
                       checkToken();

            }
        });
    }
}
