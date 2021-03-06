package com.as.app_2.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.as.app_2.R;
import com.as.app_2.bean.left.LeftBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * -----------------------------
 * Created by zqf on 2019/7/24.
 * ---------------------------
 */
public class LeftAdapter extends BaseQuickAdapter<LeftBean, BaseViewHolder> {

    private int selectPosition;

    public LeftAdapter(int layoutResId, @Nullable List<LeftBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LeftBean item) {

        helper.setText(R.id.tv_left, item.getLableName());
        ImageView image_left = helper.getView(R.id.image_left);
        image_left.setImageResource(item.getResid());


        boolean isSelected = helper.getAdapterPosition() == selectPosition;
        helper.setTextColor(R.id.tv_left, isSelected ? Color.parseColor("#FF1497E8") : Color.parseColor("#000000"));
        helper.itemView.setBackgroundColor(isSelected ? Color.parseColor("#efefef") : Color.parseColor("#ffffff"));


    }

    public void setSelectPosition(int index) {
        notifyItemChanged(selectPosition);
        notifyItemChanged(index);
        selectPosition = index;


    }
}
