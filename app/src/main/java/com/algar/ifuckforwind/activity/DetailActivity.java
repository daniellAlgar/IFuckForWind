package com.algar.ifuckforwind.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.algar.ifuckforwind.R;
import com.algar.ifuckforwind.fragment.SpotPreferedWindDirRadarChartFragment;
import com.algar.ifuckforwind.fragment.WindChartFragment;


public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // TODO: Default färgen bör sättas i res mappen. Bör vara samma som i aktiviteten innan denna
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(getIntent().getIntExtra(CardActivity.INTENT_BACKGROUND_COLOR, -1)));

        setTitle(getIntent().getStringExtra(CardActivity.INTENT_LOCATION_NAME));

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_detail_spot_wind_pref_frame_layout, new SpotPreferedWindDirRadarChartFragment())
                    .commit();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_detail_wind_chart_frame_layout, new WindChartFragment())
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
