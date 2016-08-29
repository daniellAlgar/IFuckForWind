package com.algar.ifuckforwind.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.algar.ifuckforwind.R;
import com.algar.ifuckforwind.adapter.DetailActivityAdapter;
import com.algar.ifuckforwind.fragment.SectionsPagerFragment;
import com.algar.ifuckforwind.util.Spot;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardActivity extends AppCompatActivity implements DetailActivityAdapter.Caller {

    public static final String INTENT_LOCATION_NAME = "spotName";
    public static final String INTENT_LOCATION_AVATAR = "spotAvatar";
    private boolean mIsHappyDay;

    @BindView(R.id.detail_recycler_view) RecyclerView mRecyclerView;
    private DetailActivityAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        ButterKnife.bind(this);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getIntent().getStringExtra(SectionsPagerFragment.INTENT_EXTRA_CURRENT_DAY));
        mRecyclerView.setHasFixedSize(true);

        mIsHappyDay = getIntent().getBooleanExtra(SectionsPagerFragment.INTENT_EXTRA_IS_HAPPY_DAY, true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<Spot> spots = new ArrayList<>(2);
        spots.add(new Spot("Getskär", "41 km", "Midday: 8-10 m/s", "Afternoon: 9-13 m/s", R.drawable.getskar));
        spots.add(new Spot("Apelviken", "79 km", "Midday: 7-13 m/s", "Afternoon: 8-13 m/s", R.drawable.apelviken));

        mAdapter = new DetailActivityAdapter(this, spots);

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(Spot spot) {
        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
        intent.putExtra(INTENT_LOCATION_NAME, spot.getLocation());
        intent.putExtra(INTENT_LOCATION_AVATAR, spot.getAvatar());
        startActivity(intent);
    }
}
