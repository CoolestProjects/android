package com.mttnow.coolestprojects.screens.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import com.mttnow.coolestprojects.R;
import com.mttnow.coolestprojects.app.CoolestProjectsApp;
import com.mttnow.coolestprojects.network.CoolestProjectsService;
import com.mttnow.coolestprojects.screens.fragments.dagger.DaggerBaseFragmentComponent;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseFragment extends Fragment {

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
    public void onStart() {
        super.onStart();
        setUpRx();
    }

    @Override
    public void onStop() {
        super.onStop();
        compositeSubscription.clear();
    }

    private void setUpRx() {
        compositeSubscription.clear();
        Subscription subscription = loadRxStuff();
        if(subscription != null) {
            addSubscription(subscription);
        }
    }

    public abstract Subscription loadRxStuff();

    public void addSubscription(Subscription subscription) {
        compositeSubscription.add(subscription);
    }

    public void showLoading() {
        progressDialog.show();
    }

    public void hideLoading() {
        progressDialog.hide();
    }

    public void showError() {
        hideLoading();
        new AlertDialog.Builder(getContext())
            .setTitle(R.string.error)
            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    setUpRx();
                }
            })
            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
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

    public Action1<Throwable> showErrorRx() {
        return new Action1<Throwable>() {
            @Override
            public void call(Throwable o) {
                o.printStackTrace();
                showError();
            }
        };
    }

    public CoolestProjectsService getCoolestProjectsService() {
        return coolestProjectsService;
    }
}
