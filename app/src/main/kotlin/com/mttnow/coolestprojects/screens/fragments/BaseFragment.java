package com.mttnow.coolestprojects.screens.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.mttnow.coolestprojects.R;
import com.mttnow.coolestprojects.app.CoolestProjectsApp;
import com.mttnow.coolestprojects.network.CoolestProjectsService;
import com.mttnow.coolestprojects.screens.fragments.dagger.DaggerBaseFragmentComponent;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

public class BaseFragment extends Fragment {

    @Inject
    CoolestProjectsService coolestProjectsService;

    private ProgressDialog progressDialog;
    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerBaseFragmentComponent.builder()
            .appComponent(CoolestProjectsApp.get(getActivity()).getAppComponent())
            .build().inject(this);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.loading));
    }

    @Override
    public void onStop() {
        super.onStop();
        compositeSubscription.clear();
    }

    public void addSubscription(Subscription subscription) {
        compositeSubscription.add(subscription);
    }

    public void showLoading() {
        progressDialog.show();
    }

    public void hideLoading() {
        progressDialog.hide();
    }

    public Action1<Object> hideLoadingRX() {
        return new Action1<Object>() {
            @Override
            public void call(Object o) {
                hideLoading();
            }
        };
    }

    public Action1<Object> showLoadingRX() {
        return new Action1<Object>() {
            @Override
            public void call(Object o) {
                showLoading();
            }
        };
    }

    public CoolestProjectsService getCoolestProjectsService() {
        return coolestProjectsService;
    }
}
