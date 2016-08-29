package com.algar.ifuckforwind.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private LinearLayout mCardViewContainer;
    private Caller mListener;

    public interface Caller {
        void onItemClicked(View view);
    }

    public DetailActivityAdapter(Caller listener, int[] spotAvatar, String[] spotNames, String[] spotDistance,
                                 String[] spotWindDay, String[] spotWindAfternoon) {
        mListener = listener;
        mSpotNames = spotNames;
        mSpotDistance = spotDistance;
        mSpotWindDay = spotWindDay;
        mSpotWindAfternoon = spotWindAfternoon;
        mSpotAvatar = spotAvatar;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(itemView, new ViewHolder.ViewHolderClicks() {
            @Override
            public void itemClicked(View view) {
                mListener.onItemClicked(view);
            }
        });
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

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public ImageView mSpotAvatar_imageview;
        public TextView mSpotName_textview;
        public TextView mSpotDistance_textview;
        public TextView mWindStrengthDay_textview;
        public TextView mWindStrengtAfternoon_textview;
        public ViewHolderClicks mListener;

        public ViewHolder(View itemView, ViewHolderClicks listener) {
            super(itemView);
            mListener = listener;
            mSpotAvatar_imageview = (ImageView) itemView.findViewById(R.id.detail_cardview_spot_avatar_imageview);
            mSpotName_textview = (TextView) itemView.findViewById(R.id.detail_cardview_spot_name_textview);
            mSpotDistance_textview = (TextView) itemView.findViewById(R.id.detail_cardview_spot_distance_textview);
            mWindStrengthDay_textview = (TextView) itemView.findViewById(R.id.detail_cardview_wind_day_textview);
            mWindStrengtAfternoon_textview = (TextView) itemView.findViewById(R.id.detail_cardview_wind_afternoon_textview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.itemClicked(view);
        }

        interface ViewHolderClicks {
            void itemClicked(View caller);
        }
    }
}
