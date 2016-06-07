package com.qunar.training.CountMostImport;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shining.cui on 2016/6/7.
 */
public class FileUtil {
    public static List<File> fileList = new ArrayList<File>();

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
