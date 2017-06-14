package com.mttnow.coolestprojects.screens.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mttnow.coolestprojects.R;
import com.mttnow.coolestprojects.screens.home.mvp.HomeView;

import javax.inject.Inject;

import rx.Subscription;

public class StagesFragment extends BaseFragment {
    @Inject
    HomeView homeview;

    private TextView mStage1Btn;
    private TextView mStage2Btn;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_stages, container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStage1Btn = (TextView) view.findViewById(R.id.stage_1);
        mStage2Btn = (TextView) view.findViewById(R.id.stage_2);
        mStage1Btn.setText("Explore The \n Microsoft Minecraft / STEAM Hall");
        mStage2Btn.setText("Explore The \n Smart Futures Hall");
        setupListeners();


    }
    public void swapFrag(Bundle bundle){
        Fragment newFragment = new StageScheduleFragment();
        newFragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public Subscription loadRxStuff() {
        return null;
    }
    private void setupListeners() {

        mStage1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("hall","STEAM");
                swapFrag(bundle);

            }

        });

        mStage2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("hall","SMART FUTURES");
                swapFrag(bundle);

            }
        });


    }

}
