package com.mttnow.coolestprojects.screens.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mttnow.coolestprojects.R;
import com.mttnow.coolestprojects.models.Speaker;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SpeakersAdapter extends BaseAdapter {

    private List<Speaker> mSpeakers;
    private final int mScreenWidth;

    public SpeakersAdapter(List<Speaker> speakers, int screenWidth) {
        mSpeakers = speakers;
        mScreenWidth = screenWidth;
    }

    @Override
    public int getCount() {
        return mSpeakers.size();
    }

    @Override
    public Object getItem(int i) {
        return mSpeakers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    static class ViewHolder {
        public ImageView img;
        public TextView name;
        public TextView desc;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            rowView = inflater.inflate(R.layout.speaker_row, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.img = (ImageView) rowView.findViewById(R.id.speaker_img);
            viewHolder.name = (TextView) rowView.findViewById(R.id.speaker_name);
            viewHolder.desc = (TextView) rowView.findViewById(R.id.speaker_desc);
            rowView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) rowView.getTag();
        Speaker speaker = (Speaker) getItem(position);
        ViewGroup.LayoutParams params = holder.img.getLayoutParams();
        params.height = mScreenWidth;
        params.width = mScreenWidth;
        holder.img.setLayoutParams(params);
        Picasso.with(viewGroup.getContext()).load(speaker.getPhotoUrl()).into(holder.img);
        holder.name.setText(speaker.getName());
        holder.desc.setText(speaker.getDescription());

        return rowView;
    }
}
