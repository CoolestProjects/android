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

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SpeakersFragment extends BaseFragment {

    private ListView mSpeakersLv;
    private SpeakersAdapter speakersAdapter;

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
        speakersAdapter = new SpeakersAdapter(getScreenWidth());
        mSpeakersLv.setAdapter(speakersAdapter);
    }


    @Override
    public void onStart() {
        super.onStart();
        addSubscription(Observable.just(null)
            .doOnNext(showLoadingRX())
            .observeOn(Schedulers.io())
            .switchMap(new Func1<Object, Observable<List<Speaker>>>() {
                @Override
                public Observable<List<Speaker>> call(Object o) {
                    return getCoolestProjectsService().speakers();
                }
            })
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext(hideLoadingRX())
            .subscribe(new Action1<List<Speaker>>() {
                @Override
                public void call(List<Speaker> speakers) {
                    speakersAdapter.swapData(speakers);
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    throwable.printStackTrace();
                }
            }));
    }

    private int getScreenWidth() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }
}
