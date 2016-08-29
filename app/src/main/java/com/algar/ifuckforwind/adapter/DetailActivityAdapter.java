package com.algar.ifuckforwind.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.algar.ifuckforwind.R;
import com.algar.ifuckforwind.util.Spot;

import java.util.ArrayList;

/**
 * Created by algar on 2016-08-28.
 */
public class DetailActivityAdapter extends RecyclerView.Adapter<DetailActivityAdapter.ViewHolder> {

//    private String[] mSpotNames;
//    private String[] mSpotDistance;
//    private String[] mSpotWindDay;
//    private String[] mSpotWindAfternoon;
//    private int[] mSpotAvatar;
    private Caller mListener;
    private ArrayList<Spot> mSpots;

    public interface Caller {
        void onItemClicked(Spot spot);
    }

    public DetailActivityAdapter(Caller listener, ArrayList<Spot> spot) {
        mListener = listener;
        mSpots = spot;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(itemView, new ViewHolder.ViewHolderClicks() {
            @Override
            public void itemClicked(Spot spot) {
                mListener.onItemClicked(spot);
            }
        });
        return vh;
    }


    private int spotAv;
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Spot spot = mSpots.get(position);
        holder.mSpotAvatar_imageview.setImageResource(spot.getAvatar());
        holder.mSpotName_textview.setText(spot.getLocation());
        holder.mSpotDistance_textview.setText(spot.getDistanceTo());
        holder.mWindStrengthDay_textview.setText(spot.getWindDay());
        holder.mWindStrengtAfternoon_textview.setText(spot.getWindAfternoon());
        holder.spot = spot;
    }

    @Override
    public int getItemCount() {
        return mSpots.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public ImageView mSpotAvatar_imageview;
        public TextView mSpotName_textview;
        public TextView mSpotDistance_textview;
        public TextView mWindStrengthDay_textview;
        public TextView mWindStrengtAfternoon_textview;
        public ViewHolderClicks mListener;
        public Spot spot;

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
            mListener.itemClicked(spot);
        }

        interface ViewHolderClicks {
            void itemClicked(Spot spot);
        }
    }
}
