package com.mttnow.coolestprojects.screens.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mttnow.coolestprojects.R;
import com.mttnow.coolestprojects.models.Hall;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Robbie on 5/31/2017.
 */

public class StageScheduleFragment extends BaseFragment {
    List<Hall> halls;
    private RadioGroup radioGroup;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.stage_shedule, container, false);
        Bundle bundle = getArguments();
        String hall = bundle.getString("hall");
        return contentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        setUpListeners();

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

    public void setUpListeners() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.panels) {
                    Toast.makeText(getActivity(), "Panels!",
                            Toast.LENGTH_LONG).show();

                } else if (checkedId == R.id.workshop1) {
                    Toast.makeText(getActivity(), "Workshop 1",
                            Toast.LENGTH_LONG).show();

                } else if (checkedId == R.id.workshop2) {
                    Toast.makeText(getActivity(), "Workshop 2!",
                            Toast.LENGTH_LONG).show();
                } else {

                }
            }

        });
    }
}
