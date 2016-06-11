package com.mttnow.coolestprojects.screens.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mttnow.coolestprojects.R;
import com.mttnow.coolestprojects.models.Summit;
import com.mttnow.coolestprojects.screens.adapters.SummitsAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class StagesFragment extends BaseFragment {

    private TextView mStage1Btn;
    private TextView mStage2Btn;
    private TextView mStage3Btn;
    private TextView mStage4Btn;
    private List<TextView> mStagesBtns = new ArrayList<>();

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
        mStage3Btn = (TextView) view.findViewById(R.id.stage_3);
        mStage4Btn = (TextView) view.findViewById(R.id.stage_4);

        mStagesLv = (ListView) view.findViewById(R.id.stages_lv);
        mStagesBtns.add(mStage1Btn);
        mStagesBtns.add(mStage2Btn);
        mStagesBtns.add(mStage3Btn);
        mStagesBtns.add(mStage4Btn);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        coolestProjectsService.summits()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<List<Summit>>() {

                @Override
                public void call(List<Summit> summits) {
                    setupTabs(summits);
                    showSelectedStages(summits, summits.get(0).getSummit());
                    setupListeners(summits);
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    throwable.printStackTrace();
                }
            });

    }

    private void showSelectedStages(List<Summit> summits, String summit) {
        for (int i = 0; i < summits.size(); i++) {
            if(summit.equalsIgnoreCase(summits.get(i).getSummit())) {
                mStagesLv.setAdapter(new SummitsAdapter(summits.get(i).getSpeakers()));
                break;
            }
        }
    }

    private void setupTabs(List<Summit> summits) {
        for (int i = 0; i < Math.min(4, summits.size()); i++) {
            mStagesBtns.get(i).setText(summits.get(i).getSummit());
        }
    }

    private void setupListeners(final List<Summit> summits) {

        mStage1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectSummits("Startup & Innovator", view, summits);
            }
        });

        mStage2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectSummits("Game", view, summits);
            }
        });

        mStage3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectSummits("Social & Women in Tech", view, summits);
            }
        });

        mStage4Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectSummits("MasterCard Code Summit", view, summits);
            }
        });
    }

    private void selectSummits(String key, View view, List<Summit> summits) {
        for (int i = 0; i<mStagesBtns.size(); i++) {
            mStagesBtns.get(i).setTextColor(Color.BLACK);
        }
        ((TextView)view).setTextColor(ContextCompat.getColor(getActivity(), R.color.stages_btn_selected));
        showSelectedStages(summits, key);
    }
}
