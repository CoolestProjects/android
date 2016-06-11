package com.mttnow.coolestprojects.screens.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mttnow.coolestprojects.R;
import com.mttnow.coolestprojects.models.Speaker;
import com.mttnow.coolestprojects.models.Summary;

import java.util.List;

public class ProjectsAdapter extends BaseAdapter {

    private List<Summary> mProjects;

    public ProjectsAdapter(List<Summary> projects) {
        mProjects = projects;
    }

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
            rowView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) rowView.getTag();
        Speaker speaker = (Speaker) getItem(position);
        holder.name.setText(speaker.getName());
        holder.desc.setText(speaker.getDescription());

        return rowView;
    }
}
