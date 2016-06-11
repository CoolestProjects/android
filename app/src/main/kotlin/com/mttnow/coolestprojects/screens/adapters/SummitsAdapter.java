package com.mttnow.coolestprojects.screens.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mttnow.coolestprojects.R;
import com.mttnow.coolestprojects.models.SummitSpeaker;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SummitsAdapter extends BaseAdapter {

    private List<SummitSpeaker> mSummitSpeakers;

    public SummitsAdapter(List<SummitSpeaker> summitSpeakers) {
        mSummitSpeakers = summitSpeakers;
    }

    @Override
    public int getCount() {
        return mSummitSpeakers.size();
    }

    @Override
    public Object getItem(int i) {
        return mSummitSpeakers.get(i);
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
            rowView = inflater.inflate(R.layout.summit_row, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.img = (ImageView) rowView.findViewById(R.id.summit_img);
            viewHolder.name = (TextView) rowView.findViewById(R.id.speaker_name);
            viewHolder.desc = (TextView) rowView.findViewById(R.id.speaker_desc);
            rowView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) rowView.getTag();
        SummitSpeaker summitSpeaker = (SummitSpeaker) getItem(position);

        if(summitSpeaker.getPhotoUrl() != null && !"".equals(summitSpeaker.getPhotoUrl())) {
            Picasso.with(viewGroup.getContext()).load(summitSpeaker.getPhotoUrl()).into(holder.img);
        }
        holder.name.setText(summitSpeaker.getName());
        holder.desc.setText(summitSpeaker.getDescription());
        return rowView;
    }
}
