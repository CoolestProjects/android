package com.mttnow.coolestprojects.screens.fragments;


import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mttnow.coolestprojects.R;
import com.mttnow.coolestprojects.models.Speaker;
import com.mttnow.coolestprojects.screens.adapters.SpeakersAdapter;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SpeakersFragment extends BaseFragment {

    private ListView mSpeakersLv;

    public static SpeakersFragment newInstance() {
        return new SpeakersFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_speakers, container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSpeakersLv = (ListView) view.findViewById(R.id.speakers_lv);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

              coolestProjectsService.speakers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Speaker>>() {
                    @Override
                    public void call(List<Speaker> speakers) {
                        mSpeakersLv.setAdapter(new SpeakersAdapter(speakers, getScreenWidth()));

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });

    }

    private int getScreenWidth() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }
}
