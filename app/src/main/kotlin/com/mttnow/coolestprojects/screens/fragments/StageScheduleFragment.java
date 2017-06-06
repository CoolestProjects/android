package com.mttnow.coolestprojects.screens.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mttnow.coolestprojects.R;
import com.mttnow.coolestprojects.models.Hall;
import com.mttnow.coolestprojects.models.HallPanel;
import com.mttnow.coolestprojects.models.HallPanels;
import com.mttnow.coolestprojects.models.HallWorkshop;
import com.mttnow.coolestprojects.screens.adapters.HallWorkshopAdapter;
import com.mttnow.coolestprojects.screens.adapters.HallsAdapter;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;
import org.honorato.multistatetogglebutton.ToggleButton;

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
    private ListView mStagesLv;
    private ListView workshop1Lv;
    private ListView workshop2Lv;
    private Hall selectedHall;
    private String hallInput;
    private MultiStateToggleButton toggleButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.stage_shedule, container, false);
        Bundle bundle = getArguments();
        hallInput = bundle.getString("hall");
        return contentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStagesLv = (ListView) view.findViewById(R.id.listView);
        workshop1Lv = (ListView) view.findViewById(R.id.listViewWorkshop);
        workshop2Lv = (ListView) view.findViewById(R.id.listViewWorkshop2);
        toggleButton = (MultiStateToggleButton) view.findViewById(R.id.mstb_multi_id);
        toggleButton.setElements(R.array.stage_schedule_array, 0);
        setUpListViews();
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
                        filterHalls(mhalls);
                        mStagesLv.setAdapter(new HallsAdapter(selectedHall.getHallPanels()));
                        workshop1Lv.setAdapter(new HallWorkshopAdapter(selectedHall.getHallWorkshop1()));
                        workshop2Lv.setAdapter(new HallWorkshopAdapter(selectedHall.getHallWorkshop2()));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    public void setUpListeners() {
        toggleButton.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int position) {
                switch (position) {
                    case 0:
                        mStagesLv.setVisibility(View.VISIBLE);
                        workshop1Lv.setVisibility(View.GONE);
                        workshop2Lv.setVisibility(View.GONE);
                        break;
                    case 1:
                        mStagesLv.setVisibility(View.GONE);
                        workshop1Lv.setVisibility(View.VISIBLE);
                        workshop2Lv.setVisibility(View.GONE);
                        break;
                    case 2:
                        mStagesLv.setVisibility(View.GONE);
                        workshop1Lv.setVisibility(View.GONE);
                        workshop2Lv.setVisibility(View.VISIBLE);
                        break;
                    default:

                }
            }
        });
    }
    public void setUpListViews(){
        mStagesLv.setVisibility(View.VISIBLE);
        workshop1Lv.setVisibility(View.GONE);
        workshop2Lv.setVisibility(View.GONE);
    }



    public void filterHalls(List<Hall> mhalls) {
        for (Hall hall : mhalls) {
            if (hallInput.equals(hall.getHall())) {
                selectedHall = hall;
            }
        }
    }
}
