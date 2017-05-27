package com.mttnow.coolestprojects.screens.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mttnow.coolestprojects.R;
import com.mttnow.coolestprojects.models.Hall;
import com.mttnow.coolestprojects.models.Halls;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class StagesFragment extends BaseFragment {
    private TextView mStage1Btn;
    private TextView mStage2Btn;
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
        mStagesLv = (ListView) view.findViewById(R.id.stages_lv);
        mStagesBtns.add(mStage1Btn);
        mStage1Btn.setText("STEAM");
        mStage2Btn.setText("Smart Futures");


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
    private void setupListeners(final List<Hall> halls) {

        mStage1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mStage2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

}
