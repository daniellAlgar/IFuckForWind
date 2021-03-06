package com.algar.ifuckforwind.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
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

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.BubbleChartOnValueSelectListener;
import lecho.lib.hellocharts.listener.ViewportChangeListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
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

/**
 * Created by algar on 2016-12-02
 */

public class WindChartFragment extends Fragment {
    // TODO: Preview fönstret ska matcha färgen från action bar bakgrounden

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
    private Bitmap mArrowBitmap;

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
        generateStackedData();

        mColumnChartView.setColumnChartData(mColumnChartData);
        // Disable zoom/scroll for previewed mColumnChartView, visible mColumnChartView ranges depends on preview mColumnChartView viewport so
        // zoom/scroll is unnecessary.
        mColumnChartView.setZoomEnabled(false);
        mColumnChartView.setScrollEnabled(false);

        mPreviewChart.setColumnChartData(mPreviewData);
        mPreviewChart.setViewportChangeListener(new ViewportListener());
        previewX(true);

        mPreviewChart.setContainerScrollEnabled(true, ContainerScrollType.VERTICAL);
        mPreviewChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);

        // Generate mBubbleChartData
        setupArrow();
        generateBubbleChartArrowData();
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
            generateStackedData();
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

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth)
    {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

    public int pxToDp(int px) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public static Bitmap rotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    private void setupArrow() {
        int scaleFactor = 8;

        mArrowBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.wind_dir_arrow);
        mArrowBitmap = getResizedBitmap(mArrowBitmap,
                mArrowBitmap.getWidth()/scaleFactor,
                mArrowBitmap.getHeight()/scaleFactor);
    }

    private void generateBubbleChartArrowData() {

        List<BubbleValue> values = new ArrayList<>();
        for (int i = 0; i < mNumYValues; ++i) {
            BubbleValue value = new BubbleValue(i, 1, 1);
            value.setShape(ValueShape.ARROW);
            value.setBitmap(rotateBitmap(mArrowBitmap, (float) Math.random() * 100));
            values.add(value);
        }

        mBubbleChartData = new BubbleChartData(values);
        mBubbleChartData.setHasLabels(mHasAxes);
        mBubbleChartData.setHasLabelsOnlyForSelected(mHasLabelForSelected);

        if (mHasLabels) {
            Axis axisY = new Axis().setHasLines(false);
            if (mHasAxesNames) {
                axisY.setName(" ");
            }
            mBubbleChartData.setAxisXBottom(null);
            mBubbleChartData.setAxisYLeft(axisY);
        } else {
            mBubbleChartData.setAxisXBottom(null);
            mBubbleChartData.setAxisYLeft(null);
        }

        mBubbleChartView.setBubbleChartData(mBubbleChartData);
        mBubbleChartView.setScrollEnabled(false);
    }

    private void generateStackedData() {
        int numSubcolumns = 2;
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

        List<AxisValue> axisValues = new ArrayList<>();
        for (int i = 0; i < mNumYValues; i++) {
            axisValues.add(new AxisValue(i));
        }

        if (mHasAxes) {
            Axis axisX = new Axis().setValues(axisValues);
            Axis axisY = new Axis().setHasLines(true);
            if (mHasAxesNames) {
                axisX.setName("Time (h)");
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