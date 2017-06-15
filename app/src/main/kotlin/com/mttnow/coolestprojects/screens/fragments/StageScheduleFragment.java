package com.mttnow.coolestprojects.screens.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mttnow.coolestprojects.R;
import com.mttnow.coolestprojects.app.CoolestProjectsApp;
import com.mttnow.coolestprojects.models.Hall;
import com.mttnow.coolestprojects.network.CoolestProjectsService;
import com.mttnow.coolestprojects.screens.adapters.HallWorkshopAdapter;
import com.mttnow.coolestprojects.screens.adapters.HallsAdapter;

import java.util.ArrayList;
import org.honorato.multistatetogglebutton.MultiStateToggleButton;
import org.honorato.multistatetogglebutton.ToggleButton;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;
/**
 * Created by Robbie on 5/31/2017.
 */

public class StageScheduleFragment extends Fragment {

    private final List<Hall> halls = new ArrayList<>();
    private ListView mStagesLv;
    private ListView workshop1Lv;
    private ListView workshop2Lv;
    private Hall selectedHall;
    private String hallInput;
    private MultiStateToggleButton toggleButton;
    private CoolestProjectsService coolestProjectsService;
    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Override
    public void onStart() {
        super.onStart();
        compositeSubscription.clear();
        Subscription subscription = loadRxStuff();
        if(subscription != null) {
            addSubscription(subscription);
         }
    }
    public void addSubscription(Subscription subscription) {
               compositeSubscription.add(subscription);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        coolestProjectsService = CoolestProjectsApp.get(getActivity()).getAppComponent().coolestProjectsService();
    }

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

    private Subscription loadRxStuff() {
        return coolestProjectsService.halls()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Hall>>() {

                    @Override
                    public void call(List<Hall> mhalls) {
                        filterHalls(mhalls);
                        mStagesLv.setAdapter(new HallsAdapter(selectedHall.getHallPanels()));
                        workshop1Lv.setAdapter(new HallWorkshopAdapter(selectedHall.getHallWorkshops1()));
                        workshop2Lv.setAdapter(new HallWorkshopAdapter(selectedHall.getHallWorkshops2()));
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
            if (hallInput.equalsIgnoreCase(hall.getHallId())) {
                selectedHall = hall;
            }
        }
    }
}
