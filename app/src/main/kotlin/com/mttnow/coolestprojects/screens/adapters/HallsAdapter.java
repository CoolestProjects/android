package com.mttnow.coolestprojects.screens.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mttnow.coolestprojects.R;
import com.mttnow.coolestprojects.models.HallPanel;
import com.mttnow.coolestprojects.models.HallSpeaker;

import java.util.List;

public class HallsAdapter extends BaseAdapter {

    private List<HallPanel> hallPanels;
    Context context;


    public HallsAdapter(List<HallPanel> hallPanels) {
        this.hallPanels = hallPanels;
        this.context = context;
    }

    @Override
    public int getCount() {
        return hallPanels.size();
    }

    @Override
    public Object getItem(int i) {
        return i < hallPanels.size()? hallPanels.get(i): null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }

    static class ViewHolder {
        public TextView name;
        public TextView desc;
        public TextView time;
        public TextView speakerName;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            rowView = inflater.inflate(R.layout.stage_row, null);
            ViewHolder viewHolder = new ViewHolder();
            // viewHolder.img = (ImageView) rowView.findViewById(R.id.summit_img);
            viewHolder.name = (TextView) rowView.findViewById(R.id.hallName);
            viewHolder.desc = (TextView) rowView.findViewById(R.id.hallDesc);
            viewHolder.time = (TextView) rowView.findViewById(R.id.hallTime);
            viewHolder.speakerName = (TextView) rowView.findViewById(R.id.speaker_name) ;
            rowView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) rowView.getTag();
        HallPanel hallWorkshop = (HallPanel) getItem(position);
        String names="";
        if(hallWorkshop != null && hallWorkshop.getPanelSpeakers() != null) {
            for (HallSpeaker speaker : hallWorkshop.getPanelSpeakers()) {
                names +=  speaker.getName() + ", " + speaker.getTitle() + "\n\n";

            }
            holder.name.setText(hallWorkshop.getName());
            holder.desc.setText(hallWorkshop.getDescription());
            holder.time.setText(hallWorkshop.getStartTime() + " - " + hallWorkshop.getEndTime());
            holder.speakerName.setText(names);
        }
        return rowView;

    }
}
