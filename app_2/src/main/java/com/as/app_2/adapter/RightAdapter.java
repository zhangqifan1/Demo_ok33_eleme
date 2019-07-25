package com.as.app_2.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.as.app_2.R;
import com.as.app_2.bean.ContentBean;
import com.as.app_2.bean.RightBean;
import com.as.app_2.bean.TitleBean;
import com.as.app_2.layoutmanager.CustomGridLayoutManager;
import com.as.app_2.layoutmanager.CustomLinearLayoutManager;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * -----------------------------
 * Created by zqf on 2019/7/4.
 * ---------------------------
 */
public class RightAdapter extends BaseMultiItemQuickAdapter<RightBean, BaseViewHolder> {

    public static final int TYPE_linear = 0;
    public static final int TYPE_Grid = 1;

    private int layoutType;
    private boolean changed = false;

    public void setLayoutType(int layoutType) {
        this.layoutType = layoutType;
        changed = true;
        notifyDataSetChanged();
    }

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public RightAdapter(List<RightBean> data) {
        super(data);
        addItemType(RightBean.TYPE_Title, R.layout.item_type_title);
        addItemType(RightBean.TYPE_Content, R.layout.item_right_content);

    }

    @Override
    protected void convert(BaseViewHolder helper, RightBean item) {
        switch (item.getItemType()) {
            case RightBean.TYPE_Title:
                TitleBean titleBean = item.getTitleBean();
                helper.setText(R.id.tv_title, item.getGroupName());

                ImageView image_title = helper.getView(R.id.image_title);
                image_title.setImageResource(titleBean.getImageid());

                break;
            case RightBean.TYPE_Content:
                RecyclerView item_rv = helper.getView(R.id.item_rv);

                switch (layoutType) {
                    case TYPE_linear:
                        if (item_rv.getLayoutManager() == null) {
                            CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(mContext);
                            ((CustomLinearLayoutManager) layoutManager).setScrollEnabled(false);
                            item_rv.setLayoutManager(layoutManager);
                        }

                        if (item_rv.getAdapter() == null) {
                            RightContentAdapter itemContentAdapter = new RightContentAdapter(R.layout.item_right_type_content_linear, item.getcontentBean());
                            item_rv.setAdapter(itemContentAdapter);
                        } else {
                            ((RightContentAdapter) item_rv.getAdapter()).setNewData(item.getcontentBean());
                        }

                        if (changed) {
                            CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(mContext);
                            ((CustomLinearLayoutManager) layoutManager).setScrollEnabled(false);
                            item_rv.setLayoutManager(layoutManager);

                            RightContentAdapter itemContentAdapter = new RightContentAdapter(R.layout.item_right_type_content_linear, item.getcontentBean());
                            item_rv.setAdapter(itemContentAdapter);
                            if(helper.getAdapterPosition() == mData.size()-1){
                                changed = false;
                            }
                        }


                        break;
                    case TYPE_Grid:

                        if (item_rv.getLayoutManager() == null) {
                            CustomGridLayoutManager layoutManager = new CustomGridLayoutManager(mContext, 3, false);
                            item_rv.setLayoutManager(layoutManager);
                        }

                        if (item_rv.getAdapter() == null) {
                            RightContentAdapter itemContentAdapter = new RightContentAdapter(R.layout.item_right_type_content_grid, item.getcontentBean());
                            item_rv.setAdapter(itemContentAdapter);
                        } else {
                            ((RightContentAdapter) item_rv.getAdapter()).setNewData(item.getcontentBean());
                        }

                        if (changed) {
                            CustomGridLayoutManager layoutManager = new CustomGridLayoutManager(mContext, 3, false);
                            item_rv.setLayoutManager(layoutManager);
                            RightContentAdapter itemContentAdapter = new RightContentAdapter(R.layout.item_right_type_content_grid, item.getcontentBean());
                            item_rv.setAdapter(itemContentAdapter);
                            if(helper.getAdapterPosition() == mData.size()-1){
                                changed = false;
                            }
                        }
                }
                break;
            default:
                break;
        }


    }

}


