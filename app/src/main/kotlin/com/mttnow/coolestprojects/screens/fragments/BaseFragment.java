package com.mttnow.coolestprojects.screens.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.mttnow.coolestprojects.app.CoolestProjectsApp;
import com.mttnow.coolestprojects.network.CoolestProjectsService;
import com.mttnow.coolestprojects.screens.fragments.dagger.DaggerBaseFragmentComponent;

import javax.inject.Inject;

public class BaseFragment extends Fragment {

    @Inject
    CoolestProjectsService coolestProjectsService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerBaseFragmentComponent.builder()
            .appComponent(CoolestProjectsApp.get(getActivity()).getAppComponent())
            .build().inject(this);
    }

    public CoolestProjectsService getCoolestProjectsService() {
        return coolestProjectsService;
    }
}
