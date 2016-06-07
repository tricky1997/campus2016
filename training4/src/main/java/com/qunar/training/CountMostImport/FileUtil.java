package com.qunar.training.CountMostImport;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件处理工具
 * Created by shining.cui on 2016/6/7.
 */
public class FileUtil {
    public static List<File> fileList = new ArrayList<File>();

    /**
     * 读取指定路径下的所有文件，递归循环
     * @param filePath 指定的文件夹目录
     * @return 文件夹及其子文件夹内所有文件
     */
    public static List<File> readAllFiles(String filePath) {
        File basicfile = new File(filePath);
        File[] files = basicfile.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                readAllFiles(file.getPath());
            } else if (file.getName().endsWith(".java")) {
                fileList.add(file);
            }
        }
        return fileList;
    }
}
