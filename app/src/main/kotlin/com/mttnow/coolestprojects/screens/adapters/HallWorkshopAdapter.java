package com.mttnow.coolestprojects.screens.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mttnow.coolestprojects.R;
import com.mttnow.coolestprojects.models.HallWorkshop;

import java.util.List;

public class HallWorkshopAdapter extends BaseAdapter {

    private List<HallWorkshop> hallWorkshop;


    public HallWorkshopAdapter(List<HallWorkshop> hallWorkshop) {
        this.hallWorkshop = hallWorkshop;
    }

    @Override
    public int getCount() {
        return hallWorkshop.size();
    }

    @Override
    public Object getItem(int i) {
        return hallWorkshop.get(i);
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }

    static class ViewHolder {
        public TextView name;
        public TextView desc;
        public TextView time;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            rowView = inflater.inflate(R.layout.workshop_row, null);
            ViewHolder viewHolder = new ViewHolder();
            // viewHolder.img = (ImageView) rowView.findViewById(R.id.summit_img);
            viewHolder.name = (TextView) rowView.findViewById(R.id.hallName);
            viewHolder.desc = (TextView) rowView.findViewById(R.id.hallDesc);
            viewHolder.time = (TextView) rowView.findViewById(R.id.hallTime);
            rowView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) rowView.getTag();
        HallWorkshop hallWorkshop = (HallWorkshop) getItem(position);

        holder.name.setText(hallWorkshop.getName());
        holder.desc.setText(hallWorkshop.getDescription());
        holder.time.setText(hallWorkshop.getStartTime() + " - " + hallWorkshop.getEndTime());
        return rowView;

    }
}
