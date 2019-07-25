package com.as.demo_ok33.layoutmanager;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class ScrollTopLinerManager extends LinearLayoutManager {

    public ScrollTopLinerManager(Context context) {
        super(context);
    }

    public ScrollTopLinerManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public ScrollTopLinerManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
