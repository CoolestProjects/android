package com.mttnow.coolestprojects.screens.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mttnow.coolestprojects.R;
import com.mttnow.coolestprojects.screens.fragments.ui.ZoomableImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import rx.Subscription;

public class MapsFragment extends Fragment {

    private ZoomableImageView mRdsMap;
    private Target mTargetCallback = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            mRdsMap.setImageBitmap(bitmap);
            mRdsMap.setZoom(2);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    };

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
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Picasso.with(getActivity()).load("https://firebasestorage.googleapis.com/v0/b/coolestprojectsapp.appspot.com/o/Maps%2Fcoolestprojectsmap_medium.png?alt=media&token=cdbe7fbf-88b9-4257-bb6e-1b27a5536f9d").into(mTargetCallback);
    }
}
