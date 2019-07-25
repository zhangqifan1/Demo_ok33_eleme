package com.as.demo_ok33.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.as.demo_ok33.R;
import com.as.demo_ok33.bean.ContentBean;
import com.as.demo_ok33.bean.RightBean;
import com.as.demo_ok33.bean.TitleBean;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * -----------------------------
 * Created by zqf on 2019/7/4.
 * ---------------------------
 */
public class RightAdapter_Grid extends BaseMultiItemQuickAdapter<RightBean, BaseViewHolder> {


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public RightAdapter_Grid(List<RightBean> data) {
        super(data);
        addItemType(RightBean.TYPE_Title, R.layout.item_type_title);
        addItemType(RightBean.TYPE_Content, R.layout.item_right_type_content_grid);

    }

    @Override
    protected void convert(BaseViewHolder helper, RightBean item) {
        switch (item.getItemType()) {
            case RightBean.TYPE_Title:
                helper.itemView.setTag(true);

                TitleBean titleBean = item.getTitleBean();
                helper.setText(R.id.tv_title, item.getGroupName());

                ImageView image_title = helper.getView(R.id.image_title);
                image_title.setImageResource(titleBean.getImageid());

                break;
            case RightBean.TYPE_Content:
                helper.itemView.setTag(false);

                ContentBean contentBean = item.getcontentBean();

                ImageView image_content_title = helper.getView(R.id.image_content_title);
                helper.setText(R.id.tv_content_title, contentBean.getContent_title());
                helper.setText(R.id.tv_content_desc, contentBean.getDescription());

                image_content_title.setImageResource(contentBean.getImageid());


                break;
            default:
                break;
        }

    }


    @Override
    public BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateDefViewHolder(parent, viewType);
    }
}
