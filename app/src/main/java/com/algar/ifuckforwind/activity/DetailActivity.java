package com.algar.ifuckforwind.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.algar.ifuckforwind.R;
import com.algar.ifuckforwind.adapter.DetailActivityAdapter;
import com.algar.ifuckforwind.fragment.SectionsPagerFragment;

public class DetailActivity extends AppCompatActivity {

    boolean mIsHappyDay;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mIsHappyDay = getIntent().getBooleanExtra(SectionsPagerFragment.INTENT_EXTRA_IS_HAPPY_DAY, true);

        mRecyclerView = (RecyclerView) findViewById(R.id.detail_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new DetailActivityAdapter(
                new int[]{R.drawable.getskar, R.drawable.apelviken},
                new String[]{"Getskär", "Apelviken"},
                new String[]{"41", "79"},
                new String[]{"8-10 m/s", "7-13 m/s"},
                new String[]{"9-13 m/s", "8-13 m/s"});
        mRecyclerView.setAdapter(mAdapter);
    }
}
