package com.as.demo_ok33.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * -----------------------------
 * Created by zqf on 2019/7/4.
 * ---------------------------
 */
public class RightBean implements MultiItemEntity {

    public static final int TYPE_Title = 4123;
    public static final int TYPE_Content = 4555;


    private int type;
    //后面加的  俩个都有一个相同的标题
    private String groupName;

    @Override
    public int getItemType() {
        return type;
    }

    /************0**********/
    private TitleBean titleBean;

    public RightBean(int type, TitleBean titleBean, String groupName) {
        this.type = type;
        this.titleBean = titleBean;
        this.groupName = groupName;
    }

    /************1**********/

    private ContentBean contentBean;


    public RightBean(int type, ContentBean contentBean, String groupName) {
        this.type = type;
        this.contentBean = contentBean;
        this.groupName = groupName;
    }

    public ContentBean getcontentBean() {
        return contentBean;
    }

    public void setcontentBean(ContentBean contentBean) {
        this.contentBean = contentBean;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public TitleBean getTitleBean() {
        return titleBean;
    }

    public void setTitleBean(TitleBean titleBean) {
        this.titleBean = titleBean;
    }
}
