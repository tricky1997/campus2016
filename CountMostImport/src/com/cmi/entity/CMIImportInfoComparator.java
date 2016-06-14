package com.cmi.entity;

import java.util.Comparator;

/**
 * Created by lenovo on 2016/6/14.
 */
public class CMIImportInfoComparator implements Comparator<CMIImportInfo> {


    @Override
    public int compare(CMIImportInfo o1, CMIImportInfo o2) {
        return o2.getmCount() - o1.getmCount();
    }
}
