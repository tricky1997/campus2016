package com.cmi.main;

import com.cmi.entity.CMIImportInfo;
import com.cmi.entity.CMIImportInfoComparator;
import com.cmi.entity.CMITreeNode;
import com.cmi.service.ICMIImportService;
import com.cmi.service.impl.CMIImportServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lenovo on 2016/6/5.
 */
public class main {

    public final static int TOP_N = 10;

    public static void main(String[] args) {

        String relativelyPath = System.getProperty("user.dir");
        relativelyPath += "\\src";
        ICMIImportService importService = new CMIImportServiceImpl();
        List<String> result = new ArrayList<String>();
        importService.genImportPathIlter(result, relativelyPath);
        CMITreeNode root = new CMITreeNode();
        importService.genPathTree(root, result);
        List<CMIImportInfo> infoList = importService.countImport(root);

        Collections.sort(infoList, new CMIImportInfoComparator());

        int num = infoList.size() > TOP_N ? TOP_N:infoList.size();
        CMIImportInfo info = null;

        for(int i = 0; i < num; i++){
            info = infoList.get(i);
            System.out.println(info.getmTitle()+"  "+info.getmCount());
        }
    }

}
