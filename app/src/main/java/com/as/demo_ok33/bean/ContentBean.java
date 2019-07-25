package com.as.demo_ok33.bean;

import android.app.Service;

/**
 * -----------------------------
 * Created by zqf on 2019/7/4.
 * ---------------------------
 */
public class ContentBean {

    private String content_title;
    private String description;
    private int imageid;


    public String getContent_title() {
        return content_title;
    }

    public void setContent_title(String content_title) {
        this.content_title = content_title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public ContentBean(String content_title, String description, int imageid) {
        this.content_title = content_title;
        this.description = description;
        this.imageid = imageid;
    }
}
