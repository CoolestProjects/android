package com.mttnow.coolestprojects.screens.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mttnow.coolestprojects.R;
import com.mttnow.coolestprojects.screens.fragments.ui.ZoomableImageView;

public class MapsFragment extends BaseFragment {

    private ZoomableImageView mRdsMap;
    private ZoomableImageView mParkingMap;

    public static MapsFragment newInstance() {
        return new MapsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_maps, container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRdsMap = (ZoomableImageView) view.findViewById(R.id.rds_map);
        mParkingMap = (ZoomableImageView) view.findViewById(R.id.parking_map);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bitmap rdsMapBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.map);
        mRdsMap.setImageBitmap(rdsMapBitmap);
        Bitmap parkingMapBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.parking);
        mParkingMap.setImageBitmap(parkingMapBitmap);
    }
}
