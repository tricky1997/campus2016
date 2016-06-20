package com.qunar.homework.Units.Input;

import com.qunar.homework.Units.Interface.InputHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/20.
 */
public class InputLocalFileHandler implements InputHandler{
    List<File> fileList = new ArrayList<File>();

    public List<File> getInputFileList(String name) {
        getFileList(name);
        return listFiles();
    }
    public List<File> listFiles(){
        return fileList;
    }

    public void getFileList(String dirname){
        File file = new File(dirname);
        for(File sub_file:file.listFiles()) {
            if (sub_file.isDirectory()) {
                getFileList(sub_file.getPath());
            } else {
                if (sub_file.getName().endsWith(".java")) {
                    fileList.add(sub_file);
                }
            }
        }
    }
}
