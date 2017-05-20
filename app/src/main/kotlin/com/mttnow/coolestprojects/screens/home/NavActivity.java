package com.mttnow.coolestprojects.screens.home;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mttnow.coolestprojects.R;
import com.mttnow.coolestprojects.screens.fragments.AboutFragment;
import com.mttnow.coolestprojects.screens.fragments.HomeFragment;
import com.mttnow.coolestprojects.screens.fragments.MapsFragment;
import com.mttnow.coolestprojects.screens.fragments.StagesFragment;

public class NavActivity extends AppCompatActivity {

    private TextView textHome;
    private TextView textMaps;
    private TextView textHalls;
    private TextView textAbout;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fragmentManager = getSupportFragmentManager();
        fragment = new HomeFragment();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, fragment).commit();

        textHome = (TextView) findViewById(R.id.action_home);
        textMaps = (TextView) findViewById(R.id.action_maps);
        textHalls = (TextView) findViewById(R.id.action_halls);
        textAbout = (TextView) findViewById(R.id.action_about);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                textHome.setVisibility(View.VISIBLE);
                                fragment = new HomeFragment();
                                textMaps.setVisibility(View.GONE);
                                textHalls.setVisibility(View.GONE);
                                textAbout.setVisibility(View.GONE);

                                break;
                            case R.id.action_maps:
                                textHome.setVisibility(View.GONE);
                                textMaps.setVisibility(View.VISIBLE);
                                fragment = new MapsFragment();
                                textHalls.setVisibility(View.GONE);
                                textAbout.setVisibility(View.GONE);
                                break;
                            case R.id.action_halls:
                                textHome.setVisibility(View.GONE);
                                textMaps.setVisibility(View.GONE);
                                textHalls.setVisibility(View.VISIBLE);
                                fragment = new StagesFragment();
                                textAbout.setVisibility(View.GONE);
                                break;
                            case R.id.action_about:
                              //  textHome.setVisibility(View.GONE);
                               // textMaps.setVisibility(View.GONE);
                                //textHalls.setVisibility(View.GONE);
                               // textAbout.setVisibility(View.VISIBLE);
                                fragment = new AboutFragment();
                                break;
                        }
                        final FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.fragment_container, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        return true;
                    }
                });
    }
}