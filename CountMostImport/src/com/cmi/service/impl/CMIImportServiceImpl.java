package com.cmi.service.impl;

import com.cmi.dao.ICMICodeFileOpt;
import com.cmi.dao.impl.ICMICodeFileOptImpl;
import com.cmi.entity.CMIImportInfo;
import com.cmi.entity.CMITreeNode;
import com.cmi.service.ICMIImportService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/6/5.
 */
public class CMIImportServiceImpl implements ICMIImportService {

    private ICMICodeFileOpt mCodeFile = new ICMICodeFileOptImpl();

    @Override
    public void genImportPathIlter(List<String> result, String dirPath) {
        File file = new File(dirPath);

        if(!file.exists() || !file.isDirectory())
            return;

        File[] fileArr = file.listFiles();

        for(int i = 0; i < fileArr.length; i++) {
            if (fileArr[i].isDirectory()) {
                genImportPathIlter(result, fileArr[i].getAbsolutePath());
            } else {
                if(fileArr[i].getName().endsWith(".java") || fileArr[i].getName().endsWith(".JAVA"))
                {
                    result.addAll(mCodeFile.genImportPackage(fileArr[i].getAbsolutePath()));
                }
            }
        }
    }

    @Override
    public void genPathTree(CMITreeNode root, List<String> result) {

        for(String path:result)
        {
            genPathTree(root,path);
        }
    }

    @Override
    public void genPathTree(CMITreeNode root, String path){
        String[] packName = path.split("\\.");
        CMITreeNode node = root;
        for(String name:packName)
        {
            if(name.equals("*")) {
                node.setmCount(node.getmCount() + 1);
                return;
            }else{
                CMITreeNode tmpNode = node.findChildByTitle(name);
                if(tmpNode == null)
                {
                    tmpNode = new CMITreeNode(name,0);
                    node.getmChild().add(tmpNode);
                }
                node = tmpNode;
            }
        }
        node.setmCount(node.getmCount()+1);
    }

    @Override
    public List<CMIImportInfo> countImport(CMITreeNode root){
        List<CMIImportInfo> info = new ArrayList<CMIImportInfo>();
        List<CMITreeNode> childList = root.getmChild();
        if(childList.size() == 0) {
            return info;
        }

        for(CMITreeNode child:childList) {
            countImportNum(info, child, child.getmCount(), child.getmTitle());
        }
        return info;
    }

    private void countImportNum(List<CMIImportInfo> info, CMITreeNode root, int count, String path){
        List<CMITreeNode> childList = root.getmChild();
        if(childList.size() == 0)
        {
            info.add(new CMIImportInfo(path,count));
        }

        for(CMITreeNode child:childList){
            countImportNum(info, child, count + child.getmCount(), path + "." + child.getmTitle());
        }
    }

}
