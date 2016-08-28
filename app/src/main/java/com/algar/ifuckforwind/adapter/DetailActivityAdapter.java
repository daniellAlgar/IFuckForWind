package com.algar.ifuckforwind.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.algar.ifuckforwind.R;

/**
 * Created by algar on 2016-08-28.
 */
public class DetailActivityAdapter extends RecyclerView.Adapter<DetailActivityAdapter.ViewHolder> {

    private String[] mSpotNames;
    private String[] mSpotDistance;
    private String[] mSpotWindDay;
    private String[] mSpotWindAfternoon;
    private int[] mSpotAvatar;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView mSpotAvatar_imageview;
        public TextView mSpotName_textview;
        public TextView mSpotDistance_textview;
        public TextView mWindStrengthDay_textview;
        public TextView mWindStrengtAfternoon_textview;

        public ViewHolder(View v) {
            super(v);
            mSpotAvatar_imageview = (ImageView) v.findViewById(R.id.detail_cardview_spot_avatar_imageview);
            mSpotName_textview = (TextView) v.findViewById(R.id.detail_cardview_spot_name_textview);
            mSpotDistance_textview = (TextView) v.findViewById(R.id.detail_cardview_spot_distance_textview);
            mWindStrengthDay_textview = (TextView) v.findViewById(R.id.detail_cardview_wind_day_textview);
            mWindStrengtAfternoon_textview = (TextView) v.findViewById(R.id.detail_cardview_wind_afternoon_textview);
        }
    }

    public DetailActivityAdapter(int[] spotAvatar, String[] spotNames, String[] spotDistance,
                                 String[] spotWindDay, String[] spotWindAfternoon) {
        mSpotNames = spotNames;
        mSpotDistance = spotDistance;
        mSpotWindDay = spotWindDay;
        mSpotWindAfternoon = spotWindAfternoon;
        mSpotAvatar = spotAvatar;
    }

    @Override
    public DetailActivityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_cardview, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mSpotAvatar_imageview.setImageResource(mSpotAvatar[position]);
        holder.mSpotName_textview.setText(mSpotNames[position]);
        holder.mSpotDistance_textview.setText(mSpotDistance[position]);
        holder.mWindStrengthDay_textview.setText(mSpotWindDay[position]);
        holder.mWindStrengtAfternoon_textview.setText(mSpotWindAfternoon[position]);
    }

    @Override
    public int getItemCount() {
        return mSpotNames.length;
    }
}
