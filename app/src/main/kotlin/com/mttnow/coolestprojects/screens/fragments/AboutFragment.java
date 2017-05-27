package com.mttnow.coolestprojects.screens.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.mttnow.coolestprojects.R;

import rx.Subscription;

public class AboutFragment extends BaseFragment {

//    private WebView mFirstDescrHtml;
//    private WebView mSecondDescrHtml;
//    private WebView mThirdDescrHtml;

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_about, container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mFirstDescrHtml = (WebView) view.findViewById(R.id.first_desc_html);
//        mSecondDescrHtml = (WebView) view.findViewById(R.id.second_desc_html);
//        mThirdDescrHtml = (WebView) view.findViewById(R.id.third_desc_html);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String firstHtml = getString(R.string.about_first_descr);
//        mFirstDescrHtml.loadDataWithBaseURL(null, firstHtml, "text/html", "utf-8", null);
//        String secondHtml = getString(R.string.about_second_descr);
//        mSecondDescrHtml.loadDataWithBaseURL(null, secondHtml, "text/html", "utf-8", null);
//        String thirdHtml = getString(R.string.about_third_descr);
//        mThirdDescrHtml.loadDataWithBaseURL(null, thirdHtml, "text/html", "utf-8", null);
    }

    @Override
    public Subscription loadRxStuff() {
        return null;
    }
}
