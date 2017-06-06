package com.mttnow.coolestprojects.screens.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mttnow.coolestprojects.R;
import com.mttnow.coolestprojects.models.HallPanel;

import java.util.List;

public class HallsAdapter extends BaseAdapter {

    private List<HallPanel> hallPanels;


    public HallsAdapter(List<HallPanel> hallPanels) {
        this.hallPanels = hallPanels;
    }

    @Override
    public int getCount() {
        return hallPanels.size();
    }

    @Override
    public Object getItem(int i) {
        return hallPanels.get(i);
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }

    static class ViewHolder {
        public TextView name;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            rowView = inflater.inflate(R.layout.summit_row, null);
            ViewHolder viewHolder = new ViewHolder();
            // viewHolder.img = (ImageView) rowView.findViewById(R.id.summit_img);
            viewHolder.name = (TextView) rowView.findViewById(R.id.hallName);
            rowView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) rowView.getTag();
        HallPanel hallWorkshop = (HallPanel) getItem(position);

        holder.name.setText(hallWorkshop.getName());
        return rowView;

    }
}
