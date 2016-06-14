package com.cmi.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/6/5.
 */
public class CMITreeNode {

    private String mTitle;

    private int mCount;

    private List<CMITreeNode> mChild;

    public CMITreeNode(){
        mTitle = "";
        mCount = 0;
        mChild = new ArrayList<CMITreeNode>();
    }

    public int getmCount() {
        return mCount;
    }

    public void setmCount(int mCount) {
        this.mCount = mCount;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public CMITreeNode(String title, int count){
        mTitle = title;
        mCount = count;
        mChild = new ArrayList<CMITreeNode>();
    }

    public List<CMITreeNode> getmChild() {
        return mChild;
    }

    public CMITreeNode findChildByTitle(String title) {
        for(CMITreeNode node:mChild) {
            if(node.getmTitle().equals(title)) {
                return node;
            }
        }
        return null;
    }


}
