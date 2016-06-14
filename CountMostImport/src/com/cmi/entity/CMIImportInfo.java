package com.cmi.entity;

/**
 * Created by lenovo on 2016/6/5.
 */
public class CMIImportInfo {

    private String mTitle;

    private int mCount;

    public CMIImportInfo(String title, int count){
        mTitle = title;
        mCount = count;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public int getmCount() {
        return mCount;
    }

    public void setmCount(int mCount) {
        this.mCount = mCount;
    }
}
