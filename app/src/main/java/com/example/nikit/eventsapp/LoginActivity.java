package com.example.nikit.eventsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    EditText usernameET,passwordET;
    Button loginBtn;
    ProgressDialog progressDialog;
    public static String TOKEN=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        String temp = i.getStringExtra("LOGOUT");
        if(temp != null && temp.equals("TRUE")){
            TOKEN = null;
        }
        setContentView(R.layout.activity_login);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        usernameET= findViewById(R.id.username_et);
        passwordET=findViewById(R.id.password_et);
        loginBtn=findViewById(R.id.login_btn);
        checkToken();
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Logging you in...");

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendPost(ei.getText().toString(),e2.getText().toString());
                sendPost("hey@hey.hey","heyheyhey");
                progressDialog.show();

            }
        });

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
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiService.login(login);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse>call, Response<LoginResponse> response) {
               if(response.isSuccessful()) {
                   String accessToken = response.body().getAccessToken();
                   Toast.makeText(getApplicationContext(), "Success! " , Toast.LENGTH_LONG).show();
                   TOKEN = accessToken;
               }
               else{
                   Toast.makeText(getApplicationContext(), "Error Occured "+response.code(), Toast.LENGTH_LONG).show();
                   Log.d("harsimarSingh","Error "+response.code() + " Error body "+response.errorBody());
               }
                progressDialog.cancel();
               checkToken();
            }

            @Override
            public void onFailure(Call<LoginResponse>call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Error contacting API." ,Toast.LENGTH_LONG).show();
                Log.d("harsimarSingh","Failure "+t.toString());
                TOKEN =null;
                checkToken();
                progressDialog.cancel();
            }
        });
    }
}
