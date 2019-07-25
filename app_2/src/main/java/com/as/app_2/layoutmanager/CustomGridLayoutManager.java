package com.as.app_2.layoutmanager;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

public class CustomGridLayoutManager extends GridLayoutManager {
    private boolean isScrollEnabled = true;


    public CustomGridLayoutManager(Context context, int spanCount, boolean isScrollEnabled) {
        super(context, spanCount);
        this.isScrollEnabled = isScrollEnabled;
    }

    public CustomGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    /**
     * 禁止滑动
     * canScrollHorizontally（禁止横向滑动）
     *
     * @return
     */
    @Override
    public boolean canScrollHorizontally() {
        return isScrollEnabled && super.canScrollVertically();
    }

    /**
     * 禁止滑动
     * canScrollVertically（禁止竖向滑动）
     *
     * @return
     */
    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }
}