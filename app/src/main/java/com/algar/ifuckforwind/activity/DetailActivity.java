package com.algar.ifuckforwind.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.algar.ifuckforwind.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.BubbleChartOnValueSelectListener;
import lecho.lib.hellocharts.listener.ViewportChangeListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.BubbleChartData;
import lecho.lib.hellocharts.model.BubbleValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.BubbleChartView;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.PreviewColumnChartView;


public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(getIntent().getIntExtra(CardActivity.INTENT_BACKGROUND_COLOR, -1)));

        setTitle(getIntent().getStringExtra(CardActivity.INTENT_LOCATION_NAME));

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_detail_wind_chart_frame_layout, new WindChartFragment())
                    .commit();
        }
    }

    public static class WindChartFragment extends Fragment {

        private boolean mHasLabels = true;
        private boolean mHasLabelForSelected = true;
        private boolean mHasAxes = true;
        private boolean mHasAxesNames = true;

        private ColumnChartView mColumnChartView;
        private PreviewColumnChartView mPreviewChart;
        private ColumnChartData mColumnChartData;
        private BubbleChartView mBubbleChartView;
        private BubbleChartData mBubbleChartData;
        private ValueShape mBubbleShape = ValueShape.CIRCLE;

        private int mNumYValues = 24;

        /**
         * Deep copy of mColumnChartData.
         */
        private ColumnChartData mPreviewData;

        public WindChartFragment() {
            // Default empty constructor
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            setHasOptionsMenu(true);
            View rootView = inflater.inflate(R.layout.fragment_preview_column_chart, container, false);

            mColumnChartView = (ColumnChartView) rootView.findViewById(R.id.wind_column_chart_view);
            mPreviewChart = (PreviewColumnChartView) rootView.findViewById(R.id.chart_preview);
            mBubbleChartView = (BubbleChartView) rootView.findViewById(R.id.wind_direction_bubble_chart_view);
            mBubbleChartView.setOnValueTouchListener(new ValueTouchListener());

            // Generate mColumnChartData for previewed mColumnChartView and copy of that mColumnChartData for preview mColumnChartView.
//            generateDefaultData();
            generateStackedData();

            mColumnChartView.setColumnChartData(mColumnChartData);
            // Disable zoom/scroll for previewed mColumnChartView, visible mColumnChartView ranges depends on preview mColumnChartView viewport so
            // zoom/scroll is unnecessary.
            mColumnChartView.setZoomEnabled(false);
            mColumnChartView.setScrollEnabled(false);

            mPreviewChart.setColumnChartData(mPreviewData);
            mPreviewChart.setViewportChangeListener(new ViewportListener());

            previewX(false);

            // Generate mBubbleChartData
            generateBubbleData();
            mBubbleChartView.setBubbleChartData(mBubbleChartData);

            return rootView;
        }

        // MENU
        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.preview_column_chart, menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.action_reset) {
                generateDefaultData();
                mColumnChartView.setColumnChartData(mColumnChartData);
                mPreviewChart.setColumnChartData(mPreviewData);
                previewX(true);
                return true;
            }
            if (id == R.id.action_preview_both) {
                previewXY();
                mPreviewChart.setZoomType(ZoomType.HORIZONTAL_AND_VERTICAL);
                return true;
            }
            if (id == R.id.action_preview_horizontal) {
                previewX(true);
                return true;
            }
            if (id == R.id.action_preview_vertical) {
                previewY();
                return true;
            }
            if (id == R.id.action_change_color) {
                int color = ChartUtils.pickColor();
                while (color == mPreviewChart.getPreviewColor()) {
                    color = ChartUtils.pickColor();
                }
                mPreviewChart.setPreviewColor(color);
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        private void generateBubbleData() {

            List<BubbleValue> values = new ArrayList<BubbleValue>();
            for (int i = 0; i < mNumYValues; ++i) {
//                BubbleValue value = new BubbleValue(i, (float) Math.random() * 100, (float) Math.random() * 1000);
                BubbleValue value = new BubbleValue(i, 1, 1);
                value.setColor(ChartUtils.pickColor());
                value.setShape(mBubbleShape);
                values.add(value);
            }

            mBubbleChartData = new BubbleChartData(values);
            mBubbleChartData.setHasLabels(mHasAxes);
            mBubbleChartData.setHasLabelsOnlyForSelected(mHasLabelForSelected);

            if (mHasLabels) {
                Axis axisX = new Axis();
                Axis axisY = new Axis().setHasLines(false);
                if (mHasAxesNames) {
//                    axisX.setName("Axis X");
                    axisY.setName(" ");
                }
                mBubbleChartData.setAxisXBottom(null);
                mBubbleChartData.setAxisYLeft(axisY);
            } else {
                mBubbleChartData.setAxisXBottom(null);
                mBubbleChartData.setAxisYLeft(null);
            }

            mBubbleChartView.setBubbleChartData(mBubbleChartData);

        }

        private void generateStackedData() {
            int numSubcolumns = 2;
            mNumYValues = 24;
            // Column can have many stacked sub-columns, here I use 4 stacked sub-column in each of 4 columns.
            List<Column> columns = new ArrayList<Column>();
            List<SubcolumnValue> subcolumnValues;
            for (int i = 0; i < mNumYValues; ++i) {

                subcolumnValues = new ArrayList<SubcolumnValue>();
                for (int j = 0; j < numSubcolumns; ++j) {
                    subcolumnValues.add(new SubcolumnValue((float) Math.random() * 20f + 5, ChartUtils.pickColor()));
                }

                Column column = new Column(subcolumnValues);
                column.setHasLabels(mHasLabels);
                column.setHasLabelsOnlyForSelected(mHasLabelForSelected);
                columns.add(column);
            }

            mColumnChartData = new ColumnChartData(columns);

            // Set stacked flag.
            mColumnChartData.setStacked(true);

            // prepare preview mColumnChartData, is better to use separate deep copy for preview mColumnChartView.
            // set color to grey to make preview area more visible.
            mPreviewData = new ColumnChartData(mColumnChartData);
            for (Column column : mPreviewData.getColumns()) {
                for (SubcolumnValue value : column.getValues()) {
                    value.setColor(ChartUtils.DEFAULT_DARKEN_COLOR);
                }
            }

            if (mHasAxes) {
                Axis axisX = new Axis();
                Axis axisY = new Axis().setHasLines(true);
                if (mHasAxesNames) {
                    axisX.setName("hour");
                    axisY.setName("m/s");
                }
                mColumnChartData.setAxisXBottom(axisX);
                mColumnChartData.setAxisYLeft(axisY);
            } else {
                mColumnChartData.setAxisXBottom(null);
                mColumnChartData.setAxisYLeft(null);
            }

            mColumnChartView.setColumnChartData(mColumnChartData);
        }

        private void generateDefaultData() {
            int numSubcolumns = 1;
            int numColumns = 50;
            List<Column> columns = new ArrayList<Column>();
            List<SubcolumnValue> values;
            for (int i = 0; i < numColumns; ++i) {

                values = new ArrayList<SubcolumnValue>();
                for (int j = 0; j < numSubcolumns; ++j) {
                    values.add(new SubcolumnValue((float) Math.random() * 40f + 5, ChartUtils.pickColor()));
                }

                columns.add(new Column(values));
            }

            mColumnChartData = new ColumnChartData(columns);
            mColumnChartData.setAxisXBottom(new Axis());
            mColumnChartData.setAxisYLeft(new Axis().setHasLines(true));

            // prepare preview mColumnChartData, is better to use separate deep copy for preview mColumnChartView.
            // set color to grey to make preview area more visible.
            mPreviewData = new ColumnChartData(mColumnChartData);
            for (Column column : mPreviewData.getColumns()) {
                for (SubcolumnValue value : column.getValues()) {
                    value.setColor(ChartUtils.DEFAULT_DARKEN_COLOR);
                }
            }
        }

        private void previewY() {
            Viewport tempViewport = new Viewport(mColumnChartView.getMaximumViewport());
            float dy = tempViewport.height() / 4;
            tempViewport.inset(0, dy);
            mPreviewChart.setCurrentViewportWithAnimation(tempViewport);
            mPreviewChart.setZoomType(ZoomType.VERTICAL);
        }

        private void previewX(boolean animate) {
            Viewport tempViewport = new Viewport(mColumnChartView.getMaximumViewport());
            float dx = tempViewport.width() / 4;
            tempViewport.inset(dx, 0);
            if (animate) {
                mPreviewChart.setCurrentViewportWithAnimation(tempViewport);
            } else {
                mPreviewChart.setCurrentViewport(tempViewport);
            }
            mPreviewChart.setZoomType(ZoomType.HORIZONTAL);
        }

        private void previewXY() {
            // Better to not modify viewport of any mColumnChartView directly so create a copy.
            Viewport tempViewport = new Viewport(mColumnChartView.getMaximumViewport());
            // Make temp viewport smaller.
            float dx = tempViewport.width() / 4;
            float dy = tempViewport.height() / 4;
            tempViewport.inset(dx, dy);
            mPreviewChart.setCurrentViewportWithAnimation(tempViewport);
        }

        /**
         * Viewport listener for preview mColumnChartView(lower one). in {@link #onViewportChanged(Viewport)} method change
         * viewport of upper mColumnChartView.
         */
        private class ViewportListener implements ViewportChangeListener {

            @Override
            public void onViewportChanged(Viewport newViewport) {
                // don't use animation, it is unnecessary when using preview mColumnChartView because usually viewport changes
                // happens to often.
                mColumnChartView.setCurrentViewport(newViewport);
                mBubbleChartView.setCurrentViewport(newViewport);
            }
        }

        private class ValueTouchListener implements BubbleChartOnValueSelectListener {

            @Override
            public void onValueSelected(int bubbleIndex, BubbleValue value) {
                Toast.makeText(getActivity(), "Selected: " + value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onValueDeselected() {
                // TODO Auto-generated method stub
            }
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
