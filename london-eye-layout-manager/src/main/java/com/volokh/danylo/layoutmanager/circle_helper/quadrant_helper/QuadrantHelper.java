package com.volokh.danylo.layoutmanager.circle_helper.quadrant_helper;

import android.view.View;

import com.volokh.danylo.layoutmanager.ViewData;
import com.volokh.danylo.layoutmanager.circle_helper.point.Point;

/**
 * Created by danylo.volokh on 12/3/2015.
 *
 * This is generic interface for quadrant related functionality.
 *
 * To layout in each quadrant you should implement quadrant-specific classes :
 * {@link FirstQuadrantHelper}
 */
public interface QuadrantHelper {
    Point findNextViewCenter(ViewData previousViewData, int nextViewHalfViewWidth, int nextViewHalfViewHeight);

    int getViewCenterPointIndex(Point point);

    Point getViewCenterPoint(int newCenterPointIndex);

    int getNewCenterPointIndex(int newCalculatedIndex);

    Point findPreviousViewCenter(ViewData nextViewData, int previousViewHalfViewHeight);

    boolean isLastLayoutedView(int recyclerHeight, View view);

    int checkBoundsReached(int recyclerViewHeight, int dy, View firstView, View lastView, boolean isFirstItemReached, boolean isLastItemReached);

    int getOffset(int recyclerViewHeight, View lastView);

}
