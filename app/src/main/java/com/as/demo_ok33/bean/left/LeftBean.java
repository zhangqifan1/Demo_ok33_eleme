package com.as.demo_ok33.bean.left;

/**
 * -----------------------------
 * Created by zqf on 2019/7/24.
 * ---------------------------
 */
public class LeftBean {
    private boolean isSelected;

    private int resid;
    private String lableName;


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getResid() {
        return resid;
    }

    public void setResid(int resid) {
        this.resid = resid;
    }

    public String getLableName() {
        return lableName;
    }

    public void setLableName(String lableName) {
        this.lableName = lableName;
    }

    public LeftBean(boolean isSelected, int resid, String lableName) {
        this.isSelected = isSelected;
        this.resid = resid;
        this.lableName = lableName;
    }
}
