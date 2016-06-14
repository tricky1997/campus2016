package com.cmi.service;

import com.cmi.entity.CMIImportInfo;
import com.cmi.entity.CMITreeNode;

import java.util.List;

/**
 * Created by lenovo on 2016/6/5.
 */
public interface ICMIImportService {

    /**
     * 获取所有import包的列表
     * @param result
     * @param path
     */
    public void genImportPathIlter(List<String> result, String path);

    /**
     * 构建import路径树
     * @param root
     * @param result
     */
    public void genPathTree(CMITreeNode root, List<String> result);

    /**
     * 构建import路径树
     * @param root
     * @param path
     */
    public void genPathTree(CMITreeNode root, String path);

    /**
     * 计算被import的类的个数
     * @param root
     * @return
     */
    public List<CMIImportInfo> countImport(CMITreeNode root);

}
