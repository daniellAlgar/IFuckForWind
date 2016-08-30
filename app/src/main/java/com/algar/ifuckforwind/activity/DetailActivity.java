package com.algar.ifuckforwind.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.algar.ifuckforwind.R;

import org.lucasr.dspec.DesignSpec;
import org.lucasr.dspec.DesignSpecFrameLayout;

public class DetailActivity extends AppCompatActivity {

    private String mSpotName;
    private static boolean debugUI = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (debugUI) debugUI();

        mSpotName = getIntent().getStringExtra(CardActivity.INTENT_LOCATION_NAME);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle(mSpotName);

        ImageView imageView = (ImageView) findViewById(R.id.activity_detail_toolbar_image_view);
        imageView.setImageResource(getIntent().getIntExtra(CardActivity.INTENT_LOCATION_AVATAR, -1));

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void debugUI() {
        DesignSpecFrameLayout design = (DesignSpecFrameLayout) findViewById(R.id.activity_detail_design_spec);
        DesignSpec designSpec = design.getDesignSpec();
        designSpec.setBaselineGridVisible(true);
        designSpec.setKeylinesVisible(true);
        designSpec.addKeyline(16, DesignSpec.From.LEFT);
        designSpec.addKeyline(16, DesignSpec.From.RIGHT);
    }
}
