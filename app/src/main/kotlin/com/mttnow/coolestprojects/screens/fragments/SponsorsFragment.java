package com.mttnow.coolestprojects.screens.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applidium.headerlistview.HeaderListView;
import com.mttnow.coolestprojects.R;
import com.mttnow.coolestprojects.models.SponsorTier;
import com.mttnow.coolestprojects.screens.adapters.SponsorsAdapter;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SponsorsFragment extends BaseFragment {

    private HeaderListView mSponsorsHeaderListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_sponsors, container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSponsorsHeaderListView = (HeaderListView) view.findViewById(R.id.sponsors_lv);
        mSponsorsHeaderListView.getListView().setDivider(null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        coolestProjectsService.sponsors()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<SponsorTier>>() {
                    @Override
                    public void call(List<SponsorTier> sponosrs) {
                        mSponsorsHeaderListView.setAdapter(new SponsorsAdapter(sponosrs));

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public Subscription loadRxStuff() {
        return null;
    }
}
