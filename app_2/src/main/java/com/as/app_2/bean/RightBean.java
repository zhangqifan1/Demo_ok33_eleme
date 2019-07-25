package com.as.app_2.bean;

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

    private List<ContentBean> contentBeans;


    public RightBean(int type, List<ContentBean> contentBean, String groupName) {
        this.type = type;
        this.contentBeans = contentBean;
        this.groupName = groupName;
    }

    public List<ContentBean> getcontentBean() {
        return contentBeans;
    }

    public void setcontentBean(List<ContentBean> contentBean) {
        this.contentBeans = contentBean;
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
