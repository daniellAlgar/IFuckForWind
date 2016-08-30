package com.algar.ifuckforwind.util;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by algar on 2016-08-30.
 */
public class CircleIndicatorBehaviour extends CoordinatorLayout.Behavior<CircleIndicator> {

    public CircleIndicatorBehaviour(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, CircleIndicator child, View dependency) {
        return dependency instanceof Snackbar.SnackbarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, CircleIndicator child, View dependency) {
        float translationY = getCircleIndicatorTranslationYForSnackbar(parent, child);
        child.setTranslationY(translationY);
        return true;
    }

    private float getCircleIndicatorTranslationYForSnackbar(CoordinatorLayout parent, CircleIndicator ci) {
        float minOffset = 0;
        final List<View> dependencies = parent.getDependencies(ci);
        for (int i = 0, z = dependencies.size(); i < z; i++) {
            final View view = dependencies.get(i);
            if (view instanceof Snackbar.SnackbarLayout && parent.doViewsOverlap(ci, view)) {
                minOffset = Math.min(minOffset,
                        ViewCompat.getTranslationY(view) - view.getHeight());;
            }
        }

        return minOffset;
    }
}
