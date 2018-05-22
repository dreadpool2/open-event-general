package com.example.nikit.eventsapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private String TOKEN=null;
    private ActionBar toolbar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            Bundle bundle ;
            switch (item.getItemId()) {
                case R.id.navigation_events:
                    toolbar.setTitle("Events");

                    bundle = new Bundle();
                    bundle.putString("TOKEN",TOKEN);

                    fragment = new EventsFragment();
                    fragment.setArguments(bundle);
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_profile:
                    toolbar.setTitle("Profile");

                    bundle = new Bundle();
                    bundle.putString("TOKEN",TOKEN);

                    fragment = new ProfileFragment();
                    fragment.setArguments(bundle);
                    loadFragment(fragment);

                    return true;
                case R.id.navigation_title1:
                    toolbar.setTitle("Title1");
                    return true;
                case R.id.navigation_title2:
                    toolbar.setTitle("Title2");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = getSupportActionBar();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        toolbar.hide();



        Intent i = getIntent();
        TOKEN = i.getStringExtra("TOKEN");
        TOKEN="JWT "+TOKEN;
        Log.d("harsimarSingh",TOKEN);
        getSupportActionBar().setTitle("Events");
        Bundle bundle = new Bundle();
        bundle.putString("TOKEN",TOKEN);
        EventsFragment eventsFragment = new EventsFragment();
        eventsFragment.setArguments(bundle);
        loadFragment(eventsFragment);


    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
