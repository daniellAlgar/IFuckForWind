package com.algar.ifuckforwind.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.algar.ifuckforwind.R;
import com.algar.ifuckforwind.adapter.DetailActivityAdapter;
import com.algar.ifuckforwind.fragment.SectionsPagerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailCardActivity extends AppCompatActivity implements DetailActivityAdapter.Caller {

    private boolean mIsHappyDay;

    @BindView(R.id.detail_recycler_view) RecyclerView mRecyclerView;
    private DetailActivityAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_card);
        ButterKnife.bind(this);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getIntent().getStringExtra(SectionsPagerFragment.INTENT_EXTRA_CURRENT_DAY));
        mRecyclerView.setHasFixedSize(true);

        mIsHappyDay = getIntent().getBooleanExtra(SectionsPagerFragment.INTENT_EXTRA_IS_HAPPY_DAY, true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new DetailActivityAdapter(this,
                new int[]{R.drawable.getskar, R.drawable.apelviken},
                new String[]{"Getskär", "Apelviken"},
                new String[]{"41 km", "79 km"},
                new String[]{"Midday: 8-10 m/s", "Midday: 7-13 m/s"},
                new String[]{"Afternoon: 9-13 m/s", "Afternoon: 8-13 m/s"});

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
    public void onItemClicked(View view) {
        Log.v("tag", "snyggTag");
    }
}
