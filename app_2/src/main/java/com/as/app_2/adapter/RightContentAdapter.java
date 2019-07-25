package com.as.app_2.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.as.app_2.R;
import com.as.app_2.bean.ContentBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * -----------------------------
 * Created by zqf on 2019/7/25.
 * ---------------------------
 */
public class RightContentAdapter extends BaseQuickAdapter<ContentBean, BaseViewHolder> {

    public RightContentAdapter(int layoutResId, @Nullable List<ContentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ContentBean item) {
        ImageView image_content_title = helper.getView(R.id.image_content_title);
        helper.setText(R.id.tv_content_title, item.getContent_title());
        helper.setText(R.id.tv_content_desc, item.getDescription());

        image_content_title.setImageResource(item.getImageid());
    }

}
