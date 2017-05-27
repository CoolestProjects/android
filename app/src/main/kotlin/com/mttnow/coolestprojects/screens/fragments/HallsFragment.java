package com.mttnow.coolestprojects.screens.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.mttnow.coolestprojects.R;
import com.mttnow.coolestprojects.models.Hall;
import com.mttnow.coolestprojects.screens.adapters.HallsAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class HallsFragment extends BaseFragment {

    private TextView mHalls1Btn;
    private TextView mHalls2Btn;
    private TextView mHalls3Btn;
    private List<TextView> mHallsBtns = new ArrayList<>();

    private ListView mHallsLv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_halls, container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHalls1Btn = (TextView) view.findViewById(R.id.hall_iot);
        mHalls2Btn = (TextView) view.findViewById(R.id.hall_steam);
        mHalls3Btn = (TextView) view.findViewById(R.id.hall_chill);

        mHallsLv = (ListView) view.findViewById(R.id.halls_lv);
        mHallsBtns.add(mHalls1Btn);
        mHallsBtns.add(mHalls2Btn);
        mHallsBtns.add(mHalls3Btn);

    }


    @Override
    public Subscription loadRxStuff() {
        return coolestProjectsService.halls()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<List<Hall>>() {

                @Override
                public void call(List<Hall> halls) {

                    Log.i("HallsFragment", "Halls obj " + (halls != null? halls.size():0));
                    setupTabs(halls);
                    showSelectedHalls(halls, halls.get(0).getHall());
                    setupListeners(halls);
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
    }

    private void showSelectedHalls(List<Hall> halls, String hall) {
        for (int i = 0; i < halls.size(); i++) {
            if(hall.equalsIgnoreCase(halls.get(i).getHall())) {
                mHallsLv.setAdapter(new HallsAdapter(halls.get(i).getHallWorkshops(), halls.get(i).getHallPanels()));
                break;
            }
        }
    }

    private void setupTabs(List<Hall> halls) {
        for (int i = 0; i < Math.min(3, halls.size()); i++) {
            mHallsBtns.get(i).setText(halls.get(i).getHall());
        }
    }

    private void setupListeners(final List<Hall> halls) {

        mHalls1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectHall("IOT", view, halls);
            }
        });

        mHalls2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectHall("STEAM", view, halls);
            }
        });

        mHalls3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectHall("CHILL", view, halls);
            }
        });

    }

    private void selectHall(String key, View view, List<Hall> halls) {
        for (int i = 0; i< mHallsBtns.size(); i++) {
            mHallsBtns.get(i).setTextColor(Color.BLACK);
        }
        ((TextView)view).setTextColor(ContextCompat.getColor(getActivity(), R.color.stages_btn_selected));
        showSelectedHalls(halls, key);
    }
}
