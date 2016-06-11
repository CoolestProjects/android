package com.mttnow.coolestprojects.screens.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.applidium.headerlistview.SectionAdapter;
import com.mttnow.coolestprojects.R;
import com.mttnow.coolestprojects.models.SponsorTier;
import com.squareup.picasso.Picasso;

import java.util.List;


public class SponsorsAdapter extends SectionAdapter {

    private List<SponsorTier> mSponsors;

    public SponsorsAdapter(List<SponsorTier> sponsors) {
        mSponsors = sponsors;
    }

    @Override
    public int numberOfSections() {
        return mSponsors.size();
    }

    @Override
    public boolean hasSectionHeaderView(int section) {
        return true;
    }

    @Override
    public int numberOfRows(int i) {
        if (i == -1) {
            return 0;
        }
        return mSponsors.get(i).getSponsorList().size();
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sponsors_header_tier, parent, false);
            holder.tier = (TextView) convertView.findViewById(R.id.sponsors_tier);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        String headerText = mSponsors.get(section).getTier();
        holder.tier.setText(headerText);
        return convertView;
    }

    @Override
    public View getRowView(int position, int subPosition, View view, ViewGroup viewGroup) {
        View rowView = view;
        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            rowView = inflater.inflate(R.layout.sponsor_row, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.img = (ImageView) rowView.findViewById(R.id.sponsor_logo);
            rowView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) rowView.getTag();
        Picasso.with(viewGroup.getContext()).load(mSponsors.get(position).getSponsorList().get(subPosition).getLogoUrl()).into(holder.img);

        return rowView;
    }

    @Override
    public Object getRowItem(int i, int i1) {
        return mSponsors.get(i).getSponsorList().get(i1);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    class HeaderViewHolder {
        TextView tier;
    }

    static class ViewHolder {
        public ImageView img;
    }

}
