package com.qunar.homework.CountMostImport;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件读取工具
 * Created by zhaocai-luo on 2016/6/15.
 */
public class FileReader {
    public static List<File> fileList = new ArrayList<File>();

    /**
     * 递归读取指定项目目录下的所有java文件对象，保存到一个List容器中
     * @param filePath 输入的指定项目路径
     * @return 所有Java文件
     */
    public static List<File> getAllFilesPath(String filePath){
        File rootFile = new File(filePath);
        File[] files = rootFile.listFiles();
        for (File file : files){
            if (file.isDirectory()){
                getAllFilesPath(file.getPath());
            }
            else if (file.getName().endsWith(".java")){
                fileList.add(file);
            }
        }

        return fileList;
    }
}
