package com.as.app_2.layoutmanager;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class ScrollTopGridManager extends GridLayoutManager {

    public ScrollTopGridManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public ScrollTopGridManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public ScrollTopGridManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {

            /**
             *
             * @param viewStart item的top
             * @param viewEnd item的bottom
             * @param boxStart RecyclerView的top
             * @param boxEnd RecyclerView的bottom
             * @param snapPreference
             * @return 返回item到RecyclerView Top的偏移
             */
            @Override
            public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
                return  boxStart - viewStart;
            }

        };
        linearSmoothScroller.setTargetPosition(position);
        this.startSmoothScroll(linearSmoothScroller);
    }

}
