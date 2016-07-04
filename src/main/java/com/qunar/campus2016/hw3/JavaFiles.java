package com.qunar.campus2016.hw3;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicklaus on 7/4/2016.
 */
public class JavaFiles {

    private List<String> fileLists = new ArrayList<String>();
    public void getFileLists(File file) {

        //判断路径是否为空
        if(file == null) {
            return;
        }

        File[] files = file.listFiles();
        //判断是否不存在文件
        if(files == null || files.length <= 0) {
            return;
        }

        for (File curFile : files) {
            if(curFile.isDirectory()) {
                getFileLists(curFile);
            } else {
                if(isJavaFile(curFile)) {
                    fileLists.add(curFile.getPath());
                }
            }
        }
    }

    //获取当前文件中的文件列表
    public List<String> getFileLists() {
        return fileLists;
    }

    //判断文件是否是java文件
    public boolean isJavaFile(File file)
    {
        String str = file.getPath();
        if(str.endsWith(".java")) {
            return true;
        }
        return false;
    }
}
