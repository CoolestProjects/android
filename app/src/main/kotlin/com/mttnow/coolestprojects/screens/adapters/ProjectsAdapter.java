package com.mttnow.coolestprojects.screens.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mttnow.coolestprojects.R;
import com.mttnow.coolestprojects.models.Summary;

import java.util.ArrayList;
import java.util.List;

public class ProjectsAdapter extends BaseAdapter {

    private List<Summary> mProjects = new ArrayList<>(0);

    @Override
    public int getCount() {
        return mProjects.size();
    }

    @Override
    public Object getItem(int i) {
        return mProjects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    static class ViewHolder {
        public TextView name;
        public TextView desc;
        public TextView category;
        public TextView desk;
        public TextView doyo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            rowView = inflater.inflate(R.layout.project_row, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.name = (TextView) rowView.findViewById(R.id.project_name);
            viewHolder.desc = (TextView) rowView.findViewById(R.id.project_desc);
            viewHolder.category = (TextView) rowView.findViewById(R.id.project_category);
            viewHolder.desk = (TextView) rowView.findViewById(R.id.project_desk);
            viewHolder.doyo = (TextView) rowView.findViewById(R.id.project_doyo);
            rowView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) rowView.getTag();
        Summary summary = (Summary) getItem(position);
        holder.name.setText(summary.getName());
        holder.desc.setText(summary.getDescription());
        holder.category.setText(summary.getCategory());
        holder.desk.setText(summary.getDeskNumber());
        holder.doyo.setText(summary.getCoderdojo());

        return rowView;
    }

    public void swapData(List<Summary> mProjects) {
        if(mProjects ==  null) {
            this.mProjects.clear();
        } else {
            this.mProjects = mProjects;
        }
        notifyDataSetChanged();
    }
}
