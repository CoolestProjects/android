package com.mttnow.coolestprojects.screens.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mttnow.coolestprojects.R;
import com.mttnow.coolestprojects.models.HallPanel;
import com.mttnow.coolestprojects.models.HallWorkshop;
import com.mttnow.coolestprojects.models.SummitSpeaker;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HallsAdapter extends BaseAdapter {

    private List<HallWorkshop> mHallWorkshops;
    private List<HallPanel> mHallPanels;

    public HallsAdapter(List<HallWorkshop> hallWorkshops,
                        List<HallPanel> hallPanels) {
        mHallWorkshops = hallWorkshops;
        mHallPanels = hallPanels;
    }

    @Override
    public int getCount() {
        return mHallWorkshops.size();
    }

    @Override
    public Object getItem(int i) {
        return mHallWorkshops.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    static class ViewHolder {
     //   public ImageView img;
        public TextView name;
        public TextView desc;
        public TextView startTime;
        public TextView endTime;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            rowView = inflater.inflate(R.layout.summit_row, null);
            ViewHolder viewHolder = new ViewHolder();
           // viewHolder.img = (ImageView) rowView.findViewById(R.id.summit_img);
            viewHolder.name = (TextView) rowView.findViewById(R.id.workshop_end_time);
            viewHolder.desc = (TextView) rowView.findViewById(R.id.workshop_desc);
            viewHolder.startTime = (TextView) rowView.findViewById(R.id.workshop_start_time);
            viewHolder.endTime = (TextView) rowView.findViewById(R.id.workshop_end_time);
            rowView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) rowView.getTag();
        HallWorkshop hallWorkshop = (HallWorkshop) getItem(position);

//        if(summitSpeaker.getPhotoUrl() != null && !"".equals(summitSpeaker.getPhotoUrl())) {
//            Picasso.with(viewGroup.getContext()).load(summitSpeaker.getPhotoUrl()).into(holder.img);
//        }
        holder.name.setText(hallWorkshop.getName());
        holder.desc.setText(hallWorkshop.getDescription());
        holder.startTime.setText(hallWorkshop.getStartTime());
        holder.endTime.setText(hallWorkshop.getEndTime());
        return rowView;
    }
}
