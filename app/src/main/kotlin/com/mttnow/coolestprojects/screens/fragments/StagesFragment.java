package com.mttnow.coolestprojects.screens.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.mttnow.coolestprojects.R;
import com.mttnow.coolestprojects.models.Hall;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;
import org.honorato.multistatetogglebutton.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static android.view.View.VISIBLE;

public class StagesFragment extends BaseFragment {
    private ViewFlipper viewFlipper;
    private TextView mStage1Btn;
    private TextView mStage2Btn;
    private TextView panelsContent;
    private TextView workshopContent;
    MultiStateToggleButton toggleButton;
    private List<TextView> mStagesBtns = new ArrayList<>();
    private List<Hall> halls;


    private ListView mStagesLv;

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
        panelsContent = (TextView) view.findViewById(R.id.panels_content);
        workshopContent = (TextView) view.findViewById(R.id.workshop_content);
        viewFlipper = (ViewFlipper) view.findViewById(R.id.viewflipper);
        toggleButton = (MultiStateToggleButton) view.findViewById(R.id.mstb_multi_id);
        mStagesBtns.add(mStage1Btn);
        mStage1Btn.setText("Explore The \n STEAM Hall");
        mStage2Btn.setText("Explore The \n Smart Futures Hall");
        setupListeners();


    }


    @Override
    public Subscription loadRxStuff() {
        return coolestProjectsService.halls()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<List<Hall>>() {

                @Override
                public void call(List<Hall> mhalls) {
                    halls = mhalls;
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
    }
    private void setupListeners() {

        mStage1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewFlipper.showNext();
            }
        });

        mStage2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewFlipper.showNext();
            }
        });


        toggleButton.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int position) {
                switch(position) {
                    case 0:
                        panelsContent.setVisibility(VISIBLE);
                        workshopContent.setVisibility(View.GONE);
                        break;
                    case 1:
                        panelsContent.setVisibility(View.GONE);
                        workshopContent.setVisibility(VISIBLE);
                        break;
                    default:
                        panelsContent.setVisibility(VISIBLE);
                        workshopContent.setVisibility(View.GONE);

                }

            }
        });

    }

}
